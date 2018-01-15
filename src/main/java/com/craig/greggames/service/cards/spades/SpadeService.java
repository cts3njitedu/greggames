package com.craig.greggames.service.cards.spades;

import java.util.List;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

public interface SpadeService {
	
	public List<SpadeGame> getGames();
	
	public SpadeGame findGame(String gameId);
	
	public SpadeGame findGameHeaderInfo(String gameId);
	
	public SpadeGame playGame(SpadeGame spadeGame);
	
	
	
}
