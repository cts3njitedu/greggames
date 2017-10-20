package com.craig.cards.spades.model;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.craig.cards.model.TeamTable;

public class Game {

	@Id
	private String gameId;
	private int trickCount;
	private int handCount;
	private int score;
	private boolean isGameOver;
	private boolean isDealing;
	private boolean isTeams;
	private Map<TeamTable, Team> teams;

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getTrickCount() {
		return trickCount;
	}

	public void setTrickCount(int trickCount) {
		this.trickCount = trickCount;
	}

	public int getHandCount() {
		return handCount;
	}

	public void setHandCount(int handCount) {
		this.handCount = handCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isDealing() {
		return isDealing;
	}

	public void setDealing(boolean isDealing) {
		this.isDealing = isDealing;
	}

	public boolean isTeams() {
		return isTeams;
	}

	public void setTeams(boolean isTeams) {
		this.isTeams = isTeams;
	}

	public Map<TeamTable, Team> getTeams() {
		if (teams == null) {
			teams = new EnumMap<TeamTable, Team>(TeamTable.class);
		}
		return teams;
	}

	public void setTeams(Map<TeamTable, Team> teams) {
		this.teams = teams;
	}

}
