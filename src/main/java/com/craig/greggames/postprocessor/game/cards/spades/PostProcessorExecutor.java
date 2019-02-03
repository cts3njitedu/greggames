package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class PostProcessorExecutor {

	@Autowired
	private List<AbstractPostProcessor> abstractPostProcessors;
	
	private Logger logger = Logger.getLogger(PostProcessorExecutor.class);
	
	public SpadeGame postProcess(SpadeGame spadeGame) {
		logger.info("Entering " + getClass());
		SpadeGame newSpadeGame = spadeGame;
		for(AbstractPostProcessor abstractPostProcessor: abstractPostProcessors) {
			newSpadeGame = abstractPostProcessor.postProcess(newSpadeGame);
			
		}
		
		logger.info("Exiting "+ getClass());
		
		return newSpadeGame;
	}
}
