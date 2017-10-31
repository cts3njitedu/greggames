package com.craig.greggames.controller.spades.model;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.Game;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpadeGame extends Game {

	private String gameId;

	private boolean isStarting;

	private int pointsToWin;

	private PlayerTable startHand;
	private PlayerTable startTrick;

	private PlayerTable currTrick;
	private PlayerTable endTrick;
	private int trickCount;

	private int handCount;
	private boolean isGameOver;

	private Map<TeamTable, Team> teams;

	private boolean isDealing;
	private int numberDeals;
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

	public boolean isStarting() {
		return isStarting;
	}

	public void setStarting(boolean isStarting) {
		this.isStarting = isStarting;
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

	

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public Map<TeamTable, Team> getTeams() {
		if (teams == null) {

			teams = new EnumMap<TeamTable, Team>(TeamTable.class);
			
			for(TeamTable team: TeamTable.values()) {
				
				teams.put(team, new Team());
			}
			
			
		}
		return teams;
	}

	public void setTeams(Map<TeamTable, Team> teams) {
		this.teams = teams;
	}

	public boolean isDealing() {
		return isDealing;
	}

	public void setDealing(boolean isDealing) {
		this.isDealing = isDealing;
	}

	public int getNumberDeals() {
		return numberDeals;
	}

	public void setNumberDeals(int numberDeals) {
		this.numberDeals = numberDeals;
	}

	public PlayerTable getStartHand() {
		return startHand;
	}

	public void setStartHand(PlayerTable startHand) {
		this.startHand = startHand;
	}

	public PlayerTable getStartTrick() {
		return startTrick;
	}

	public void setStartTrick(PlayerTable startTrick) {
		this.startTrick = startTrick;
	}
	
	



	public PlayerTable getCurrTrick() {
		return currTrick;
	}

	public void setCurrTrick(PlayerTable currTrick) {
		this.currTrick = currTrick;
	}
	
	

	public PlayerTable getEndTrick() {
		return endTrick;
	}

	public void setEndTrick(PlayerTable endTrick) {
		this.endTrick = endTrick;
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

	@Override
	public String toString() {
		return "SpadeGame [gameId=" + gameId + ", isStarting=" + isStarting + ", pointsToWin=" + pointsToWin
				+ ", startHand=" + startHand + ", startTrick=" + startTrick + ", currTrick=" + currTrick + ", endTrick="
				+ endTrick + ", trickCount=" + trickCount + ", handCount=" + handCount + ", isGameOver=" + isGameOver
				+ ", teams=" + teams + ", isDealing=" + isDealing + ", numberDeals=" + numberDeals + ", bags=" + bags
				+ ", overBook=" + overBook + ", bidNil=" + bidNil + ", numberOfPlayers=" + numberOfPlayers + "]";
	}

	

}
