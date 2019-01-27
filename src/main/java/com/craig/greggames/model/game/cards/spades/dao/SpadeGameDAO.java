package com.craig.greggames.model.game.cards.spades.dao;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.CardGame;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeBroken;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Document(collection = "spadegame")
public class SpadeGameDAO extends CardGame{

	@Id
	private String gameId;

	private final GregGameChildTypes gameType = GregGameChildTypes.SPADES;
	private SpadePreviousHandDAO previousHand;
	private SpadePreviousTrickDAO previousTrick;
	private boolean isStarting;

	private SpadeNotifications notification;

	private boolean isBidding;

	private boolean isPlaying;
	private boolean isNewPlayer;
	private int pointsToWin;
	
	private long versionNb;

	private long gameVersionNb;

	private PlayerTable gameModifier;
	private PlayerTable startHand;
	private PlayerTable startTurn;

	private PlayerTable currTurn;
	private int trickCount;

	private int turnCount;

	private int handCount;
	private boolean isGameOver;

	private boolean isSpadePlayed;
	
	private int activePlayers;

	private Map<TeamTable, SpadeTeamDAO> teams;

	private boolean isDealing;
	private int numberDeals;
	private int bags;

	private int bagPoints;

	private int bidNilPoints;

	private int bidNil;

	private int numberOfPlayers;

	private int numberOfTeams;

	private Map<PlayerTable, Integer> playerCardCount;
	private int pointsToLose;

	private SpadeNotifications gameNotification;
	private SpadeNotifications playerNotification;
	private PlayerTable tempWinner;
	
	private boolean isTrickOver;
	private boolean isHandOver;
	
	private boolean isBotPlaying;
	
	private SpadeBroken spadeBroken;
	
	private long maxTime;
	
	private boolean isServingPlaying;
	
	private boolean lock;

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

	public int getBagPoints() {
		return bagPoints;
	}

	public void setBagPoints(int bagPoints) {
		this.bagPoints = bagPoints;
	}

	public int getBidNilPoints() {
		return bidNilPoints;
	}

	public void setBidNilPoints(int bidNilPoints) {
		this.bidNilPoints = bidNilPoints;
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

	public Map<TeamTable, SpadeTeamDAO> getTeams() {
		if (teams == null) {

			teams = new EnumMap<TeamTable, SpadeTeamDAO>(TeamTable.class);

			for (int code = 1; code <= numberOfTeams; code++) {

				teams.put(TeamTable.getTeam(code), new SpadeTeamDAO());
			}

		}
		return teams;
	}

	public void setTeams(Map<TeamTable, SpadeTeamDAO> teams) {
		this.teams = teams;
	}
	

	public Map<PlayerTable, Integer> getPlayerCardCount() {
	
		return playerCardCount;
	}

	public void setPlayerCardCount(Map<PlayerTable, Integer> playerCardCount) {
		this.playerCardCount = playerCardCount;
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

	public int getPointsToLose() {
		return pointsToLose;
	}

	public void setPointsToLose(int pointsToLose) {
		this.pointsToLose = pointsToLose;
	}

	public PlayerTable getTempWinner() {
		return tempWinner;
	}

	public void setTempWinner(PlayerTable tempWinner) {
		this.tempWinner = tempWinner;
	}

	public boolean isSpadePlayed() {
		return isSpadePlayed;
	}

	public void setSpadePlayed(boolean isSpadePlayed) {
		this.isSpadePlayed = isSpadePlayed;
	}

	public int getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(int activePlayers) {
		this.activePlayers = activePlayers;
	}

	public boolean isNewPlayer() {
		return isNewPlayer;
	}

	public void setNewPlayer(boolean isNewPlayer) {
		this.isNewPlayer = isNewPlayer;
	}

	public PlayerTable getGameModifier() {
		return gameModifier;
	}

	public void setGameModifier(PlayerTable gameModifier) {
		this.gameModifier = gameModifier;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public SpadePreviousHandDAO getPreviousHand() {
		return previousHand;
	}

	public void setPreviousHand(SpadePreviousHandDAO previousHand) {
		this.previousHand = previousHand;
	}

	public SpadePreviousTrickDAO getPreviousTrick() {
		return previousTrick;
	}

	public void setPreviousTrick(SpadePreviousTrickDAO previousTrick) {
		this.previousTrick = previousTrick;
	}

	public SpadeNotifications getNotification() {
		return notification;
	}

	public void setNotification(SpadeNotifications notification) {
		this.notification = notification;
	}

	public SpadeNotifications getGameNotification() {
		return gameNotification;
	}

	public void setGameNotification(SpadeNotifications gameNotification) {
	
		this.gameNotification = gameNotification;
	}

	public SpadeNotifications getPlayerNotification() {
	
		return playerNotification;
	}

	public void setPlayerNotification(SpadeNotifications playerNotification) {
		this.playerNotification = playerNotification;
	}

	public boolean isTrickOver() {
		return isTrickOver;
	}

	public void setTrickOver(boolean isTrickOver) {
		this.isTrickOver = isTrickOver;
	}

	public boolean isHandOver() {
		return isHandOver;
	}

	public void setHandOver(boolean isHandOver) {
		this.isHandOver = isHandOver;
	}

	@Override
	public GregGameChildTypes getGameType() {
		// TODO Auto-generated method stub
		return gameType;
	}

	public SpadeBroken getSpadeBroken() {
		return spadeBroken;
	}

	public void setSpadeBroken(SpadeBroken spadeBroken) {
		this.spadeBroken = spadeBroken;
	}

	public long getVersionNb() {
		return versionNb;
	}

	public void setVersionNb(long versionNb) {
		this.versionNb = versionNb;
	}


	public long getGameVersionNb() {
		return gameVersionNb;
	}

	public void setGameVersionNb(long gameVersionNb) {
		this.gameVersionNb = gameVersionNb;
	}

	public boolean isBotPlaying() {
		return isBotPlaying;
	}

	public void setBotPlaying(boolean isBotPlaying) {
		this.isBotPlaying = isBotPlaying;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public boolean isServingPlaying() {
		return isServingPlaying;
	}

	public void setServingPlaying(boolean isServingPlaying) {
		this.isServingPlaying = isServingPlaying;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	
	
	
	
	
	
	
	

}
