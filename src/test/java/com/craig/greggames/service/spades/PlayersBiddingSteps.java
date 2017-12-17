//package com.craig.greggames.service.spades;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.craig.greggames.controller.game.cards.spades.SpadesController;
//import com.craig.greggames.controller.game.cards.spades.socket.SpadesSocketController;
//import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
//import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
//import com.craig.greggames.model.game.cards.spades.SpadeGame;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cucumber.api.PendingException;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
//public class PlayersBiddingSteps {
//
//	private SpadeGame spadeGame;
//	@Autowired
//	private SpadesController spadeController;
//
//	@Autowired
//	private SpadesSocketController messageController;
//
//	@Autowired
//	private SpadeTeamHandler teamService;
//
//	@Given("^a game and players ready to bid$")
//	public void a_game_and_players_ready_to_bid() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//
//		spadeGame = new SpadeGame();
//
//		spadeGame.setActivePlayers(0);
//		spadeGame.setBagPoints(100);
//		spadeGame.setBags(50);
//		spadeGame.setBidNilPoints(100);
//		spadeGame.setStarting(true);
//		spadeGame.setPointsToWin(150);
//		spadeGame.setPointsToLose(-100);
//		spadeGame.setNumberOfTeams(2);
//		spadeGame = messageController.createGame("", spadeGame);
//		spadeGame = spadeController.startGame(spadeGame.getGameId());
//
//	}
//
//	@When("^I attempt to bid$")
//	public void i_attempt_to_bid() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
//				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(50);
//
//		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
//		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
//				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(40);
//
//		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
//		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
//				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(30);
//		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
//
//		spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
//				.getPlayers().get(spadeGame.getCurrTurn()).setPlayerBid(60);
//		spadeGame = messageController.modifyGameState("spade", spadeGame.getGameId(), spadeGame);
//		// throw new PendingException();
//	}
//
//	@Then("^I should be successful in bidding$")
//	public void i_should_be_successful_in_bidding() throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		ObjectMapper mapper = new ObjectMapper();
//
//		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(spadeGame);
//		System.out.println(json);
//
//	}
//}
