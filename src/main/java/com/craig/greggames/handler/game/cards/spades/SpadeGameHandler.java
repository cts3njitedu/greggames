package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;;
@Service
public class SpadeGameHandler {

	
	
	@Autowired
	private SpadeBidderHandler spadeBidderHandler;
	
	@Autowired
	private CardHandler cardHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	
	@Autowired
	private SpadeMetaDataHandler spadeMetaDataHandler;
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	
	private static final Logger logger = Logger.getLogger(SpadeGameHandler.class);

	
	public void create(SpadeGame newSpadeGame) {
		
		logger.info("Creating game");
		
		newSpadeGame.setSpadePlayed(false);
		
		newSpadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
		newSpadeGame.setGameNotification(SpadeNotifications.START);
		spadeTeamHandler.makeTeams(newSpadeGame);
		spadePlayerHandler.setPlayerPosition(newSpadeGame);
		
		logger.info("New created game " + newSpadeGame);
		
		
	}
	
	public void start(SpadeGame newSpadeGame) {

		logger.info("Starting game");
		Random rand = new Random();
		int start = rand.nextInt(MAX_TURN_PER_TRICK) + 1;
		
		newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
		newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setHandCount(1);
		cardHandler.distributeCards(newSpadeGame);
		newSpadeGame.setSpadePlayed(false);
		newSpadeGame.setGameNotification(SpadeNotifications.BID);
		newSpadeGame.setTurnCount(1);
		spadeBidderHandler.cleanUpBid(newSpadeGame);
		spadePlayerHandler.determineTurn(newSpadeGame);
	}


	public void bid(SpadeGame spadeGame) {
		logger.info("Bidding...");
		spadeBidderHandler.determineBid(spadeGame);
		spadeGame.setSpadePlayed(false);
		if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {
			logger.info("Final turn for trick number: " + spadeGame.getTrickCount());
			spadeGame.setCurrTurn(spadeGame.getStartTurn());

			spadeGame.setGameNotification(SpadeNotifications.PLAY);

			spadeGame.setTurnCount(1);
			spadeGame.setTrickCount(1);
		} else {

			int currTurnCode = spadeGame.getCurrTurn().getCode() + 1;

			if (currTurnCode > MAX_TURN_PER_TRICK) {
				currTurnCode = currTurnCode - MAX_TURN_PER_TRICK;
			}
			spadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
			spadeGame.setTurnCount(spadeGame.getTurnCount() + 1);
			spadeGame.setGameNotification(SpadeNotifications.BID);
		}

		spadePlayerHandler.determineTurn(spadeGame);
		
	}
	public void play(SpadeGame spadeGame){
//		spadeBotHandler.determineBotCard(spadeGame);
		logger.info("Playing game...");
		if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {
			logger.info("Max turn for trick count: " + spadeGame.getTrickCount());
			// determine who won the trick
			spadePlayerHandler.determinePlayerWinner(spadeGame);
			// determine the amount of points the winner has
			spadePlayerHandler.determinePlayerPoints(spadeGame);
			// reAdjust cards. should return boolean
			cardHandler.reAdjustCards(spadeGame);
			PlayerTable temWinnerPlayer = spadeGame.getTempWinner();
			spadeGame.setTempWinner(null);
			spadeMetaDataHandler.addPreviousTrick(spadeGame);

			if (spadeGame.getTrickCount() == MAX_TRICK_COUNT) {
				logger.info("Max trick count for hand count: " + spadeGame.getHandCount());
				// determine the total team score;
				spadeTeamHandler.determineTeamPoints(spadeGame);
				// determine if someone won the game;
				spadeTeamHandler.determineGameWinner(spadeGame);
				spadeMetaDataHandler.addPreviousHand(spadeGame);

				spadeGame.setTrickOver(true);
				spadeGame.setHandOver(true);
				if (spadeGame.isGameOver()) {
					logger.info("Game is over for game id " + spadeGame.getGameId());
//					create(spadeGame);
//					start(spadeGame);

				} else {

					int start = spadeGame.getStartHand().getCode() + 1;
					if (start > MAX_TURN_PER_TRICK) {
						start = start - MAX_TURN_PER_TRICK;
					}

					spadeGame.setBidding(true);
					spadeGame.setPlaying(false);
					spadeGame.setGameNotification(SpadeNotifications.BID);
					spadeGame.setStartHand(PlayerTable.getPlayer(start));
					spadeGame.setStartTurn(spadeGame.getStartHand());
					spadeGame.setCurrTurn(spadeGame.getStartHand());

//					cardHandler.distributeCards(spadeGame);
//					spadeTeamHandler.cleanUpPoints(spadeGame);
//					spadeBidderHandler.cleanUpBid(spadeGame);

					spadeGame.setHandCount(spadeGame.getHandCount() + 1);
					spadeGame.setTrickCount(0);
					spadeGame.setTurnCount(1);
//					spadePlayerHandler.cleanUpWhoHasPlayed(spadeGame);
//					spadePlayerHandler.determineTurn(spadeGame);

				}

			} else {

				spadeGame.setCurrTurn(temWinnerPlayer);
				spadeGame.setStartTurn(temWinnerPlayer);
				spadeGame.setTurnCount(1);
				spadeGame.setTrickCount(spadeGame.getTrickCount() + 1);
				spadeGame.setGameNotification(SpadeNotifications.PLAY);
				spadeGame.setTrickOver(true);
				spadePlayerHandler.cleanUpWhoHasPlayed(spadeGame);
//				cardHandler.removePlayingCard(spadeGame);
				spadePlayerHandler.determineTurn(spadeGame);

			}
			

		} else {

			spadePlayerHandler.determinePlayerWinner(spadeGame);
			cardHandler.reAdjustCards(spadeGame);
			int curr = spadeGame.getCurrTurn().getCode() + 1;
			if (curr > MAX_TURN_PER_TRICK) {
				curr = curr - MAX_TURN_PER_TRICK;
			}
			spadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
			spadeGame.setTurnCount(spadeGame.getTurnCount() + 1);
			spadeGame.setGameNotification(SpadeNotifications.PLAY);
			spadePlayerHandler.determineTurn(spadeGame);

		}
		
		

	}
	
	
	public void leaveGame(SpadeGame spadeGame) {
		logger.info("Leaving game is player " + spadeGame.getGameModifier());
		SpadePlayer leavingPlayer = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
		.getPlayers().get(spadeGame.getGameModifier());
		
		leavingPlayer.setBot(true);
		leavingPlayer.setUserId(null);
		spadeBotHandler.determineBots(spadeGame);
		
	}



	
	
	


	

	



}
