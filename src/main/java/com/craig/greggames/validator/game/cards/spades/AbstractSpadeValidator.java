package com.craig.greggames.validator.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public abstract class AbstractSpadeValidator implements SpadeValidator {

	
	@Override
	abstract public boolean validate(SpadeGame spadeGame);
	@Override
	abstract public boolean validateState(SpadeNotifications spadeNotifications);

}
