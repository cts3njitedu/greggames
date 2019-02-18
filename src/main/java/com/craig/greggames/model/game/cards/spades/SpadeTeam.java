package com.craig.greggames.model.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.team.TeamTable;

//@JsonInclude(Include.NON_NULL)
public class SpadeTeam {

	private int totalBid;
	
	private int totalScore;
	
	private int totalBags;

	private boolean overBook;

	private Map<PlayerTable, SpadePlayer> players;
	private boolean won;
	private boolean lost;

	private TeamTable name;
	
	private int totalPreviousScore;
	
	private int totalRoundScore;
	
	private int totalPreviousBags;
	
	private int totalTricks;
	
	private int totalSuccessfulBids;
	private int totalFailedBids;
	private int totalBidNilScore;
	
	private int totalOverBookScore;
	
	private int totalRoundBags;
	

	public int getTotalBid() {
		return totalBid;
	}

	public void setTotalBid(int totalBid) {
		this.totalBid = totalBid;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}


	public boolean isOverBook() {
		return overBook;
	}

	public void setOverBook(boolean isOverBook) {
		this.overBook = isOverBook;
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
		return won;
	}

	public void setWon(boolean isWon) {
		this.won = isWon;
	}

	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean isLost) {
		this.lost = isLost;
	}

	public TeamTable getName() {
		return name;
	}

	public void setName(TeamTable name) {
		this.name = name;
	}
	
	

	
	public int getTotalBags() {
		return totalBags;
	}

	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}
	
	

	public int getTotalRoundBags() {
		return totalRoundBags;
	}

	public void setTotalRoundBags(int totalRoundBags) {
		this.totalRoundBags = totalRoundBags;
	}

	public int getTotalPreviousScore() {
		return totalPreviousScore;
	}

	public void setTotalPreviousScore(int totalPreviousScore) {
		this.totalPreviousScore = totalPreviousScore;
	}

	public int getTotalRoundScore() {
		return totalRoundScore;
	}

	public void setTotalRoundScore(int totalRoundScore) {
		this.totalRoundScore = totalRoundScore;
	}

	public int getTotalPreviousBags() {
		return totalPreviousBags;
	}

	public void setTotalPreviousBags(int totalPreviousBags) {
		this.totalPreviousBags = totalPreviousBags;
	}

	public int getTotalTricks() {
		return totalTricks;
	}

	public void setTotalTricks(int totalTricks) {
		this.totalTricks = totalTricks;
	}

	public int getTotalSuccessfulBids() {
		return totalSuccessfulBids;
	}

	public void setTotalSuccessfulBids(int totalSuccessfulBids) {
		this.totalSuccessfulBids = totalSuccessfulBids;
	}

	public int getTotalFailedBids() {
		return totalFailedBids;
	}

	public void setTotalFailedBids(int totalFailedBids) {
		this.totalFailedBids = totalFailedBids;
	}

	public int getTotalBidNilScore() {
		return totalBidNilScore;
	}

	public void setTotalBidNilScore(int totalBidNilScore) {
		this.totalBidNilScore = totalBidNilScore;
	}

	public int getTotalOverBookScore() {
		return totalOverBookScore;
	}

	public void setTotalOverBookScore(int totalOverBookScore) {
		this.totalOverBookScore = totalOverBookScore;
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

	

	

}
