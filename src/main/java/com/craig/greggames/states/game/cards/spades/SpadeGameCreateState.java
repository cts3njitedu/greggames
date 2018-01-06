package com.craig.greggames.states.game.cards.spades;

import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
@Service
@Order(1)
public class SpadeGameCreateState extends AbstractSpadeGameState{

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadePlayerHandler playerService;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	private final SpadeNotifications spadeNotification = SpadeNotifications.CREATE;
	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		switch(spadeGame.getPlayerNotification()) {
		
		case CREATE:
			spadeGame.setSpadePlayed(false);

			spadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
			spadeGame.setGameNotification(SpadeNotifications.START);
			teamService.makeTeams(spadeGame);
			return spadePersistenceDal.saveGame(spadeGame);
		default:
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			playerService.addError(spadeGame, SpadeErrors.WRONG_MOVE, oldSpadeGame);
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
