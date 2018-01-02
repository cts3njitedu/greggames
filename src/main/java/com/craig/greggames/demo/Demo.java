package com.craig.greggames.demo;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.util.DealUtility;

public class Demo {

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadePlayerHandler playerService;
	@Autowired
	private CardHandler cardService;
	
	

	public static void main(String[] args) {

//		PlaySpades playSpades = new PlaySpades();
//		
//
//		SpadeGame spadeGame = new SpadeGame();
//
//		spadeGame.setStarting(true);
//		spadeGame.setBags(50);
//		spadeGame.setBagPoints(100);
//		spadeGame.setGameOver(false);
//		spadeGame.setPointsToWin(150);
//		spadeGame.setNumberOfTeams(2);
//		spadeGame.setBidNilPoints(100);
//		spadeGame.setPointsToLose(-500);
//		
//		playSpades.playGame(spadeGame);
		
		Set<Card> cardSet = DealUtility.getSpadeHand();
		
		PlaySpades playSpades = new PlaySpades();
		
		playSpades.test();
		

	}
}
