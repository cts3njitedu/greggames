package com.craig.greggames.model.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.Game;
import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.player.PlayerTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpadeGame extends Game {

	private String gameId;

	private boolean isStarting;

	private boolean isBidding;

	private int pointsToWin;

	private PlayerTable startHand;
	private PlayerTable startTurn;

	private PlayerTable currTurn;
	private int trickCount;

	private int turnCount;

	private int handCount;
	private boolean isGameOver;

	private Map<TeamTable, SpadeTeam> teams;

	private boolean isDealing;
	private int numberDeals;
	private int bags;

	private int overBook;

	private int bidNil;

	private int numberOfPlayers;

	private int numberOfTeams;

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

	public Map<TeamTable, SpadeTeam> getTeams() {
		if (teams == null) {

			teams = new EnumMap<TeamTable, SpadeTeam>(TeamTable.class);

			for (int code = 1; code <= numberOfTeams; code++) {

				teams.put(TeamTable.getTeamByName(code), new SpadeTeam());
			}

		}
		return teams;
	}

	public void setTeams(Map<TeamTable, SpadeTeam> teams) {
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

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(int numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	public boolean isBidding() {
		return isBidding;
	}

	public void setBidding(boolean isBidding) {
		this.isBidding = isBidding;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public PlayerTable getStartTurn() {
		return startTurn;
	}

	public void setStartTurn(PlayerTable startTurn) {
		this.startTurn = startTurn;
	}

	public PlayerTable getCurrTurn() {
		return currTurn;
	}

	public void setCurrTurn(PlayerTable currTurn) {
		this.currTurn = currTurn;
	}

	@Override
	public String toString() {
		return "SpadeGame [gameId=" + gameId + ", isStarting=" + isStarting + ", isBidding=" + isBidding
				+ ", pointsToWin=" + pointsToWin + ", startHand=" + startHand + ", startTurn=" + startTurn
				+ ", currTurn=" + currTurn + ", trickCount=" + trickCount + ", turnCount=" + turnCount + ", handCount="
				+ handCount + ", isGameOver=" + isGameOver + ", teams=" + teams + ", isDealing=" + isDealing
				+ ", numberDeals=" + numberDeals + ", bags=" + bags + ", overBook=" + overBook + ", bidNil=" + bidNil
				+ ", numberOfPlayers=" + numberOfPlayers + ", numberOfTeams=" + numberOfTeams + "]";
	}

}
