package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(1)
public class SpadeGameReceiveErrorState extends AbstractSpadeGameState {

	private static final Logger logger = Logger.getLogger(SpadeGameReceiveErrorState.class);
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;

	private final SpadeNotifications spadeNotification = SpadeNotifications.RECEIVED_ERROR;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		spadePlayerHandler.cleanUpError(spadeGame);
		logger.info("Exiting " + getClass());
		
		return spadeGame;
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
