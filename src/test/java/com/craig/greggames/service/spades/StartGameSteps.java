package com.craig.greggames.service.spades;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.controller.spades.SpadesController;
import com.craig.greggames.model.spades.SpadeGame;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
public class StartGameSteps {
	
	@Autowired
	private SpadesController spadeService;

	private String gameId;
	
	private SpadeGame spadeGame;


	@Given("^a created game$")
	public void a_created_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		gameId = "5a2c9a784ac63b078508ec94";

	    //throw new PendingException();
	}

	@When("^I attempt to start the game$")
	public void i_attempt_to_start_the_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		spadeGame = spadeService.startGame(gameId);
	
	
	}

	@Then("^I should be successful in starting the game$")
	public void i_should_be_successful_in_starting_the_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);
		
	}

}
