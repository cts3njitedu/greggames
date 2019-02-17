package com.craig.greggames.model.game.cards.player;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.craig.greggames.model.game.ServerMessage;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;

public abstract class Player {

	private String userId;
	private PlayerTable name;
	private int rank;
	private Card playingCard;
	private boolean isTurn;
	private boolean isWon;
	private Set<Card> remainingCards;
	private Set<Card> playedCards;
	private TeamTable team;
	
	private boolean hasPlayed;

	private boolean error;
	private Map<SpadeErrors,String> errorMessages;
	
	private Map<ServerMessage, String> serverMessages;

	private Map<PlayerPosition, PlayerTable> playerPositions;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PlayerTable getName() {
		return name;
	}

	public void setName(PlayerTable name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Card getPlayingCard() {
		return playingCard;
	}

	public void setPlayingCard(Card playingCard) {
		this.playingCard = playingCard;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean isWon) {
		this.isWon = isWon;
	}

	public Set<Card> getRemainingCards() {
		if (remainingCards == null) {
			remainingCards = new HashSet<Card>();
		}
		return remainingCards;
	}

	public void setRemainingCards(Set<Card> remainingCards) {
		this.remainingCards = remainingCards;
	}

	public TeamTable getTeam() {
		return team;
	}

	public void setTeam(TeamTable team) {
		this.team = team;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Map<SpadeErrors, String> getErrorMessages() {
		if(errorMessages==null) {
			
			errorMessages = new EnumMap<SpadeErrors, String>(SpadeErrors.class);
		}
		
		return errorMessages;
	}

	public void setErrorMessages(Map<SpadeErrors, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public boolean isHasPlayed() {
		return hasPlayed;
	}

	public void setHasPlayed(boolean hasPlayed) {
		this.hasPlayed = hasPlayed;
	}

	public Set<Card> getPlayedCards() {
		if(playedCards==null) {
			playedCards = new HashSet<>();
		}
		return playedCards;
	}

	public void setPlayedCards(Set<Card> playedCards) {
		this.playedCards = playedCards;
	}

	public Map<ServerMessage, String> getServerMessages() {
		if(serverMessages==null) {
			serverMessages = new HashMap<>();
		}
		return serverMessages;
	}

	public void setServerMessages(Map<ServerMessage, String> serverMessages) {
		this.serverMessages = serverMessages;
	}

	public Map<PlayerPosition, PlayerTable> getPlayerPositions() {
		if(playerPositions==null) {
			return new HashMap<>();
		}
		return playerPositions;
	}

	public void setPlayerPositions(Map<PlayerPosition, PlayerTable> playerPositions) {
		this.playerPositions = playerPositions;
	}

	

	

	

}
