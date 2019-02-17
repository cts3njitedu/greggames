package com.craig.greggames.model.game.cards.player;

public enum PlayerPosition {

	PLAYERSOUTH(1,"SOUTH"),
	PLAYERWEST(2,"WEST"),
	PLAYERNORTH(3,"NORTH"),
	PLAYEREAST(4,"EAST");
	
	private int code;
	private String direction;
	private PlayerPosition(int code, String direction) {
		this.code = code;
		this.direction = direction;
	}
	
	public static PlayerPosition getPlayerPosition(int c) {
		
		for(PlayerPosition playerPosition: PlayerPosition.values()) {
			if(playerPosition.getCode()==c) {
				return playerPosition;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getDirection() {
		return direction;
	}
	
	
	
}
