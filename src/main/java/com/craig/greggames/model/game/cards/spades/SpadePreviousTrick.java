package com.craig.greggames.model.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadePreviousTrick {

	private Map<PlayerTable, SpadePlayer> players;
	
	
	public Map<PlayerTable, SpadePlayer> getPlayers() {
		if (players == null||players.size()==0) {

			players = new EnumMap<PlayerTable, SpadePlayer>(PlayerTable.class);

		}
		return players;
	}

	public void setPlayers(Map<PlayerTable, SpadePlayer> players) {
		this.players = players;
	}
}
