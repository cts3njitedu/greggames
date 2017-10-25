package com.craig.greggames.controller.spades.model;

public class Trick {

	private int trickCount;
	private SpadePlayer winner;

	public int getTrickCount() {
		return trickCount;
	}

	public void setTrickCount(int trickCount) {
		this.trickCount = trickCount;
	}

	public SpadePlayer getWinner() {
		return winner;
	}

	public void setWinner(SpadePlayer winner) {
		this.winner = winner;
	}

}
