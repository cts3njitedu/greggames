package com.craig.greggames.service.spades;

import org.springframework.stereotype.Service;

import com.craig.greggames.controller.spades.model.Game;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.controller.spades.model.Trick;
import com.craig.greggames.model.Hand;
import com.craig.greggames.util.DealUtility;

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
