package com.craig.greggames.service.message;

import com.craig.greggames.model.spades.SpadeGame;

public interface MessageService {
	
	public SpadeGame addGame(SpadeGame spadeGame);
	
	public SpadeGame getGame(String gameId);
	
	public SpadeGame modifyGameState(String gameType, String gameId, SpadeGame spadeGame);

}
