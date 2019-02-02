package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class PostProcessorExecutor {

	@Autowired
	private List<AbstractPostProcessor> abstractPostProcessors;
	
	
	
	public SpadeGame postProcess(SpadeGame spadeGame) {
		SpadeGame newSpadeGame = spadeGame;
		for(AbstractPostProcessor abstractPostProcessor: abstractPostProcessors) {
			newSpadeGame = abstractPostProcessor.postProcess(newSpadeGame);
		}
		
		return newSpadeGame;
	}
}
