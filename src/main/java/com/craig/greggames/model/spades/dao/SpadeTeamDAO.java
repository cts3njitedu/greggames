package com.craig.greggames.model.spades.dao;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.team.TeamTable;

public class SpadeTeamDAO {
	private int totalBid;

	private int totalCurrentScore;

	private int totalScore;

	private int bags;

	private boolean isOverBook;

	private Map<PlayerTable, SpadePlayerDAO> players;
	private boolean isWon;
	private boolean isLost;

	private TeamTable name;

	public int getTotalBid() {
		return totalBid;
	}

	public void setTotalBid(int totalBid) {
		this.totalBid = totalBid;
	}

	public int getTotalCurrentScore() {
		return totalCurrentScore;
	}

	public void setTotalCurrentScore(int totalCurrentScore) {
		this.totalCurrentScore = totalCurrentScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public boolean isOverBook() {
		return isOverBook;
	}

	public void setOverBook(boolean isOverBook) {
		this.isOverBook = isOverBook;
	}

	public Map<PlayerTable, SpadePlayerDAO> getPlayers() {
		if (players == null) {

			players = new EnumMap<PlayerTable, SpadePlayerDAO>(PlayerTable.class);

		}
		return players;
	}

	public void setPlayers(Map<PlayerTable, SpadePlayerDAO> players) {
		this.players = players;
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean isWon) {
		this.isWon = isWon;
	}

	public boolean isLost() {
		return isLost;
	}

	public void setLost(boolean isLost) {
		this.isLost = isLost;
	}

	public TeamTable getName() {
		return name;
	}

	public void setName(TeamTable name) {
		this.name = name;
	}

}
