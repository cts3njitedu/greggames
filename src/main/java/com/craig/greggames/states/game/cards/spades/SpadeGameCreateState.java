package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(1)
public class SpadeGameCreateState extends AbstractSpadeGameState {

	
	@Autowired
	private SpadeGameHandler spadeGameHandler;

	private final SpadeNotifications spadeNotification = SpadeNotifications.CREATE;
	
	private static final Logger logger = Logger.getLogger(SpadeGameCreateState.class);
	
	@Value ("${spade.maxTime:60}")
	private long maxTime;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		spadeGameHandler.create(spadeGame);
		spadeGame.setMaxTime(maxTime);
		logger.info("Exiting " + getClass());
		return spadeGame;

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
