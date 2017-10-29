package com.craig.greggames.mongo.model;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.Player;
import com.craig.greggames.model.PlayerTable;

public class SpadeTeamDAO {
	private int totalBid;

	private int totalCurrentScore;

	private int totalScore;

	private int bags;

	private boolean isOverBook;

	private Map<PlayerTable, Player> players;
	private boolean isWon;

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

	public Map<PlayerTable, Player> getPlayers() {
		if (players == null) {

			players = new EnumMap<PlayerTable, Player>(PlayerTable.class);
		}
		return players;
	}

	public void setPlayers(Map<PlayerTable, Player> players) {
		this.players = players;
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean isWon) {
		this.isWon = isWon;
	}

}