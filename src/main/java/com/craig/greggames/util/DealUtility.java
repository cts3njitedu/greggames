package com.craig.greggames.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;

@Service
public class DealUtility {
	
	@Autowired
	private RandomUtility randomUtility;
	
	public List<Card> getSpadeHand() {
		
		return randomUtility.shuffle();
		
	
	}

}
