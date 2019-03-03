package com.craig.greggames.model.game;

public enum AsyncState {

	PROCESSING ("PROCESSING"),
	NEAR_ACTION ("NEAR_ACTION"),
	ACTION ("ACTION"),
	READY ("READY"),
	LOCK ("LOCK"),
	EXIT ("EXIT");
	
	private String code;

	private AsyncState(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}	
	
}
