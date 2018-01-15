package com.craig.greggames.executor.game.cards;

import com.craig.greggames.executor.GreggamesExecutor;
import com.craig.greggames.model.game.cards.CardGame;

public abstract class CardGameExecutor <T extends CardGame> implements GreggamesExecutor<T>{

	
	@Override
	abstract public T execute(T cardGame);
	

	
}
