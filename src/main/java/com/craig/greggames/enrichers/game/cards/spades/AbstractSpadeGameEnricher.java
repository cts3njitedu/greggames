package com.craig.greggames.enrichers.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public abstract class AbstractSpadeGameEnricher implements SpadeGameEnricher {

	@Override
	abstract public void enricher(SpadeGame spadeGame);
	@Override
	abstract public boolean validateState(SpadeNotifications spadeNotification);
}
