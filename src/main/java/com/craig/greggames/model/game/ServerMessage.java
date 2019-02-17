package com.craig.greggames.model.game;

public enum ServerMessage {

	TIME_IS_ALMOST_UP("Time is almost up!!!!"), 
	SERVER_PLAYING("Server is playing...");

	private String error;

	private ServerMessage(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
