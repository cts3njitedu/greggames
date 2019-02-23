package com.craig.greggames.preprocessor.game.cards.spades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class PreProcessorExecutor {

	@Autowired
	private List<AbstractPreProcessor> preProcessors;
	
	private static final Logger logger = Logger.getLogger(PreProcessorExecutor.class);
	
	public SpadeGame preProcess(SpadeGame spadeGame) {
		
		SpadeGame newSpadeGame = spadeGame;
		logger.info("Entering spade game preprocessor...");
		for(AbstractPreProcessor abstractPreProcessor: preProcessors) {
			if(abstractPreProcessor.isValidState(spadeGame)) {
				newSpadeGame = abstractPreProcessor.preProcess(newSpadeGame);
				if(newSpadeGame==null) {
					return null;
				}
			}
		}
		logger.info("Exiting spade game preprocessor...");
		return newSpadeGame;
	}
}
