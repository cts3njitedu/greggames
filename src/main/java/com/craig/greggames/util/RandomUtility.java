package com.craig.greggames.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.Deck;

public class RandomUtility {
	
	private final static int STANDARDDECKSIZE=52;
	
	
	public static Set<Card> shuffle(){
		
		List<Integer> cardIds = new ArrayList<Integer>();
		
		for(int i=1; i<=STANDARDDECKSIZE; i++) {
			
			cardIds.add(i);
		}
		
		Collections.shuffle(cardIds);
		
		Set<Card>cards = new HashSet<Card>();
		
		for(Integer cardId:cardIds) {
			
			cards.add(Deck.getCardByCode(cardId));
		}
		return cards;
		
		
	}
	

}
