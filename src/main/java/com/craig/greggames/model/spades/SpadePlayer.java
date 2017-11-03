package com.craig.greggames.model.spades;

import com.craig.greggames.model.player.Player;

public class SpadePlayer extends Player {

	private int playerBid;
	private int playerCurrentScore;
	private int playerPreviousScore;
	private boolean isFailBid;
	private boolean isOverBook;
	private int playerBags;
	private boolean isBidding;
	

	public int getPlayerBid() {
		return playerBid;
	}

	public void setPlayerBid(int playerBid) {
		this.playerBid = playerBid;
	}

	public int getPlayerCurrentScore() {
		return playerCurrentScore;
	}

	public void setPlayerCurrentScore(int playerCurrentScore) {
		this.playerCurrentScore = playerCurrentScore;
	}

	public int getPlayerBags() {
		return playerBags;
	}

	public void setPlayerBags(int playerBags) {
		this.playerBags = playerBags;
	}

	public boolean isBidding() {
		return isBidding;
	}

	public void setBidding(boolean isBidding) {
		this.isBidding = isBidding;
	}
	

	public int getPlayerPreviousScore() {
		return playerPreviousScore;
	}

	public void setPlayerPreviousScore(int playerPreviousScore) {
		this.playerPreviousScore = playerPreviousScore;
	}
	
	

	public boolean isFailBid() {
		return isFailBid;
	}

	public void setFailBid(boolean isFailBid) {
		this.isFailBid = isFailBid;
	}

	public boolean isOverBook() {
		return isOverBook;
	}

	public void setOverBook(boolean isOverBook) {
		this.isOverBook = isOverBook;
	}

	@Override
	public String toString() {
		return "SpadePlayer [playerBid=" + playerBid + ", playerCurrentScore=" + playerCurrentScore + ", playerBags="
				+ playerBags + ", isBidding=" + isBidding + ", getPlayerBid()=" + getPlayerBid()
				+ ", getPlayerCurrentScore()=" + getPlayerCurrentScore() + ", getPlayerBags()=" + getPlayerBags()
				+ ", isBidding()=" + isBidding() + ", getUserId()=" + getUserId() + ", getName()=" + getName()
				+ ", getRank()=" + getRank() + ", getPlayingCard()=" + getPlayingCard() + ", isTurn()=" + isTurn()
				+ ", isWon()=" + isWon() + ", getRemainingCards()=" + getRemainingCards() + ", getTeam()=" + getTeam()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
