package com.craig.greggames.service.spades;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.utility.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = CardGamesApplication.class)
public class CreateGameSteps {

	@Autowired
	private SpadeService spadeService;
	

	private JsonConverter jsonConverter;
	private SpadeGame spadeGame;

	@Before
	public void setUp() {
		jsonConverter = new JsonConverter();
	}
	@Given("^a initial game$")
	public void a_initial_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
		spadeGame = jsonConverter.getSpadeGameState("gamestates/spades/creategamereq.json", SpadeGame.class);
		
		//System.out.println(spadeGame);

	}

	@When("^I attempt to create the game$")
	public void i_attempt_to_create_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//spadeGame = spadeSocket.createGame("game", spadeGame);
		spadeGame = spadeService.playGame(spadeGame);
	}

	@Then("^I should be successful in creating the game$")
	public void i_should_be_successful_in_creating_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);
	}

}
