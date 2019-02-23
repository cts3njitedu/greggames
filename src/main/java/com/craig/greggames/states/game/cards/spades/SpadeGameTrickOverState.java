package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(1)
public class SpadeGameTrickOverState extends AbstractSpadeGameState {

	@Autowired
	private CardHandler cardHandler;
	private final SpadeNotifications spadeNotification = SpadeNotifications.TRICK_OVER;
	
	private static final Logger logger = Logger.getLogger(SpadeGameTrickOverState.class);

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		cardHandler.removePlayingCard(spadeGame);
		logger.info("Exiting: " + getClass());

		return spadeGame;

	}

	

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
