package com.craig.greggames.model.game.cards.spades;

import java.util.HashMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerPosition;
import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadePlayerView {

	private PlayerTable player;
	private Map<PlayerPosition, SpadePlayer> playerPositions;
	
	
	public PlayerTable getPlayer() {
		return player;
	}
	public void setPlayer(PlayerTable player) {
		this.player = player;
	}
	public Map<PlayerPosition, SpadePlayer> getPlayerPositions() {
		if(playerPositions==null) {
			playerPositions=new HashMap<>();
		}
		return playerPositions;
	}
	public void setPlayerPositions(Map<PlayerPosition, SpadePlayer> playerPositions) {
		this.playerPositions = playerPositions;
	}
	
	
	
	
	
	
	
}
