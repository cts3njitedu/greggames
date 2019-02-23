package com.craig.greggames.model.error.game.cards.spades;

import java.util.HashMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public class SpadePlayerMessage {
	
	private boolean error;
	
	private boolean hasMessage;
	
	private Map<SpadeErrors,String> errorMessages;
	
	private PlayerTable player;
	
	private SpadeNotifications notification;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Map<SpadeErrors, String> getErrorMessages() {
		if(errorMessages==null) {
			errorMessages = new HashMap<>();
		}
		return errorMessages;
	}

	public void setErrorMessages(Map<SpadeErrors, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public PlayerTable getPlayer() {
		return player;
	}

	public void setPlayer(PlayerTable player) {
		this.player = player;
	}

	public SpadeNotifications getNotification() {
		return notification;
	}

	public void setNotification(SpadeNotifications notification) {
		this.notification = notification;
	}

	public boolean isHasMessage() {
		return hasMessage;
	}

	public void setHasMessage(boolean hasMessage) {
		this.hasMessage = hasMessage;
	}
	
	
	

	
	
	
	

}
