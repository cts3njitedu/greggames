package com.craig.greggames.states.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
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
	private SpadeGameBroadCaster spadeGameBroadCaster;

	@Autowired
	private SpadeGameHandler spadeGameHandler;
	private final SpadeNotifications spadeNotification = SpadeNotifications.START;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		if(spadeGame.getPlayerNotification()==spadeGame.getGameNotification()) {
			spadeGameHandler.start(spadeGame);
			spadeGameBroadCaster.addGameToScheduler(spadeGame);
		}
		else {
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			spadePlayerHandler.addError(spadeGame, SpadeErrors.WRONG_MOVE, oldSpadeGame);
		}
	
			
		return spadePersistenceDal.saveGame(spadeGame);
		

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
