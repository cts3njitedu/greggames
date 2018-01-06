package com.craig.greggames.executor.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.states.GreggameStateFactory;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadeService spadeService;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;
	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		boolean isValid = validatorEngine.validate(spadeGame);
		
		if(!isValid) {
			
			return spadePersistenceDal.saveGame(spadeGame);
		}
		
		
		
		return stateEngine.machine(spadeGame);
	

	}


}
