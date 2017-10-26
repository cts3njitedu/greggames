package com.craig.greggames.service.spades;

import java.util.List;

import com.craig.greggames.model.SpadeGame;

public interface SpadeService {
	
	public List<SpadeGame> getGames();
	
	public SpadeGame addGame(SpadeGame spadeGame); 
}
