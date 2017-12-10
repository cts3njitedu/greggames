package com.craig.greggames.service.cards.spades;

import java.util.List;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

public interface SpadeService {
	
	public List<SpadeGame> getGames();
	
	public SpadeGame createGame(SpadeGame spadeGame); 
	
	public SpadeGame findGame(String gameId);
	
	public SpadeGame findGameHeaderInfo(String gameId);
	
	public SpadeGame startGame(String gameId);
	
	public SpadeGame saveGame(SpadeGame spadeGame);
	
	public SpadeGame modifyGameState(String gameType, String gameId, SpadeGame spadeGame);
}
