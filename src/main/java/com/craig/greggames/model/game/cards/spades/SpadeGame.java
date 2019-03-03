package com.craig.greggames.model.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.cards.CardGame;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SpadeGame extends CardGame {

	private String gameId;

	private final GregGameChildTypes gameType = GregGameChildTypes.SPADES;
	
	private SpadePreviousHand previousHand;
	private SpadePreviousTrick previousTrick;
	private boolean starting;

	private boolean bidding;

	private boolean playing;

	private boolean newPlayer;

	private int pointsToWin;
	
	private long versionNb;

	private long gameVersionNb;
	private PlayerTable startHand;
	private PlayerTable startTurn;

	private PlayerTable currTurn;

	private PlayerTable gameModifier;
	private int trickCount;

	private int turnCount;

	private int handCount;
	private boolean gameOver;

	private boolean spadePlayed;
	private int activePlayers;

	private Map<TeamTable, SpadeTeam> teams;

	private boolean dealing;
	private int numberDeals;
	private int bags;

	private int bagPoints;

	private int bidNilPoints;

	private int bidNil;

	private int numberOfPlayers;

	private int numberOfTeams;

	private int pointsToLose;

	private PlayerTable tempWinner;

	private Map<PlayerTable, Integer> playerCardCount;

	private SpadeNotifications gameNotification;
	private SpadeNotifications playerNotification;
	
	private boolean botPlaying;
	
	private boolean trickOver;
	private boolean handOver;

	private SpadeBroken spadeBroken;
	
	private long maxTime;
	
	private boolean serverPlaying;
	
	private boolean lock;
	
	private boolean playAgain;
	
	private AsyncState asyncState;
	
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
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

	

	public Map<TeamTable, SpadeTeam> getTeams() {
		if (teams == null || teams.size() == 0) {

			teams = new EnumMap<TeamTable, SpadeTeam>(TeamTable.class);

			for (int code = 1; code <= numberOfTeams; code++) {

				teams.put(TeamTable.getTeam(code), new SpadeTeam());
			}

		}
		return teams;
	}

	public void setTeams(Map<TeamTable, SpadeTeam> teams) {
		this.teams = teams;
	}

	@JsonInclude(Include.NON_EMPTY)
	public Map<PlayerTable, Integer> getPlayerCardCount() {

		return playerCardCount;
	}

	public void setPlayerCardCount(Map<PlayerTable, Integer> playerCardCount) {
		this.playerCardCount = playerCardCount;
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

	

	public int getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(int activePlayers) {
		this.activePlayers = activePlayers;
	}


	public PlayerTable getGameModifier() {
		return gameModifier;
	}

	public void setGameModifier(PlayerTable gameModifier) {
		this.gameModifier = gameModifier;
	}

	
	public SpadePreviousHand getPreviousHand() {
		return previousHand;
	}

	public void setPreviousHand(SpadePreviousHand previousHand) {
		this.previousHand = previousHand;
	}

	public SpadePreviousTrick getPreviousTrick() {
		return previousTrick;
	}

	public void setPreviousTrick(SpadePreviousTrick previousTrick) {
		this.previousTrick = previousTrick;
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
	
	public GregGameChildTypes getGameType() {
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
	
	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
	
	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isBidding() {
		return bidding;
	}

	public void setBidding(boolean bidding) {
		this.bidding = bidding;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isNewPlayer() {
		return newPlayer;
	}

	public void setNewPlayer(boolean newPlayer) {
		this.newPlayer = newPlayer;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isSpadePlayed() {
		return spadePlayed;
	}

	public void setSpadePlayed(boolean spadePlayed) {
		this.spadePlayed = spadePlayed;
	}

	public boolean isDealing() {
		return dealing;
	}

	public void setDealing(boolean dealing) {
		this.dealing = dealing;
	}

	public boolean isBotPlaying() {
		return botPlaying;
	}

	public void setBotPlaying(boolean botPlaying) {
		this.botPlaying = botPlaying;
	}

	public boolean isTrickOver() {
		return trickOver;
	}

	public void setTrickOver(boolean trickOver) {
		this.trickOver = trickOver;
	}

	public boolean isHandOver() {
		return handOver;
	}

	public void setHandOver(boolean handOver) {
		this.handOver = handOver;
	}

	public boolean isServerPlaying() {
		return serverPlaying;
	}

	public void setServerPlaying(boolean serverPlaying) {
		this.serverPlaying = serverPlaying;
	}

	public boolean isPlayAgain() {
		return playAgain;
	}

	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}


	public AsyncState getAsyncState() {
		return asyncState;
	}

	public void setAsyncState(AsyncState asyncState) {
		this.asyncState = asyncState;
	}

	
	

}
