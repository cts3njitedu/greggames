package com.craig.greggames.states.game.cards.spades;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public abstract class AbstractSpadeGameState{

	protected final GregGameChildTypes gregGameChildTypes= GregGameChildTypes.SPADES;

	abstract public SpadeGame state(SpadeGame spadeGame);
	
	abstract public boolean validateState(SpadeNotifications spadeNotifications); 
	
	
	
	
}
