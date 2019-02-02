package com.craig.greggames.preprocessor.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

public abstract class AbstractPreProcessor {

	
	abstract boolean isValidState(SpadeGame spadeGame);
	
	abstract SpadeGame preProcess(SpadeGame spadeGame);
}
