package com.craig.greggames.broadcast.game.cards.spades;

import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.cards.spades.SpadeGame;

public interface SpadeAsyncStateProcess {

	
	public boolean isValidAsyncTask(AsyncState asyncState);
	
	public SpadeGame processAsyncTask(SpadeGame spadeGame);
}
