package com.craig.cards.spades.service;

import org.springframework.stereotype.Service;

import com.craig.cards.model.Hand;
import com.craig.cards.spades.model.Game;
import com.craig.cards.spades.model.SpadePlayer;
import com.craig.cards.spades.model.Trick;
import com.craig.cards.util.DealUtility;

@Service
public class SpadesServiceImpl implements SpadeService {

	@Override
	public Hand getHand(Game game) {
		// TODO Auto-generated method stub
		Hand hand = new Hand();
		hand.setShuffleDeck(DealUtility.getSpadeHand());
		return hand;
	}

	
	

}
