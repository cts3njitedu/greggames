package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpadeGameErrorState extends AbstractSpadeGameState {



	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeGameErrorState.class);
	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
		spadePlayerHandler.addError(spadeGame, SpadeErrors.WAITING_FOR_OTHER_PLAYERS, oldSpadeGame);
		logger.info("Exiting " + getClass());
		return spadePersistenceDal.saveGame(spadeGame);
		
		

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		//this should run last. this means that others state were invalid
		return true;
	}

}
