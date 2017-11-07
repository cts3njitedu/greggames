package com.craig.greggames.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.card.CardValue;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.service.cards.CardService;
import com.craig.greggames.service.spades.state.SpadePlayerService;
import com.craig.greggames.service.spades.state.SpadeTeamService;

public class Demo {

	@Autowired
	private SpadeTeamService teamService;
	@Autowired
	private SpadePlayerService playerService;
	
	

	public static void main(String[] args) {

		PlaySpades playSpades = new PlaySpades();
		
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
		
		CardService cardService = new CardService();
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(CardValue.KING,CardSuit.SPADES));
		cards.add(new Card(CardValue.ACE,CardSuit.DIAMONDS));
		cards.add(new Card(CardValue.EIGHT,CardSuit.CLUBS));
		cards.add(new Card(CardValue.THREE,CardSuit.DIAMONDS));
		cards.add(new Card(CardValue.KING,CardSuit.HEARTS));
		
		cardService.sortCards(cards);
		
		System.out.println(cards.toString());
	
		
		
		// spadeGame.getTeams();
		

	}
}
