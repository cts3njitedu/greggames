package com.craig.greggames.model;

public class SpadeGame {

	private String gameId;

	private int pointsToWin;
	
	private int bags;
	
	private int overBook;
	
	private int bidNil;

	private int numberOfPlayers;
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getPointsToWin() {
		return pointsToWin;
	}

	public void setPointsToWin(int pointsToWin) {
		this.pointsToWin = pointsToWin;
	}

	
	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public int getOverBook() {
		return overBook;
	}

	public void setOverBook(int overBook) {
		this.overBook = overBook;
	}

	public int getBidNil() {
		return bidNil;
	}

	public void setBidNil(int bidNil) {
		this.bidNil = bidNil;
	}

	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	@Override
	public String toString() {
		return "SpadeGame [gameId=" + gameId + ", pointsToWin=" + pointsToWin + ", bags=" + bags + ", overBook="
				+ overBook + ", bidNil=" + bidNil + ", numberOfPlayers=" + numberOfPlayers + "]";
	}

	
	

}
