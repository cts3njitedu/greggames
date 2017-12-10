package com.craig.greggames.service.spades.state.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;
import com.craig.greggames.model.team.TeamTable;
import com.craig.greggames.service.spades.state.SpadeTeamService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
public class DetermineGameWinnerSteps {

	@Autowired
	private SpadeTeamService teamService;

	private List<SpadeGame> games = new ArrayList<SpadeGame>();

	@Given("^a spade game state and the final scores for each team$")
	public void makeGame(List<List<Integer>> teamList) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)

		for (List<Integer> teams : teamList) {

			SpadeGame spadeGame = new SpadeGame();
			spadeGame.setStarting(true);
			spadeGame.setBags(50);
			spadeGame.setBagPoints(100);
			spadeGame.setGameOver(false);
			spadeGame.setPointsToWin(150);
			spadeGame.setNumberOfTeams(2);
			spadeGame.setBidNilPoints(100);
			spadeGame.setPointsToLose(-500);

			teamService.makeTeams(spadeGame);

			SpadeTeam team1 = spadeGame.getTeams().get(TeamTable.TEAM1);
			SpadeTeam team2 = spadeGame.getTeams().get(TeamTable.TEAM2);
			team1.setTotalScore(teams.get(0));
			team2.setTotalScore(teams.get(1));

			games.add(spadeGame);

		}

	}

	@When("^I try to determine the team who won$")
	public void i_try_to_determine_the_team_who_won() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		for (SpadeGame game : games) {

			teamService.determineGameWinner(game);
		}

	}

	@Then("^I should get the correct team$")
	public void i_should_get_the_correct_team() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		for (SpadeGame game : games) {

			SpadeTeam team1 = game.getTeams().get(TeamTable.TEAM1);
			SpadeTeam team2 = game.getTeams().get(TeamTable.TEAM2);
			System.out.println("Team 1: " + team1.getTotalScore() + ", Won? " + team1.isWon() + ", Team2: "
					+ team2.getTotalScore() + ", Won? " + team2.isWon());
		}
	}
}
