package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class PostProcessorExecutor {

	@Autowired
	private List<AbstractPostProcessor> abstractPostProcessors;
	
	
	
	private static final Logger logger = Logger.getLogger(PostProcessorExecutor.class);
	
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
