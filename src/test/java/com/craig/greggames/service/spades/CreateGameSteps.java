package com.craig.greggames.service.spades;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.craig.cards.GameId;
import com.craig.cards.World;
import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.controller.message.MessageController;
import com.craig.greggames.model.spades.SpadeGame;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
public class CreateGameSteps {

	private SpadeGame spadeGame;
	@Autowired
	private MessageController gameService;


	

	@Given("^a initial game$")
	public void a_initial_game() throws Throwable {
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

	}

	@When("^I attempt to create the game$")
	public void i_attempt_to_create_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		spadeGame = gameService.createGame("spades", spadeGame);
	}

	@Then("^I should be successful in creating the game$")
	public void i_should_be_successful_in_creating_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);
		
		//game.setGameId(spadeGame.getGameId());
		
	}
}
