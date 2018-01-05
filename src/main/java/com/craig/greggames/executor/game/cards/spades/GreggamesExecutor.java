package com.craig.greggames.executor.game.cards.spades;

import com.craig.greggames.model.AbstractGreggame;

public interface GreggamesExecutor <T extends AbstractGreggame>{

	public void create(T greggame);
	
	public void execute(T greggame);


}
