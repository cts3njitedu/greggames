package com.craig.greggames.model.game.cards.spades.dal;

import java.util.List;
import java.util.Set;

import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;

public interface SpadePersistenceDal {

	public List<SpadeGame> getGames();

	public SpadeGame findGame(String gameId);

	public SpadeGame findGameHeaderInfo(String gameId);

	public SpadeGame startGame(String gameId);

	public SpadeGame saveGame(SpadeGame spadeGame);
	
	public SpadeGame updateLockingField(SpadeGame spadeGame);
	
	public SpadeGame updateGame(SpadeGame spadeGame,Set<String>excludedFields, Set<String>makeEmptyIfNull);

	public SpadeGame updateActionField(SpadeGame spadeGame, AsyncState newAsyncState);
	public List<SpadeGame> findGamesByGameNotification(List<SpadeNotifications>spadeNotifications);
}
