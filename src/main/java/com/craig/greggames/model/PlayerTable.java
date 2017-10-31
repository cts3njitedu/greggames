package com.craig.greggames.model;

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
		
		
		
		for(PlayerTable player: PlayerTable.values()) {
			
			if(player.getCode()==code) {
				return player;
			}
		}
		return null;
	}

}
