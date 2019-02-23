package com.craig.greggames.model.game.cards.spades;

import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadeGameAbridged {

	private String gameId;
	
	private SpadeNotifications playerNotification;
	
	private PlayerTable gameModifier;
	
	private SpadePlayer spadePlayer;

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public SpadeNotifications getPlayerNotification() {
		return playerNotification;
	}

	public void setPlayerNotification(SpadeNotifications playerNotification) {
		this.playerNotification = playerNotification;
	}

	public PlayerTable getGameModifier() {
		return gameModifier;
	}

	public void setGameModifier(PlayerTable gameModifier) {
		this.gameModifier = gameModifier;
	}

	public SpadePlayer getSpadePlayer() {
		return spadePlayer;
	}

	public void setSpadePlayer(SpadePlayer spadePlayer) {
		this.spadePlayer = spadePlayer;
	}
	
	
	
	
}
