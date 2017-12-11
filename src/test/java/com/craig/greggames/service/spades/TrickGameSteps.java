package com.craig.greggames.service.spades;

import java.util.Set;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.controller.game.cards.spades.SpadesController;
import com.craig.greggames.controller.game.cards.spades.socket.SpadesSocketController;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
public class TrickGameSteps {
	private SpadeGame spadeGame;
	@Autowired
	private SpadesController spadeController;

	@Autowired
	private SpadesSocketController messageController;

	@Autowired
	private SpadeTeamHandler teamService;

	@Given("^a game a players ready to play$")
	public void a_game_a_players_ready_to_play() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		spadeGame = new SpadeGame();

		spadeGame.setActivePlayers(0);
		spadeGame.setBagPoints(100);
		spadeGame.setBags(50);
		spadeGame.setBidNilPoints(100);
		spadeGame.setStarting(true);
		spadeGame.setPointsToWin(150);
		spadeGame.setPointsToLose(-100);
		spadeGame.setNumberOfTeams(2);
		spadeGame = messageController.createGame("", spadeGame);
		spadeGame = spadeController.startGame(spadeGame.getGameId());
		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(50);

		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(40);

		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(30);
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);

		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(60);
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
	
	}

	@When("^I attempt to play one trick$")
	public void i_attempt_to_play_one_trick() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Set<Card> cards = null;
		Card card = null;
		PlayerTable player = spadeGame.getCurrTurn();
		SpadePlayer spadePlayer1 = null;
		TeamTable team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
		cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
		for(Card c: cards) {
			
			card = c;
			break;
		}
		
		spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
		
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		
		player = spadeGame.getCurrTurn();
		team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
		cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
		for(Card c: cards) {
			
			card = c;
			break;
		}
		
		spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
		
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		
		player = spadeGame.getCurrTurn();
		team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
		cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
		for(Card c: cards) {
			
			card = c;
			break;
		}
		
		spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
		
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		
		player = spadeGame.getCurrTurn();
		team = teamService.getTeamByPlayer(player, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team).getPlayers().get(player);
		cards = spadeGame.getTeams().get(team).getPlayers().get(player).getRemainingCards();
		for(Card c: cards) {
			
			card = c;
			break;
		}
		
		spadeGame.getTeams().get(team).getPlayers().get(player).setPlayingCard(card);
		
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		
	}

	@Then("^I should be successful in determining the winner of trick$")
	public void i_should_be_successful_in_determining_the_winner_of_trick() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);
	}

}
