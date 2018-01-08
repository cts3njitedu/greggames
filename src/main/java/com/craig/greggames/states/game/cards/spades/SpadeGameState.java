package com.craig.greggames.states.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public interface SpadeGameState{



	public SpadeGame state(SpadeGame spadeGame);
	
	public boolean validateState(SpadeNotifications spadeNotifications); 
	
	
	
	
}
