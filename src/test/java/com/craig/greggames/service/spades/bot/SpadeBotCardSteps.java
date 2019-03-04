package com.craig.greggames.service.spades.bot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.craig.cards.PlayerCardGenerators;
import com.craig.cards.Players;
import com.craig.cards.TestConfiguration;
import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.util.GregMapper;
import com.craig.greggames.utility.JsonConverter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = {CardGamesApplication.class,TestConfiguration.class})
public class SpadeBotCardSteps {
	
	@Autowired
	private GregMapper gregMapper;
	
	@Autowired
	private SpadeBotHandler botHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	@Autowired
	private PlayerCardGenerators playerCardGenerators;
	private SpadeGame spadeGame;
	
	
	Card card = null;
	@Given("^a game state with other card already played$")
	public void a_game_state_with_other_card_already_played(List<Players> players) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		JsonConverter jsonConverter = new JsonConverter();
		spadeGame = jsonConverter.getSpadeGameState("gamestates/spades/gameinprogressreq.json", SpadeGame.class);
//		playerCardGenerators.leadPlayerBidNil(spadeGame);
		playerCardGenerators.thirdPositionLeadPlayerBidNilWinning(spadeGame);

	}

	@When("^I attempt to get a card for a bot$")
	public void i_attempt_to_get_a_card_for_a_bot() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	  botHandler.determineBotCard(spadeGame);
	}

	@Then("^I should be successful in get card for bot$")
	public void i_should_be_successful_in_get_card_for_bot() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   SpadePlayer spadePlayer = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
			   				.getPlayers().get(spadeGame.getCurrTurn());
	   System.out.println(gregMapper.convertObjectToString(spadePlayer.getPlayingCard()));
	}
}
