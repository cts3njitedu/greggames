package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(1)
public class SpadeGameNewPlayerState extends AbstractSpadeGameState {
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeGameNewPlayerState.class);

	private final SpadeNotifications spadeNotification = SpadeNotifications.NEW_PLAYER;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		spadeBotHandler.determineBots(spadeGame);
		spadeGame.setNewPlayer(false);
		logger.info("Exiting " + getClass());
		return spadeGame;
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
