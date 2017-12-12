package com.craig.greggames.model.game.cards;

public class Card {

	private String name;
	private CardValue value;
	private CardSuit suit;

	public Card() {
		
	}

	public Card(String name, CardValue value, CardSuit suit) {

		this.name = name;
		this.value = value;
		this.suit = suit;
	}

	public String getName() {
		return name;
	}

	public CardValue getValue() {
		return value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", value=" + value + ", suit=" + suit + "]";
	}

	
}
