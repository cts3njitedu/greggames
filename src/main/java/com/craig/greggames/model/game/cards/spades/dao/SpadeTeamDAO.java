package com.craig.greggames.model.game.cards.spades.dao;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.team.TeamTable;

public class SpadeTeamDAO {
	private int totalBid;

	private int totalScore;

	private int totalBags;

	private boolean overBook;

	private Map<PlayerTable, SpadePlayerDAO> players;
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

	public int getTotalRoundBags() {
		return totalRoundBags;
	}

	public void setTotalRoundBags(int totalRoundBags) {
		this.totalRoundBags = totalRoundBags;
	}
	
	

}
