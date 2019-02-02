package com.craig.greggames.preprocessor.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class PreProcessorExecutor {

	@Autowired
	private List<AbstractPreProcessor> preProcessors;
	
	
	public SpadeGame preProcess(SpadeGame spadeGame) {
		SpadeGame newSpadeGame = spadeGame;
		for(AbstractPreProcessor abstractPreProcessor: preProcessors) {
			if(abstractPreProcessor.isValidState(spadeGame)) {
				newSpadeGame = abstractPreProcessor.preProcess(newSpadeGame);
				if(newSpadeGame==null) {
					return null;
				}
			}
		}
		return newSpadeGame;
	}
}
