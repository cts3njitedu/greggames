package com.craig.greggames.model;

public class Card {

	private CardValue value;
	private CardSuit suit;
	
	public Card(CardValue value, CardSuit suit) {
		
		this.value = value;
		this.suit = suit;
	}

	public CardValue getValue() {
		return value;
	}

	public void setValue(CardValue value) {
		this.value = value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public void setSuit(CardSuit suit) {
		this.suit = suit;
	}

	
	

	

	
	
}
