package com.craig.greggames.service.spades;

import java.util.List;

import com.craig.greggames.model.spades.SpadeGame;

public interface SpadeService {
	
	public List<SpadeGame> getGames();
	
	public SpadeGame addGame(SpadeGame spadeGame); 
	
	public SpadeGame findGame(String gameId);
	
	public SpadeGame findGameHeaderInfo(String gameId);
	
	public SpadeGame startGame(String gameId);
}
