package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBidderHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.util.TimeHelper;

@Service
@Order(2)
public class HandPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private CardHandler cardHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	
	@Autowired
	private SpadeBidderHandler spadeBidderHandler;
	
	@Autowired
	private TimeHelper timeHelper;
	
	private static final Logger logger = Logger.getLogger(HandPostProcessor.class);
	
	
	private Set<SpadeNotifications> messageNotifications = new HashSet<>(Arrays.asList(
			SpadeNotifications.CLIENT_ERROR
	));
	
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return saveGame(spadeGame);
	}
	
	@Value ("${spade.maxTime:60}")
	private long maxTime;
	@Value ("${spade.maxNotificationTime:12}")
	private int maxNotificationTime;
	
	private  SpadeGame saveGame(SpadeGame spadeGame) {
		
		if(messageNotifications.contains(spadeGame.getPlayerNotification())) {
			return spadeGame;
		}
		logger.info("Entering: "+getClass());
		if(spadeGame.isHandOver()) {
			simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId(), spadeGame);
			
			logger.info("Pausing game id: "+spadeGame.getGameId());
			timeHelper.delayTask(maxNotificationTime);
			logger.info("Finish pausing game id: "+spadeGame.getGameId());
			if(!spadeGame.isGameOver()) {
				cardHandler.distributeCards(spadeGame);
				
			}
			spadeTeamHandler.cleanUpPoints(spadeGame);
			spadeBidderHandler.cleanUpBid(spadeGame);
			spadePlayerHandler.cleanUpWhoHasPlayed(spadeGame);
			spadePlayerHandler.determineTurn(spadeGame);
			spadeGame.setSpadeBroken(null);
			spadeGame.setHandOver(false);
			
			spadeGame.setPreviousHand(null);
			spadeGame.setPlayerNotification(SpadeNotifications.HAND_OVER);
		}
		logger.info("Exiting: " + getClass());
		return spadeGame;
	}
		
}
