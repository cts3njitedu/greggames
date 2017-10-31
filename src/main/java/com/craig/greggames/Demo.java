package com.craig.greggames;

import java.util.List;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.model.Card;
import com.craig.greggames.model.CardSuit;
import com.craig.greggames.model.CardValue;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;
import com.craig.greggames.util.SpadeGameCreator;

public class Demo {

	public static void main(String[] args) {

		SpadeGameCreator creator = new SpadeGameCreator();

		SpadeGame spadeGame = new SpadeGame();

		spadeGame.setStarting(true);
		spadeGame.setBags(100);
		spadeGame.setOverBook(100);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(500);
		spadeGame.setNumberOfTeams(2);

		spadeGame.getTeams();
		spadeGame = creator.execute(spadeGame);

		PlayerTable player = spadeGame.getCurrTrick();

		TeamTable team = TeamTable.getTeam(player.getCode() % 2);

		spadeGame.getTeams().get(team).getPlayers().get(player)
				.setPlayingCard(new Card(CardSuit.CLUBS, CardValue.FIVE));
		spadeGame = creator.execute(spadeGame);
		
		player = spadeGame.getCurrTrick();

		team = TeamTable.getTeam(player.getCode() % 2);

		spadeGame.getTeams().get(team).getPlayers().get(player)
				.setPlayingCard(new Card(CardSuit.CLUBS, CardValue.NINE));
		
		spadeGame = creator.execute(spadeGame);
		
		player = spadeGame.getCurrTrick();

		team = TeamTable.getTeam(player.getCode() % 2);

		spadeGame.getTeams().get(team).getPlayers().get(player)
				.setPlayingCard(new Card(CardSuit.DIAMONDS, CardValue.NINE));
		
		spadeGame = creator.execute(spadeGame);
		
		player = spadeGame.getCurrTrick();

		team = TeamTable.getTeam(player.getCode() % 2);

		spadeGame.getTeams().get(team).getPlayers().get(player)
				.setPlayingCard(new Card(CardSuit.HEARTS, CardValue.KING));
		
		spadeGame = creator.execute(spadeGame);

	}
}
