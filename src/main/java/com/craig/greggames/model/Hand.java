package com.craig.greggames.model;

import java.util.ArrayList;
import java.util.List;

import com.craig.greggames.model.card.Card;

public class Hand {

	private List<Card> shuffleDeck;

	public List<Card> getShuffleDeck() {
		if (shuffleDeck == null) {
			shuffleDeck = new ArrayList<Card>();
		}
		return shuffleDeck;
	}

	public void setShuffleDeck(List<Card> shuffleDeck) {
		this.shuffleDeck = shuffleDeck;
	}

}
