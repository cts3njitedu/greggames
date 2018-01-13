package com.craig.greggames.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.Deck;

@Service
public class RandomUtility {
	
	private final static int STANDARDDECKSIZE=52;
	
	
	public List<Card> shuffle(){
		
		List<Integer> cardIds = new ArrayList<Integer>();
		
		for(int i=1; i<=STANDARDDECKSIZE; i++) {
			
			cardIds.add(i);
		}
		
		Collections.shuffle(cardIds);
		
		List<Card>cards = new ArrayList<Card>();
		
		for(Integer cardId:cardIds) {
			
			cards.add(Deck.getCardByCode(cardId));
		}
		return cards;
		
		
	}
	

}
