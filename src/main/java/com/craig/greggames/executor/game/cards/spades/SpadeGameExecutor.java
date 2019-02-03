package com.craig.greggames.executor.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.game.cards.spades.SpadeGameEnricherEngine;
import com.craig.greggames.executor.game.cards.CardGameExecutor;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.postprocessor.game.cards.spades.PostProcessorExecutor;
import com.craig.greggames.preprocessor.game.cards.spades.PreProcessorExecutor;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {


	private Logger logger = Logger.getLogger(SpadeGameExecutor.class);

	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadeGameEnricherEngine enricherEngine;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private PreProcessorExecutor preProcessorExecutor;
	
	@Autowired
	private PostProcessorExecutor postProcessorExecutor;

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.debug("Spade Game State Start: "+ spadeGame.getGameId());
		try {
			logger.debug("Spade Game: " + new ObjectMapper().writeValueAsString(spadeGame));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpadeGame newSpadeGame = preProcessorExecutor.preProcess(spadeGame);
		
		if(newSpadeGame == null) {
			return spadePersistenceDal.findGame(spadeGame.getGameId());
		}
		
	
		enricherEngine.enricherEngine(newSpadeGame);
		
		boolean isValid = validatorEngine.validate(newSpadeGame);
		
		//stop before going
		if(isValid) {
			stateEngine.machine(newSpadeGame);
			
			
		}
		
		return postProcessorExecutor.postProcess(newSpadeGame);
		

	}


}
