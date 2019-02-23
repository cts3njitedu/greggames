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
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(4)
public class SpadeGamePlayState extends AbstractSpadeGameState {

	@Autowired
	private SpadePlayerHandler spadePlayerHandler;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;

	private static final Logger logger = Logger.getLogger(SpadeGamePlayState.class);

	@Autowired
	private SpadeGameHandler spadeGameHandler;

	private final SpadeNotifications spadeNotification = SpadeNotifications.PLAY;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		if(spadeGame.getPlayerNotification()==spadeGame.getGameNotification()) {
			logger.info("Player notification is playing");
			spadeGameHandler.play(spadeGame);
			
		}
		else {
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			spadePlayerHandler.addError(spadeGame, SpadeErrors.CURRENTLY_PLAYING, oldSpadeGame);
		}
		logger.info("Exiting " + getClass());
		return spadeGame;
//		switch (spadeGame.getPlayerNotification()) {
//
//		case PLAY:
//			spadeGameHandler.play(spadeGame);
//			//before you save add to map
//			return spadePersistenceDal.saveGame(spadeGame);
//		case NEW_PLAYER:
//			spadeBotHandler.determineBots(spadeGame);
//			spadeGame.setNewPlayer(false);
//			spadeGame.setActivePlayers(spadeGame.getActivePlayers() + 1);
//			return spadePersistenceDal.saveGame(spadeGame);
//		case LEAVE_GAME:
//			spadeGameHandler.leaveGame(spadeGame);
//			return spadePersistenceDal.saveGame(spadeGame);
//		case RECEIVED_ERROR:
//			spadePlayerHandler.cleanUpError(spadeGame);
//			return spadePersistenceDal.saveGame(spadeGame);
//		default:
//			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
//			spadePlayerHandler.addError(spadeGame, SpadeErrors.CURRENTLY_PLAYING, oldSpadeGame);
//			return spadePersistenceDal.saveGame(spadeGame);
//
//		}

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
		
	}

}
