package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.executor.game.cards.spades.SpadeGameExecutor;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeBotEnricher extends AbstractSpadeGameEnricher {

	@Autowired
	private SpadeBotHandler spadeBidderHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeBotEnricher.class);
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.BID);
		notificationSet.add(SpadeNotifications.PLAY);
		
	

	}
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		if(spadeGame.isServerPlaying()) {
			logger.info("Entering Spade Bot Enricher with player action of  " +spadeGame.getGameNotification());
			switch (spadeGame.getGameNotification()) {

			case PLAY:
				spadeBidderHandler.determineBotCard(spadeGame);
				break;
			case BID:
				spadeBidderHandler.getBotBid(spadeGame);
				break;
			default:
				spadeBidderHandler.getBotBid(spadeGame);
				break;
			}
			
		}
		
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
