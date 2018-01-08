package com.craig.greggames.states.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(1)
public class SpadeGameCreateState extends AbstractSpadeGameState {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;
	
	@Autowired
	private SpadeGameHandler gameService;

	private final SpadeNotifications spadeNotification = SpadeNotifications.CREATE;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		boolean isValid = validatorEngine.validate(spadeGame);
		if(!isValid) {
			return spadePersistenceDal.saveGame(spadeGame);
		}
		gameService.create(spadeGame);
		return spadePersistenceDal.saveGame(spadeGame);

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
