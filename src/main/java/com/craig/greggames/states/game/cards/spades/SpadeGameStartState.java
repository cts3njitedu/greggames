package com.craig.greggames.states.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(2)
public class SpadeGameStartState extends AbstractSpadeGameState {

	@Autowired
	private SpadePlayerHandler spadePlayerHandler;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;


	@Autowired
	private SpadeGameHandler spadeGameHandler;
	private static final Logger logger = Logger.getLogger(SpadeGameStartState.class);
	private final SpadeNotifications spadeNotification = SpadeNotifications.START;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		if(spadeGame.getPlayerNotification()==spadeGame.getGameNotification()) {
			logger.info("Player currently starting");
			spadeGameHandler.start(spadeGame);
			
		}
		else {
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			spadePlayerHandler.addError(spadeGame, SpadeErrors.WRONG_MOVE, oldSpadeGame);
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
