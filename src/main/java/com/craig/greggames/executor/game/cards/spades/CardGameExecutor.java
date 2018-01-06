package com.craig.greggames.executor.game.cards.spades;

import com.craig.greggames.model.game.cards.CardGame;

public abstract class CardGameExecutor <T extends CardGame> implements GreggamesExecutor<T>{

	
	@Override
	abstract public T execute(T cardGame);
	

	
}
