package com.craig.greggames.states.game.cards.spades;

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
	private SpadePlayerHandler playerService;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;

	@Autowired
	private SpadeBotHandler botService;

	@Autowired
	private SpadeValidatorEngine validatorEngine;

	@Autowired
	private SpadeGameHandler gameService;

	private final SpadeNotifications spadeNotification = SpadeNotifications.PLAY;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		switch (spadeGame.getPlayerNotification()) {

		case PLAY:
			boolean isValid = validatorEngine.validate(spadeGame);
			if (!isValid) {
				return spadePersistenceDal.saveGame(spadeGame);
			}
			gameService.play(spadeGame);
			return spadePersistenceDal.saveGame(spadeGame);
		case NEW_PLAYER:
			botService.determineBots(spadeGame);
			spadeGame.setNewPlayer(false);
			spadeGame.setActivePlayers(spadeGame.getActivePlayers() + 1);
			return spadePersistenceDal.saveGame(spadeGame);
		case RECEIVED_ERROR:
			playerService.cleanUpError(spadeGame);
			return spadePersistenceDal.saveGame(spadeGame);
		default:
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			playerService.addError(spadeGame, SpadeErrors.CURRENTLY_PLAYING, oldSpadeGame);
			return spadePersistenceDal.saveGame(spadeGame);

		}

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		if (spadeNotification == spadeNotifications) {
			return true;
		}
		return false;
	}

}
