package com.craig.greggames.enrichers.game.cards.spades;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public interface SpadeGameEnricher {

	public void enricher(SpadeGame spadeGame);
	
	public boolean validateState(SpadeNotifications spadeNotification);
}
