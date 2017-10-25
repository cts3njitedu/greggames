package com.craig.greggames.model;

import java.util.HashMap;
import java.util.Map;

public final class Deck {
	
	private final static Map<Integer,Card> deck = new HashMap<Integer,Card>();
	
	static {
		
		deck.put(1, new Card(CardValue.ACE,CardSuit.SPADES));
		deck.put(2, new Card(CardValue.TWO,CardSuit.SPADES));
		deck.put(3, new Card(CardValue.THREE,CardSuit.SPADES));
		deck.put(4, new Card(CardValue.FOUR,CardSuit.SPADES));
		deck.put(5, new Card(CardValue.FIVE,CardSuit.SPADES));
		deck.put(6, new Card(CardValue.SIX,CardSuit.SPADES));
		deck.put(7, new Card(CardValue.SEVEN,CardSuit.SPADES));
		deck.put(8, new Card(CardValue.EIGHT,CardSuit.SPADES));
		deck.put(9, new Card(CardValue.NINE,CardSuit.SPADES));
		deck.put(10, new Card(CardValue.TEN,CardSuit.SPADES));
		deck.put(11, new Card(CardValue.JACK,CardSuit.SPADES));
		deck.put(12, new Card(CardValue.QUEEN,CardSuit.SPADES));
		deck.put(13, new Card(CardValue.KING,CardSuit.SPADES));
		deck.put(14, new Card(CardValue.ACE,CardSuit.HEARTS));
		deck.put(15, new Card(CardValue.TWO,CardSuit.HEARTS));
		deck.put(16, new Card(CardValue.THREE,CardSuit.HEARTS));
		deck.put(17, new Card(CardValue.FOUR,CardSuit.HEARTS));
		deck.put(18, new Card(CardValue.FIVE,CardSuit.HEARTS));
		deck.put(19, new Card(CardValue.SIX,CardSuit.HEARTS));
		deck.put(20, new Card(CardValue.SEVEN,CardSuit.HEARTS));
		deck.put(21, new Card(CardValue.EIGHT,CardSuit.HEARTS));
		deck.put(22, new Card(CardValue.NINE,CardSuit.HEARTS));
		deck.put(23, new Card(CardValue.TEN,CardSuit.HEARTS));
		deck.put(24, new Card(CardValue.JACK,CardSuit.HEARTS));
		deck.put(25, new Card(CardValue.QUEEN,CardSuit.HEARTS));
		deck.put(26, new Card(CardValue.KING,CardSuit.HEARTS));
		deck.put(27, new Card(CardValue.ACE,CardSuit.DIAMONDS));
		deck.put(28, new Card(CardValue.TWO,CardSuit.DIAMONDS));
		deck.put(29, new Card(CardValue.THREE,CardSuit.DIAMONDS));
		deck.put(30, new Card(CardValue.FOUR,CardSuit.DIAMONDS));
		deck.put(31, new Card(CardValue.FIVE,CardSuit.DIAMONDS));
		deck.put(32, new Card(CardValue.SIX,CardSuit.DIAMONDS));
		deck.put(33, new Card(CardValue.SEVEN,CardSuit.DIAMONDS));
		deck.put(34, new Card(CardValue.EIGHT,CardSuit.DIAMONDS));
		deck.put(35, new Card(CardValue.NINE,CardSuit.DIAMONDS));
		deck.put(36, new Card(CardValue.TEN,CardSuit.DIAMONDS));
		deck.put(37, new Card(CardValue.JACK,CardSuit.DIAMONDS));
		deck.put(38, new Card(CardValue.QUEEN,CardSuit.DIAMONDS));
		deck.put(39, new Card(CardValue.KING,CardSuit.DIAMONDS));
		deck.put(40, new Card(CardValue.ACE,CardSuit.CLUBS));
		deck.put(41, new Card(CardValue.TWO,CardSuit.CLUBS));
		deck.put(42, new Card(CardValue.THREE,CardSuit.CLUBS));
		deck.put(43, new Card(CardValue.FOUR,CardSuit.CLUBS));
		deck.put(44, new Card(CardValue.FIVE,CardSuit.CLUBS));
		deck.put(45, new Card(CardValue.SIX,CardSuit.CLUBS));
		deck.put(46, new Card(CardValue.SEVEN,CardSuit.CLUBS));
		deck.put(47, new Card(CardValue.EIGHT,CardSuit.CLUBS));
		deck.put(48, new Card(CardValue.NINE,CardSuit.CLUBS));
		deck.put(49, new Card(CardValue.TEN,CardSuit.CLUBS));
		deck.put(50, new Card(CardValue.JACK,CardSuit.CLUBS));
		deck.put(51, new Card(CardValue.QUEEN,CardSuit.CLUBS));
		deck.put(52, new Card(CardValue.KING,CardSuit.CLUBS));
		
		
		
		
	}

	public static Card getCard(int cardId) {
		return deck.get(cardId);
	}
	
	
	


}
