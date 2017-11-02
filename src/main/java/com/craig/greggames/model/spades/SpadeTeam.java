package com.craig.greggames.model.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.player.Player;
import com.craig.greggames.model.player.PlayerTable;

public class SpadeTeam {

	private int totalBid;

	private int totalCurrentScore;

	private int totalScore;

	private int bags;

	private boolean isOverBook;

	private Map<PlayerTable,SpadePlayer> players;
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

	public Map<PlayerTable, SpadePlayer> getPlayers() {
		if(players==null) {
			
			players = new EnumMap<PlayerTable,SpadePlayer>(PlayerTable.class);
			
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

	@Override
	public String toString() {
		return "Team [totalBid=" + totalBid + ", totalCurrentScore=" + totalCurrentScore + ", totalScore=" + totalScore
				+ ", bags=" + bags + ", isOverBook=" + isOverBook + ", players=" + players + ", isWon=" + isWon
				+ ", getTotalBid()=" + getTotalBid() + ", getTotalCurrentScore()=" + getTotalCurrentScore()
				+ ", getTotalScore()=" + getTotalScore() + ", getBags()=" + getBags() + ", isOverBook()=" + isOverBook()
				+ ", getPlayers()=" + getPlayers() + ", isWon()=" + isWon() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

	

}
