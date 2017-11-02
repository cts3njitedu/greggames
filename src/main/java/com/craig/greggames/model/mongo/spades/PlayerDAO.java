package com.craig.greggames.model.mongo.spades;

import java.util.ArrayList;
import java.util.List;

import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.player.PlayerTable;

public abstract class PlayerDAO {

	private String userId;
	private PlayerTable name;
	private int rank;
	private Card playingCard;
	private boolean isTurn;
	private boolean isWon;
	private List<Card> remainingCards;

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

	public List<Card> getRemainingCards() {
		if (remainingCards == null) {
			remainingCards = new ArrayList<Card>();
		}
		return remainingCards;
	}

	public void setRemainingCards(List<Card> remainingCards) {
		this.remainingCards = remainingCards;
	}
}
