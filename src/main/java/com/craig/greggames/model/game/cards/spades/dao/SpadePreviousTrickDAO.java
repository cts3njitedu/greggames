package com.craig.greggames.model.game.cards.spades.dao;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadePreviousTrickDAO {

	private Map<PlayerTable, SpadePlayerDAO> players;
	
	
	public Map<PlayerTable, SpadePlayerDAO> getPlayers() {
		if (players == null||players.size()==0) {

			players = new EnumMap<PlayerTable, SpadePlayerDAO>(PlayerTable.class);

		}
		return players;
	}

	public void setPlayers(Map<PlayerTable, SpadePlayerDAO> players) {
		this.players = players;
	}
}
