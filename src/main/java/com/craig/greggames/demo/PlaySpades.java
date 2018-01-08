package com.craig.greggames.demo;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.DealUtility;
import com.craig.greggames.validator.game.cards.spades.SpadeValidator;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
public class PlaySpades {

	@Autowired
	private SpadeGameHandler creator;
	@Autowired
	private SpadeTeamHandler teamService;
	
	@Autowired
	private CardHandler cardService;
	@Autowired
	private SpadeValidatorEngine validatorFactory;

	public PlaySpades() {

		this.creator = new SpadeGameHandler();
		this.cardService = new CardHandler();
		this.validatorFactory=new SpadeValidatorEngine();
	}

	public void playGame(SpadeGame spadeGame) throws GreggamesException {

		creator.create(spadeGame);
		creator.start(spadeGame);
		creator.play(spadeGame);
		while (!spadeGame.isGameOver()) {
			spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setBot(true);

			creator.play(spadeGame);

			spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setBot(true);
			creator.play(spadeGame);

			spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setBot(true);
			creator.play(spadeGame);

			spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn()).setBot(true);
			creator.play(spadeGame);

			playRound(spadeGame);

		}

	}

	private void playRound(SpadeGame spadeGame) throws GreggamesException {

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

		Card card = null;
		Set<Card> cards = null;
		for (int i = 1; i <= 13; i++) {

			PlayerTable player = spadeGame.getCurrTurn();

			TeamTable team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
			spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
			cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
			for(Card c: cards) {
				
				card = c;
				break;
			}
			
			spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
			creator.play(spadeGame);

			player = spadeGame.getCurrTurn();

			team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
			spadePlayer2 = spadeGame.getTeams().get(team).getPlayers().get(player);
			cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
			for(Card c: cards) {
				
				card = c;
				break;
			}
			
			spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);

			creator.play(spadeGame);

			player = spadeGame.getCurrTurn();
			team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
			spadePlayer3 = spadeGame.getTeams().get(team).getPlayers().get(player);
			cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
			for(Card c: cards) {
				
				card = c;
				break;
			}
			
			spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
			creator.play(spadeGame);

			player = spadeGame.getCurrTurn();
			team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
			spadePlayer4 = spadeGame.getTeams().get(team).getPlayers().get(player);
			cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
			for(Card c: cards) {
				
				card = c;
				break;
			}
			
			spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);

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

			creator.play(spadeGame);
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
	
	
	public void test() {
		
		
		validatorFactory.validate(new SpadeGame());
	}
}
