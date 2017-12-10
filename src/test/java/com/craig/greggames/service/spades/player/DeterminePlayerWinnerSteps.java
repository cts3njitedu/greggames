package com.craig.greggames.service.spades.player;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.craig.cards.TempWinner;
import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.handler.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.Deck;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
public class DeterminePlayerWinnerSteps {

	List<SpadeGame> spadeGames = new ArrayList<SpadeGame>();

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadePlayerHandler playerService;

	@Given("^a state of game, a temp winner and the current player and temp winner playing card$")
	public void makeState(List<TempWinner> tempWinner) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)

		for (TempWinner temp : tempWinner) {

			SpadeGame spadeGame = new SpadeGame();

			spadeGame.setStarting(true);
			spadeGame.setBags(50);
			spadeGame.setBagPoints(100);
			spadeGame.setGameOver(false);
			spadeGame.setPointsToWin(150);
			spadeGame.setNumberOfTeams(2);
			spadeGame.setBidNilPoints(100);
			spadeGame.setPointsToLose(-500);
			spadeGame.setTurnCount(temp.getTrickCount());

			spadeGame.setCurrTurn(PlayerTable.PLAYER2);
			spadeGame.setTempWinner(PlayerTable.PLAYER1);
			teamService.makeTeams(spadeGame);
			Card tempWinnerCard = Deck.getCardByName(temp.getTempWinnerCard());
			

			Card currPlayerCard = Deck.getCardByName(temp.getCurrPlayerCard());

			SpadePlayer tempPlayer = spadeGame.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER1);

			SpadePlayer currPlayer = spadeGame.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER2);

			tempPlayer.setPlayingCard(tempWinnerCard);
			currPlayer.setPlayingCard(currPlayerCard);

			spadeGames.add(spadeGame);

		}

	}

	@When("^I determine who the temp winner is$")
	public void i_determine_who_the_temp_winner_is() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		for (SpadeGame game : spadeGames) {

			playerService.determinePlayerWinner(game);
		}
	}

	@Then("^I should be successful$")
	public void i_should_be_successful() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		int counter =1;
		for (SpadeGame game : spadeGames) {

			System.out.println("Count: " + counter++);
			System.out.println("Temp Winner: " + game.getTempWinner());

			SpadePlayer tempPlayer = game.getTeams().get(TeamTable.TEAM1).getPlayers().get(PlayerTable.PLAYER1);

			SpadePlayer currPlayer = game.getTeams().get(TeamTable.TEAM2).getPlayers().get(PlayerTable.PLAYER2);

			System.out.println("Temp Player 1: " + tempPlayer.isWon());
			System.out.println("Curr Player 2: " + currPlayer.isWon());
		}
	}

}
