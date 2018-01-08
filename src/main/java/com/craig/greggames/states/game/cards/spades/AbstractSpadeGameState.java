package com.craig.greggames.states.game.cards.spades;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public abstract class AbstractSpadeGameState implements SpadeGameState {

	
	protected final GregGameChildTypes gregGameChildTypes= GregGameChildTypes.SPADES;

	@Override
	abstract public SpadeGame state(SpadeGame spadeGame);
	@Override
	abstract public boolean validateState(SpadeNotifications spadeNotifications); 
}
