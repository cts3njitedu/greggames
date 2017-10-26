package com.craig.greggames.model;

public class SpadeGame {

	private String gameId;

	private int pointsToWin;

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

	@Override
	public String toString() {
		return "SpadeGame [gameId=" + gameId + ", pointsToWin=" + pointsToWin + "]";
	}
	
	

}
