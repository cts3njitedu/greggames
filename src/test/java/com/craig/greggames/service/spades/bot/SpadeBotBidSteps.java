package com.craig.greggames.service.spades.bot;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.craig.cards.Players;
import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.Deck;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.utility.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



@ContextConfiguration(classes = CardGamesApplication.class)
public class SpadeBotBidSteps {

	@Autowired
	private SpadeBotHandler botHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	private JsonConverter jsonConverter;

	@Before
	public void setUp() {
		jsonConverter = new JsonConverter();
	}
	
	private Card playingCard;
	
	private SpadeGame spadeGame;
	@Given("^a game and players cards for bot$")
	public void a_game_and_players_cards_for_bot(List<Players>players) throws Throwable {
	
		
		spadeGame = jsonConverter.getSpadeGameState("gamestates/spades/gameinprogressreq.json", SpadeGame.class);
		spadeGame.setCurrTurn(PlayerTable.PLAYER3);
		
		
		SpadePlayer spadePlayer1=null;
		TeamTable team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER1, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer1.setPlayingCard(Deck.FIVE_DIAMONDS.getCard());
		
		
		SpadePlayer spadePlayer2 = null;
		TeamTable team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER2, spadeGame.getNumberOfTeams());
		spadePlayer2 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer2.setPlayingCard(Deck.SIX_DIAMONDS.getCard());
		
	
		SpadePlayer spadePlayer3=null;
		team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER3, spadeGame.getNumberOfTeams());
		spadePlayer3 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer3.setPlayingCard(Deck.NINE_DIAMONDS.getCard());
		
		spadeGame.setStartTurn(PlayerTable.PLAYER3);
		spadeGame.setTempWinner(PlayerTable.PLAYER2);
		
	}

	@When("^I attempt to play the game for one hand$")
	public void i_attempt_to_play_the_game_for_one_hand() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
		//playingCard = botHandler.determineBotCard(spadeGame);
		
	}

	@Then("^I should be successful in playing the game$")
	public void i_should_be_successful_in_playing_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(playingCard);
		System.out.println(json);
		
	}

}
