package com.craig.greggames.service.spades;

import com.craig.greggames.controller.spades.model.Game;
import com.craig.greggames.model.Hand;

public interface SpadeService {
	
	public Hand getHand(Game game);
}
