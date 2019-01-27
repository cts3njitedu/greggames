package com.craig.greggames.service.spades;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.controller.game.cards.spades.SpadesController;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.socket.game.cards.spades.SpadesSocketController;
import com.craig.greggames.utility.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PlayersBiddingSteps {

	private SpadeGame spadeGame;
	@Autowired
	private SpadesController spadeController;

	@Autowired
	private SpadesSocketController messageController;

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	@Autowired
	private SpadeService spadeService;
	private JsonConverter jsonConverter;
	
	
	@Before
	public void setUp() {
		jsonConverter = new JsonConverter();
	}

	@Given("^a game and players ready to bid$")
	public void a_game_and_players_ready_to_bid() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		spadeGame = jsonConverter.getSpadeGameState("gamestates/spades/gameinprogressreq.json", SpadeGame.class);

	}

	@When("^I attempt to bid$")
	public void i_attempt_to_bid() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(50);
		spadeGame.setGameModifier(spadeGame.getCurrTurn());

		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(40);

		spadeGame.setGameModifier(spadeGame.getCurrTurn());
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(30);
		spadeGame.setGameModifier(spadeGame.getCurrTurn());
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);

		spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(60);
		spadeGame.setGameModifier(spadeGame.getCurrTurn());
		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
		// throw new PendingException();
	}

	@Then("^I should be successful in bidding$")
	public void i_should_be_successful_in_bidding() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);

	}
}
