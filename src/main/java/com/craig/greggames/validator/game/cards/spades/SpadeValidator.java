package com.craig.greggames.validator.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;


public interface SpadeValidator {

	
	public boolean validate(SpadeGame spadeGame);
	
	public boolean validateState(SpadeNotifications spadeNotifications);
}
