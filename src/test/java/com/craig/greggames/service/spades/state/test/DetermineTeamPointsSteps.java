//package com.craig.greggames.service.spades.state.test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map.Entry;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootContextLoader;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.craig.cards.PlayerScores;
//import com.craig.greggames.CardGamesApplication;
//import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
//import com.craig.greggames.model.game.cards.player.PlayerTable;
//import com.craig.greggames.model.game.cards.spades.SpadeGame;
//import com.craig.greggames.model.game.cards.spades.SpadePlayer;
//import com.craig.greggames.model.game.cards.spades.SpadeTeam;
//import com.craig.greggames.model.game.cards.team.TeamTable;
//
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@ContextConfiguration(loader = SpringBootContextLoader.class, classes = CardGamesApplication.class)
//public class DetermineTeamPointsSteps {
//
//	@Autowired
//	private SpadeTeamHandler teamService;
//
//
//	private List<SpadeGame> games = new ArrayList<SpadeGame>();
//
//	@Given("^a spade game state and bids and scores$")
//	public void makeGameState(List<PlayerScores> playerScores) throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		// For automatic transformation, change DataTable to one of
//		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
//		// E,K,V must be a scalar (String, Integer, Date, enum etc)
//		for (PlayerScores playerScore : playerScores) {
//			SpadeGame spadeGame = new SpadeGame();
//			spadeGame.setStarting(true);
//			spadeGame.setBags(50);
//			spadeGame.setBagPoints(100);
//			spadeGame.setGameOver(false);
//			spadeGame.setPointsToWin(150);
//			spadeGame.setNumberOfTeams(2);
//			spadeGame.setBidNilPoints(100);
//			spadeGame.setPointsToLose(-500);
//
//			teamService.makeTeams(spadeGame);
//
//			SpadeTeam team1 = spadeGame.getTeams().get(TeamTable.TEAM1);
//			SpadeTeam team2 = spadeGame.getTeams().get(TeamTable.TEAM2);
//			SpadePlayer player1 = team1.getPlayers().get(PlayerTable.PLAYER1);
//
//			player1.setPlayerBid(playerScore.getP1_bid());
//			player1.setPlayerCurrentScore(playerScore.getP1_score());
//
//			SpadePlayer player3 = team1.getPlayers().get(PlayerTable.PLAYER3);
//
//			player3.setPlayerBid(playerScore.getP3_bid());
//			player3.setPlayerCurrentScore(playerScore.getP3_score());
//
//			team1.setTotalBid(player1.getPlayerBid() + player3.getPlayerBid());
//
//			SpadePlayer player2 = team2.getPlayers().get(PlayerTable.PLAYER2);
//
//			player2.setPlayerBid(playerScore.getP2_bid());
//			player2.setPlayerCurrentScore(playerScore.getP2_score());
//
//			SpadePlayer player4 = team2.getPlayers().get(PlayerTable.PLAYER4);
//
//			player4.setPlayerBid(playerScore.getP4_bid());
//			player4.setPlayerCurrentScore(playerScore.getP4_score());
//
//			team2.setTotalBid(player2.getPlayerBid() + player4.getPlayerBid());
//			games.add(spadeGame);
//
//		}
//
//	}
//
//	@When("^I try to determine the points for each team$")
//	public void i_try_to_determine_the_points_for_each_team() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		
//		for(SpadeGame game: games) {
//			
//			teamService.determineTeamPoints(game);
//		}
//		
//
//	}
//
//	@Then("^I should get the correct points for each team$")
//	public void i_should_get_the_correct_points_for_each_team() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		for(SpadeGame game: games) {
//			
//			for(Entry<TeamTable,SpadeTeam> team: game.getTeams().entrySet()) {
//				System.out.println(team.getKey()+ ":"+team.getValue().getTotalScore());
//			}
//		}
//	}
//}
