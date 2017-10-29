package com.craig.greggames.model;

import java.util.ArrayList;
import java.util.List;

public enum PlayerTable {

	PLAYER1(1), PLAYER2(2), PLAYER3(3), PLAYER4(4);

	private int code;

	private PlayerTable(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	
	public static PlayerTable getPlayer(int code) {
		
		List<PlayerTable> players = new ArrayList<PlayerTable>();
		
		for(PlayerTable player: players) {
			
			if(player.getCode()==code) {
				return player;
			}
		}
		return null;
	}

}
