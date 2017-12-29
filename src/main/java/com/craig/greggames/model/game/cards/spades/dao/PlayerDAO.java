package com.craig.greggames.model.game.cards.spades.dao;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.team.TeamTable;

public abstract class PlayerDAO {

	private String userId;
	private PlayerTable name;
	private int rank;
	private Card playingCard;
	private boolean isTurn;
	private boolean isWon;
	private Set<Card> remainingCards;
	private TeamTable team;

	private boolean hasPlayed;
	private boolean error;
	private Map<SpadeErrors,String> errorMessages;

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

	
	
}
