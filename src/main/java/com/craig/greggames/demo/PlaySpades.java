package com.craig.greggames.demo;

import java.util.Random;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.util.SpadeGameCreator;

public class PlaySpades {

	private SpadeGameCreator creator;

	
	public PlaySpades() {

		this.creator = new SpadeGameCreator();
	}

	public void playGame(SpadeGame spadeGame) {
		Random rand = new Random();
	
		creator.execute(spadeGame);
		while (!spadeGame.isGameOver()) {
			spadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(spadeGame.getCurrTurn().getCode(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(10*(rand.nextInt(6)+1));

			creator.execute(spadeGame);

			spadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(spadeGame.getCurrTurn().getCode(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(10*(rand.nextInt(6)+1));
			creator.execute(spadeGame);

			spadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(spadeGame.getCurrTurn().getCode(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(10*(rand.nextInt(6)+1));
			creator.execute(spadeGame);

			spadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(spadeGame.getCurrTurn().getCode(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(10*(rand.nextInt(6)+1));
			creator.execute(spadeGame);

			playRound(spadeGame);

		}

	}

	private void playRound(SpadeGame spadeGame) {

		System.out.println("Hand");
		System.out.println("Start Hand: " + spadeGame.getStartHand());
		System.out.println("Turn 0");
		System.out.println("Turn");
		System.out.println("Start Turn: " + spadeGame.getStartTurn());
		System.out.println("Current Turn: " + spadeGame.getCurrTurn());
		System.out.println("Player 1");
		SpadePlayer spadePlayer1 = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER1);
		System.out.println("Remaining Cards: " + spadePlayer1.getRemainingCards());
		System.out.println("Bid: " + spadePlayer1.getPlayerBid());
		System.out.println("Player 2");
		SpadePlayer spadePlayer2 = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER2);
		System.out.println("Remaining Cards: " + spadePlayer2.getRemainingCards());
		System.out.println("Bid: " + spadePlayer2.getPlayerBid());
		System.out.println("Player 3");
		SpadePlayer spadePlayer3 = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER3);
		System.out.println("Remaining Cards: " + spadePlayer3.getRemainingCards());
		System.out.println("Bid: " + spadePlayer3.getPlayerBid());
		System.out.println("Player 4");
		SpadePlayer spadePlayer4 = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER4);
		System.out.println("Remaining Cards: " + spadePlayer4.getRemainingCards());
		System.out.println("Bid: " + spadePlayer4.getPlayerBid());

		for (int i = 1; i <= 13; i++) {

			PlayerTable player = spadeGame.getCurrTurn();

			TeamTable team = TeamTable.getTeamByPlayer(player.getCode(), spadeGame.getNumberOfTeams());
			spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
			spadeGame.getTeams().get(team).getPlayers().get(player)
					.setPlayingCard(spadePlayer1.getRemainingCards().get(0));
			creator.execute(spadeGame);

			player = spadeGame.getCurrTurn();

			team = TeamTable.getTeamByPlayer(player.getCode(), spadeGame.getNumberOfTeams());
			spadePlayer2 = spadeGame.getTeams().get(team).getPlayers().get(player);
			spadeGame.getTeams().get(team).getPlayers().get(player)
					.setPlayingCard(spadePlayer2.getRemainingCards().get(0));

			creator.execute(spadeGame);

			player = spadeGame.getCurrTurn();
			team = TeamTable.getTeamByPlayer(player.getCode(), spadeGame.getNumberOfTeams());
			spadePlayer3 = spadeGame.getTeams().get(team).getPlayers().get(player);
			spadeGame.getTeams().get(team).getPlayers().get(player)
					.setPlayingCard(spadePlayer3.getRemainingCards().get(0));

			creator.execute(spadeGame);

			player = spadeGame.getCurrTurn();
			team = TeamTable.getTeamByPlayer(player.getCode(), spadeGame.getNumberOfTeams());
			spadePlayer4 = spadeGame.getTeams().get(team).getPlayers().get(player);
			spadeGame.getTeams().get(team).getPlayers().get(player)
					.setPlayingCard(spadePlayer4.getRemainingCards().get(0));

			System.out.println("Player 1");
			SpadePlayer spadePlayer1a = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER1);
			System.out.println("Playing Card: " + spadePlayer1a.getPlayingCard());
			System.out.println("Player 2");
			SpadePlayer spadePlayer2a = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER2);
			System.out.println("Playing Card: " + spadePlayer2a.getPlayingCard());
			System.out.println("Player 3");
			SpadePlayer spadePlayer3a = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER3);
			System.out.println("Playing Card: " + spadePlayer3a.getPlayingCard());
			System.out.println("Player 4");
			SpadePlayer spadePlayer4a = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER4);
			System.out.println("Playing Card: " + spadePlayer4a.getPlayingCard());

			creator.execute(spadeGame);
			System.out.println("Player 1");
			spadePlayer1 = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER1);
			System.out.println("Current Score: " + spadePlayer1.getPlayerCurrentScore());
			System.out.println("Remaining Cards: " + spadePlayer1.getRemainingCards().size());
			System.out.println("Player 2");
			spadePlayer2 = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER2);
			System.out.println("Current Score: " + spadePlayer2.getPlayerCurrentScore());
			System.out.println("Remaining Cards: " + spadePlayer2.getRemainingCards().size());
			System.out.println("Player 3");
			spadePlayer3 = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER3);
			System.out.println("Current Score: " + spadePlayer3.getPlayerCurrentScore());
			System.out.println("Remaining Cards: " + spadePlayer3.getRemainingCards().size());
			System.out.println("Player 4");
			spadePlayer4 = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER4);
			System.out.println("Current Score: " + spadePlayer4.getPlayerCurrentScore());
			System.out.println("Remaining Cards: " + spadePlayer4.getRemainingCards().size());

			System.out.println("Trick: " + spadeGame.getTrickCount());
			System.out.println("Start Turn: " + spadeGame.getStartTurn());
			System.out.println("Current Turn: " + spadeGame.getCurrTurn());

		}

	}
}
