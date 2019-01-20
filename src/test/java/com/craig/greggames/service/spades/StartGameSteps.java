package com.craig.greggames.service.spades;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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
public class StartGameSteps {
	
	@Autowired
	private SpadeService spadeService;

	private JsonConverter jsonConverter;

	@Before
	public void setUp() {
		jsonConverter = new JsonConverter();
	}
	private SpadeGame spadeGame;


	@Given("^a created game$")
	public void a_created_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		spadeGame = jsonConverter.getSpadeGameState("gamestates/spades/startgamereq.json", SpadeGame.class);

	}

	@When("^I attempt to start the game$")
	public void i_attempt_to_start_the_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		spadeService.playGame(spadeGame);
	
	
	}

	@Then("^I should be successful in starting the game$")
	public void i_should_be_successful_in_starting_the_game() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
		System.out.println(json);
		
	}

}
