package com.craig.cards;

import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.card.CardValue;

public class TempWinner {

	private CardValue tempWinnerCardValue;
	private CardSuit tempWinnerSuit;
	private CardValue currPlayerCardValue;
	private CardSuit currPlayerSuit;
	
	
	private int trickCount;

	public CardValue getTempWinnerCardValue() {
		return tempWinnerCardValue;
	}

	public void setTempWinnerCardValue(CardValue tempWinnerCardValue) {
		this.tempWinnerCardValue = tempWinnerCardValue;
	}

	public CardSuit getTempWinnerSuit() {
		return tempWinnerSuit;
	}

	public void setTempWinnerSuit(CardSuit tempWinnerSuit) {
		this.tempWinnerSuit = tempWinnerSuit;
	}

	public CardValue getCurrPlayerCardValue() {
		return currPlayerCardValue;
	}

	public void setCurrPlayerCardValue(CardValue currPlayerCardValue) {
		this.currPlayerCardValue = currPlayerCardValue;
	}

	public CardSuit getCurrPlayerSuit() {
		return currPlayerSuit;
	}

	public void setCurrPlayerSuit(CardSuit currPlayerSuit) {
		this.currPlayerSuit = currPlayerSuit;
	}

	public int getTrickCount() {
		return trickCount;
	}

	public void setTrickCount(int trickCount) {
		this.trickCount = trickCount;
	}

}
