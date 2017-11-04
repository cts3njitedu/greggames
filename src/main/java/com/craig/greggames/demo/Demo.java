package com.craig.greggames.demo;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadeTeam;

public class Demo {

	public static void main(String[] args) {

		PlaySpades playSpades = new PlaySpades();
		

		SpadeGame spadeGame = new SpadeGame();

		spadeGame.setStarting(true);
		spadeGame.setBags(50);
		spadeGame.setBagPoints(500);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(25);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setBidNilPoints(100);
		spadeGame.setPointsToLose(-200);
		
		playSpades.playGame(spadeGame);

		for(TeamTable team: TeamTable.values()) {
			
			SpadeTeam spadeTeam = spadeGame.getTeams().get(team);
			
			if(spadeTeam!=null) {
				
				if(spadeTeam.isWon()) {
					System.out.println("Winner!");
					System.out.println(spadeGame.getHandCount());
					System.out.println(spadeTeam.getName());
					System.out.println(spadeTeam.getTotalScore());
			
				}
				if(spadeTeam.isLost()) {
					System.out.println("Lost!");
					System.out.println(spadeGame.getHandCount());
					System.out.println(spadeTeam.getName());
					System.out.println(spadeTeam.getTotalScore());
				}
			}
			
			
		}
		// spadeGame.getTeams();
		

	}
}
