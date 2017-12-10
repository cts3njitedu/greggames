package com.craig.greggames.service.spades.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.Deck;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.team.TeamTable;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DeterminePlayerCardSteps {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadePlayerHandler playerService;

	private SpadeGame spadeGame;

	@Given("^a currplayer and tempwinner$")
	public void a_currplayer_and_tempwinner() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		spadeGame = new SpadeGame();

		spadeGame.setNumberOfTeams(2);
		teamService.makeTeams(spadeGame);

		spadeGame.setCurrTurn(PlayerTable.PLAYER2);

		spadeGame.setTempWinner(PlayerTable.PLAYER1);
		spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(spadeGame.getTempWinner())
				.setPlayingCard(Deck.EIGHT_CLUBS.getCard());

		List<Card> currPlayerCards = new ArrayList<Card>();

//		currPlayerCards.add(Deck.ACE_CLUBS.getCard());
//		currPlayerCards.add(Deck.NINE_CLUBS.getCard());
//		currPlayerCards.add(Deck.NINE_SPADES.getCard());
//		currPlayerCards.add(Deck.FIVE_SPADES.getCard());
		currPlayerCards.add(Deck.TEN_DIAMONDS.getCard());
		currPlayerCards.add(Deck.TWO_DIAMONDS.getCard());
		currPlayerCards.add(Deck.SEVEN_HEARTS.getCard());
		currPlayerCards.add(Deck.FOUR_HEARTS.getCard());

		spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(spadeGame.getTempWinner())
				.setPlayingCard(Deck.EIGHT_CLUBS.getCard());

		spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(spadeGame.getCurrTurn()).getRemainingCards()
				.addAll(currPlayerCards);
	}

	@When("^I determine player card$")
	public void i_determine_player_card() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		playerService.determinePlayerCard(spadeGame);
	}

	@Then("^I should be correct in determining player card$")
	public void i_should_be_correct_in_determining_player_card() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		System.out.println("Temp Winner Card: " + spadeGame.getTeams().get(TeamTable.TEAM1).
				getPlayers().get(spadeGame.getTempWinner()).getPlayingCard());
		System.out.println("Current Player Card: " + spadeGame.getTeams().get(TeamTable.TEAM2).
				getPlayers().get(spadeGame.getCurrTurn()).getPlayingCard());
	    
	}

}
