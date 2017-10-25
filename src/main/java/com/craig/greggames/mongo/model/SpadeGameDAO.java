package com.craig.greggames.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spadegame")
public class SpadeGameDAO {

	@Id
	private String gameId;

	private int pointsToWin;

	private boolean isActive;

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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
