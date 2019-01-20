package com.craig.greggames.states.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(1)
public class SpadeGameCreateState extends AbstractSpadeGameState {



	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;
	
	@Autowired
	private SpadeGameHandler gameService;

	private final SpadeNotifications spadeNotification = SpadeNotifications.CREATE;
	
	@Value ("${spade.maxTime}")
	private long maxTime;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		boolean isValid = validatorEngine.validate(spadeGame);
		if(!isValid) {
			return spadePersistenceDal.saveGame(spadeGame);
		}
		gameService.create(spadeGame);
		spadeGame.setMaxTime(maxTime);
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
