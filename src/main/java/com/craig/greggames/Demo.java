package com.craig.greggames;

import java.util.List;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.model.Card;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;
import com.craig.greggames.util.SpadeGameCreator;

public class Demo {

	
	public static void main (String [] args) {
		
		SpadeGameCreator creator = new SpadeGameCreator();
		
		SpadeGame spadeGame = new SpadeGame();
		
		spadeGame.setStarting(true);
		spadeGame.setStartTrick(PlayerTable.PLAYER3);
		spadeGame.setEndTrick(PlayerTable.PLAYER2);
		spadeGame.setBags(100);
		spadeGame.setOverBook(100);
		spadeGame.setCurrTrick(PlayerTable.PLAYER3);
		spadeGame.setGameOver(false);
		spadeGame.setStartHand(PlayerTable.PLAYER3);
		spadeGame.setPointsToWin(500);
		
		spadeGame = creator.execute(spadeGame);
		
		spadeGame = creator.execute(spadeGame);

		
		
	}
}
