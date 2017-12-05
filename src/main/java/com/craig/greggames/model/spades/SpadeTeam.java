package com.craig.greggames.model.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.player.PlayerTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
public class SpadeTeam {

	private int totalBid;

	private int totalCurrentScore;

	private int totalScore;

	private int bags;

	private boolean isOverBook;

	private Map<PlayerTable, SpadePlayer> players;
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

	
	public Map<PlayerTable, SpadePlayer> getPlayers() {
		if (players == null||players.size()==0) {

			players = new EnumMap<PlayerTable, SpadePlayer>(PlayerTable.class);

		}
		return players;
	}

	public void setPlayers(Map<PlayerTable, SpadePlayer> players) {
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpadeTeam other = (SpadeTeam) obj;
		if (name != other.name)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpadeTeam [totalBid=" + totalBid + ", totalCurrentScore=" + totalCurrentScore + ", totalScore="
				+ totalScore + ", bags=" + bags + ", isOverBook=" + isOverBook + ", players=" + players + ", isWon="
				+ isWon + ", isLost=" + isLost + ", name=" + name + "]";
	}

	

}
