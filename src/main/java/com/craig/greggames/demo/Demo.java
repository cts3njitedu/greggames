package com.craig.greggames.demo;

import com.craig.greggames.model.spades.SpadeGame;

public class Demo {

	public static void main(String[] args) {

		PlaySpades playSpades = new PlaySpades();
		

		SpadeGame spadeGame = new SpadeGame();

		spadeGame.setStarting(true);
		spadeGame.setBags(100);
		spadeGame.setBagPoints(100);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(500);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setBidNilPoints(100);
		
		playSpades.playGame(spadeGame);

		// spadeGame.getTeams();
		

	}
}
