package com.craig.greggames.executor.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.game.cards.spades.SpadeGameEnricherEngine;
import com.craig.greggames.executor.game.cards.CardGameExecutor;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;

@Service
public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {


	

	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadeGameEnricherEngine enricherEngine;

	

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		enricherEngine.enricherEngine(spadeGame);
		
		return stateEngine.machine(spadeGame);
	

	}


}
