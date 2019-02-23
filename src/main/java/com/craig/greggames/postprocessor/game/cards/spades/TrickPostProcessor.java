package com.craig.greggames.postprocessor.game.cards.spades;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrickPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private CardHandler cardHandler;
	
	
	private Set<SpadeNotifications> spadeNotifications = new HashSet<>(Arrays.asList(SpadeNotifications.BID,SpadeNotifications.PLAY));
	
	private Set<SpadeNotifications> playerChangeNotifications = 
			new HashSet<>(Arrays.asList(SpadeNotifications.LEAVE_GAME,SpadeNotifications.NEW_PLAYER));
	private Logger logger = Logger.getLogger(TrickPostProcessor.class);
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return saveGame(spadeGame);
	}
	
	@Value ("${spade.maxTime:60}")
	private long maxTime;
	
	private long maxNotificationTime = 2;
	
	private  SpadeGame saveGame(SpadeGame spadeGame) {
		
		logger.info("Entering: "+getClass());
		if(spadeGame.isTrickOver()) {
			simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId(), spadeGame);
			
			try {
				logger.info("Pausing game id: "+spadeGame.getGameId());
				TimeUnit.SECONDS.sleep(maxNotificationTime);
				logger.info("Finish pausing game id: "+spadeGame.getGameId());
				cardHandler.removePlayingCard(spadeGame);
				spadeGame.setTrickOver(false);
				spadeGame.setPreviousTrick(null);
				spadeGame.setPlayerNotification(SpadeNotifications.TRICK_OVER);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("Exiting: " + getClass());
		return spadeGame;
	}
		
}
