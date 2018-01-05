package com.craig.greggames.model;

public enum GregGameChildTypes {

	SPADES("Spades",GreggameParentTypes.CARD),
	SOLITAIRE("Solitaire",GreggameParentTypes.BOARD);
	
	
	private String name;
	private GreggameParentTypes type;
	private GregGameChildTypes(String name, GreggameParentTypes type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}

	public GreggameParentTypes getType() {
		return type;
	}
	
	
	
	
	
	
}
