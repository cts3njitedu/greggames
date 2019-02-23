package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeGameHandOverState extends AbstractSpadeGameState {

	private final SpadeNotifications spadeNotification = SpadeNotifications.HAND_OVER;
	
	private static final Logger logger = Logger.getLogger(SpadeGameHandOverState.class);

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		return spadeGame;
	

	}

	

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
