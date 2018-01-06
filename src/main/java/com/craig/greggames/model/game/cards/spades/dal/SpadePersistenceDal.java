package com.craig.greggames.model.game.cards.spades.dal;

import java.util.List;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

public interface SpadePersistenceDal {

	public List<SpadeGame> getGames();

	public SpadeGame findGame(String gameId);

	public SpadeGame findGameHeaderInfo(String gameId);

	public SpadeGame startGame(String gameId);

	public SpadeGame saveGame(SpadeGame spadeGame);


}
