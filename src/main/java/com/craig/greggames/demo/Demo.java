package com.craig.greggames.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.Deck;
import com.craig.greggames.model.game.cards.spades.SpadeGame;

public class Demo {

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadePlayerHandler playerService;
	
	

	public static void main(String[] args) {

		PlaySpades playSpades = new PlaySpades();
		

		SpadeGame spadeGame = new SpadeGame();

		spadeGame.setStarting(true);
		spadeGame.setBags(50);
		spadeGame.setBagPoints(100);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(150);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setBidNilPoints(100);
		spadeGame.setPointsToLose(-500);
		
		playSpades.playGame(spadeGame);
		
		
	
		

	}
}
