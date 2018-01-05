package com.craig.greggames.model;

public enum GreggameParentTypes {

	CARD("CARD"),
	BOARD("BOARD");
	
	private String name;

	private GreggameParentTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	
}
