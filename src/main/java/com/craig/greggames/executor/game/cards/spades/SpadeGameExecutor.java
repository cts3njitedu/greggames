package com.craig.greggames.executor.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.game.cards.spades.SpadeBroadCasterEnricher;
import com.craig.greggames.enrichers.game.cards.spades.SpadeGameEnricherEngine;
import com.craig.greggames.executor.game.cards.CardGameExecutor;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.postprocessor.game.cards.spades.PostProcessorExecutor;
import com.craig.greggames.preprocessor.game.cards.spades.PreProcessorExecutor;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {


	

	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadeGameEnricherEngine enricherEngine;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBroadCasterEnricher spadeBroadCasterEnricher;
	
	@Autowired
	private PreProcessorExecutor preProcessorExecutor;
	
	@Autowired
	private PostProcessorExecutor postProcessorExecutor;

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGame newSpadeGame = preProcessorExecutor.preProcess(spadeGame);
		
		if(newSpadeGame == null) {
			return spadePersistenceDal.findGame(spadeGame.getGameId());
		}
		
	
		enricherEngine.enricherEngine(newSpadeGame);
		
		boolean isValid = validatorEngine.validate(newSpadeGame);
		
		//stop before going
		if(isValid) {
			stateEngine.machine(spadeGame);
			
			
		}
		
		return postProcessorExecutor.postProcess(newSpadeGame);
		

	}


}
