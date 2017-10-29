package com.craig.greggames.service.message;

import com.craig.greggames.controller.spades.model.SpadeGame;

public interface MessageService {
	
	public SpadeGame addGame(SpadeGame spadeGame);
	
	public SpadeGame getGame(String gameId);

}
