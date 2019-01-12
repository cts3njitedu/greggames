package com.craig.greggames.model.game.cards.spades;

import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadeBroken {
	
	
	private PlayerTable player;
	
	private int trickCount;
	
	private boolean isFirstSpade;

	public PlayerTable getPlayer() {
		return player;
	}

	public void setPlayer(PlayerTable player) {
		this.player = player;
	}

	public int getTrickCount() {
		return trickCount;
	}

	public void setTrickCount(int trickCount) {
		this.trickCount = trickCount;
	}

	public boolean isFirstSpade() {
		return isFirstSpade;
	}

	public void setFirstSpade(boolean isFirstSpade) {
		this.isFirstSpade = isFirstSpade;
	}
	
	
	
	
	

}
