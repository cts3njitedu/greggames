package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.postprocessor.game.cards.spades.BroadcastPostProcessor;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(3)
public class SpadeGameBidState extends AbstractSpadeGameState {

	
	@Autowired
	private SpadeBotHandler spadeBotHandler;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	
	@Autowired
	private SpadeGameHandler spadeGameHandler;
	private final SpadeNotifications spadeNotification = SpadeNotifications.BID;
	
	private static final Logger logger = Logger.getLogger(SpadeGameBidState.class);

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		logger.info("Entering " + getClass());
		if(spadeGame.getPlayerNotification()==spadeGame.getGameNotification()) {
			logger.info("Player notification is bidding");
			spadeGameHandler.bid(spadeGame);
		}
		else {
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			spadePlayerHandler.addError(spadeGame, SpadeErrors.CURRENTLY_BIDDING, oldSpadeGame);	
		}
		logger.info("Exiting " + getClass());
		return spadeGame;

	}

	

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
