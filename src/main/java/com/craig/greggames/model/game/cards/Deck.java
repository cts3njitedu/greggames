package com.craig.greggames.model.game.cards;

import java.util.HashMap;
import java.util.Map;

public enum Deck {
	

	TWO_DIAMONDS(1,new Card("TWO_DIAMONDS",CardValue.TWO,CardSuit.DIAMONDS)),
	TWO_HEARTS(2,new Card("TWO_HEARTS",CardValue.TWO,CardSuit.HEARTS)),
	TWO_CLUBS(3,new Card("TWO_CLUBS",CardValue.TWO,CardSuit.CLUBS)),
	TWO_SPADES(4,new Card("TWO_SPADES",CardValue.TWO,CardSuit.SPADES)),
	THREE_DIAMONDS(5,new Card("THREE_DIAMONDS",CardValue.THREE,CardSuit.DIAMONDS)),
	THREE_HEARTS(6,new Card("THREE_HEARTS",CardValue.THREE,CardSuit.HEARTS)),
	THREE_CLUBS(7,new Card("THREE_CLUBS",CardValue.THREE,CardSuit.CLUBS)),
	THREE_SPADES(8,new Card("THREE_SPADES",CardValue.THREE,CardSuit.SPADES)),
	FOUR_DIAMONDS(9,new Card("FOUR_DIAMONDS",CardValue.FOUR,CardSuit.DIAMONDS)),
	FOUR_HEARTS(10,new Card("FOUR_HEARTS",CardValue.FOUR,CardSuit.HEARTS)),
	FOUR_CLUBS(11,new Card("FOUR_CLUBS",CardValue.FOUR,CardSuit.CLUBS)),
	FOUR_SPADES(12,new Card("FOUR_SPADES",CardValue.FOUR,CardSuit.SPADES)),
	FIVE_DIAMONDS(13,new Card("FIVE_DIAMONDS",CardValue.FIVE,CardSuit.DIAMONDS)),
	FIVE_HEARTS(14,new Card("FIVE_HEARTS",CardValue.FIVE,CardSuit.HEARTS)),
	FIVE_CLUBS(15,new Card("FIVE_CLUBS",CardValue.FIVE,CardSuit.CLUBS)),
	FIVE_SPADES(16,new Card("FIVE_SPADES",CardValue.FIVE,CardSuit.SPADES)),
	SIX_DIAMONDS(17,new Card("SIX_DIAMONDS",CardValue.SIX,CardSuit.DIAMONDS)),
	SIX_HEARTS(18,new Card("SIX_HEARTS",CardValue.SIX,CardSuit.HEARTS)),
	SIX_CLUBS(19,new Card("SIX_CLUBS",CardValue.SIX,CardSuit.CLUBS)),
	SIX_SPADES(20,new Card("SIX_SPADES",CardValue.SIX,CardSuit.SPADES)),
	SEVEN_DIAMONDS(21,new Card("SEVEN_DIAMONDS",CardValue.SEVEN,CardSuit.DIAMONDS)),
	SEVEN_HEARTS(22,new Card("SEVEN_HEARTS",CardValue.SEVEN,CardSuit.HEARTS)),
	SEVEN_CLUBS(23,new Card("SEVEN_CLUBS",CardValue.SEVEN,CardSuit.CLUBS)),
	SEVEN_SPADES(24,new Card("SEVEN_SPADES",CardValue.SEVEN,CardSuit.SPADES)),
	EIGHT_DIAMONDS(25,new Card("EIGHT_DIAMONDS",CardValue.EIGHT,CardSuit.DIAMONDS)),
	EIGHT_HEARTS(26,new Card("EIGHT_HEARTS",CardValue.EIGHT,CardSuit.HEARTS)),
	EIGHT_CLUBS(27,new Card("EIGHT_CLUBS",CardValue.EIGHT,CardSuit.CLUBS)),
	EIGHT_SPADES(28,new Card("EIGHT_SPADES",CardValue.EIGHT,CardSuit.SPADES)),
	NINE_DIAMONDS(29,new Card("NINE_DIAMONDS",CardValue.NINE,CardSuit.DIAMONDS)),
	NINE_HEARTS(30,new Card("NINE_HEARTS",CardValue.NINE,CardSuit.HEARTS)),
	NINE_CLUBS(31,new Card("NINE_CLUBS",CardValue.NINE,CardSuit.CLUBS)),
	NINE_SPADES(32,new Card("NINE_SPADES",CardValue.NINE,CardSuit.SPADES)),
	TEN_DIAMONDS(33,new Card("TEN_DIAMONDS",CardValue.TEN,CardSuit.DIAMONDS)),
	TEN_HEARTS(34,new Card("TEN_HEARTS",CardValue.TEN,CardSuit.HEARTS)),
	TEN_CLUBS(35,new Card("TEN_CLUBS",CardValue.TEN,CardSuit.CLUBS)),
	TEN_SPADES(36,new Card("TEN_SPADES",CardValue.TEN,CardSuit.SPADES)),
	JACK_DIAMONDS(37,new Card("JACK_DIAMONDS",CardValue.JACK,CardSuit.DIAMONDS)),
	JACK_HEARTS(38,new Card("JACK_HEARTS",CardValue.JACK,CardSuit.HEARTS)),
	JACK_CLUBS(39,new Card("JACK_CLUBS",CardValue.JACK,CardSuit.CLUBS)),
	JACK_SPADES(40,new Card("JACK_SPADES",CardValue.JACK,CardSuit.SPADES)),
	QUEEN_DIAMONDS(41,new Card("QUEEN_DIAMONDS",CardValue.QUEEN,CardSuit.DIAMONDS)),
	QUEEN_HEARTS(42,new Card("QUEEN_HEARTS",CardValue.QUEEN,CardSuit.HEARTS)),
	QUEEN_CLUBS(43,new Card("QUEEN_CLUBS",CardValue.QUEEN,CardSuit.CLUBS)),
	QUEEN_SPADES(44,new Card("QUEEN_SPADES",CardValue.QUEEN,CardSuit.SPADES)),
	KING_DIAMONDS(45,new Card("KING_DIAMONDS",CardValue.KING,CardSuit.DIAMONDS)),
	KING_HEARTS(46,new Card("KING_HEARTS",CardValue.KING,CardSuit.HEARTS)),
	KING_CLUBS(47,new Card("KING_CLUBS",CardValue.KING,CardSuit.CLUBS)),
	KING_SPADES(48,new Card("KING_SPADES",CardValue.KING,CardSuit.SPADES)),
	ACE_DIAMONDS(49,new Card("ACE_DIAMONDS",CardValue.ACE,CardSuit.DIAMONDS)),
	ACE_HEARTS(50,new Card("ACE_HEARTS",CardValue.ACE,CardSuit.HEARTS)),
	ACE_CLUBS(51,new Card("ACE_CLUBS",CardValue.ACE,CardSuit.CLUBS)),
	ACE_SPADES(52,new Card("ACE_SPADES",CardValue.ACE,CardSuit.SPADES));
	
	
	
	
	private int code;
	private Card card;
	
	private static Map<Integer, Card> cardCode;
	private static Map<String,Card>cardName;
	
	static {
		
		cardCode = new HashMap<Integer,Card>();
		cardName = new HashMap<String,Card>();
		
		for(Deck deck: Deck.values()) {
			
			cardCode.put(deck.code,deck.card);
			cardName.put(deck.card.getName(), deck.card);
			
		}
	}
	

	private Deck(int code, Card card) {

		this.code = code;
		this.card = card;
	}

	public static Card getCardByCode(int code) {
		
		return cardCode.get(code);
	}
	
	public static Card getCardByName(String name) {
		
		return cardName.get(name);
	}

	public Card getCard() {
		return card;
	}

	


}
