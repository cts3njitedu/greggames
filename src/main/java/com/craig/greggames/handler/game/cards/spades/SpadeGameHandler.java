package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;;
@Service
public class SpadeGameHandler {

	private Random rand = new Random();
	
	@Autowired
	private SpadeBidderHandler bidderService;
	
	@Autowired
	private CardHandler cardService;
	
	@Autowired
	private SpadeTeamHandler teamService;
	
	@Autowired
	private SpadePlayerHandler playerService;
	
	@Autowired
	private SpadeMetaDataHandler metaDataService;
	
	public void create(SpadeGame newSpadeGame) {
		
		
		newSpadeGame.setSpadePlayed(false);
		
		newSpadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
		newSpadeGame.setGameNotification(SpadeNotifications.START);
		teamService.makeTeams(newSpadeGame);
		
		
	}
	
	public void start(SpadeGame newSpadeGame) {

		int start = rand.nextInt(MAX_TURN_PER_TRICK) + 1;
		
		newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
		newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setHandCount(1);
		cardService.distributeCards(newSpadeGame);
		newSpadeGame.setSpadePlayed(false);
		newSpadeGame.setGameNotification(SpadeNotifications.BID);
		newSpadeGame.setTurnCount(1);
		bidderService.cleanUpBid(newSpadeGame);
		playerService.determineTurn(newSpadeGame);
	}


	public void bid(SpadeGame spadeGame) {
		
		bidderService.determineBid(spadeGame);
		spadeGame.setSpadePlayed(false);
		if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

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

		playerService.determineTurn(spadeGame);
		
	}
	public void play(SpadeGame spadeGame){

		if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

			// determine who won the trick
			playerService.determinePlayerWinner(spadeGame);
			// determine the amount of points the winner has
			playerService.determinePlayerPoints(spadeGame);
			// reAdjust cards. should return boolean
			cardService.reAdjustCards(spadeGame);
			PlayerTable temWinnerPlayer = spadeGame.getTempWinner();
			spadeGame.setTempWinner(null);
			metaDataService.addPreviousTrick(spadeGame);

			if (spadeGame.getTrickCount() == MAX_TRICK_COUNT) {

				// determine the total team score;
				teamService.determineTeamPoints(spadeGame);
				// determine if someone won the game;
				teamService.determineGameWinner(spadeGame);
				metaDataService.addPreviousHand(spadeGame);

				spadeGame.setTrickOver(true);
				spadeGame.setHandOver(true);
				if (spadeGame.isGameOver()) {

					spadeGame.setTeams(null);
					spadeGame.setPlaying(false);
					create(spadeGame);
					start(spadeGame);

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

					cardService.distributeCards(spadeGame);
					teamService.cleanUpPoints(spadeGame);
					bidderService.cleanUpBid(spadeGame);

					spadeGame.setHandCount(spadeGame.getHandCount() + 1);
					spadeGame.setTrickCount(0);
					spadeGame.setTurnCount(1);
					playerService.cleanUpWhoHasPlayed(spadeGame);
					cardService.removePlayingCard(spadeGame);
					playerService.determineTurn(spadeGame);

				}

			} else {

				spadeGame.setCurrTurn(temWinnerPlayer);
				spadeGame.setStartTurn(temWinnerPlayer);
				spadeGame.setTurnCount(1);
				spadeGame.setTrickCount(spadeGame.getTrickCount() + 1);
				spadeGame.setGameNotification(SpadeNotifications.PLAY);
				spadeGame.setTrickOver(true);
				playerService.cleanUpWhoHasPlayed(spadeGame);
				cardService.removePlayingCard(spadeGame);
				playerService.determineTurn(spadeGame);

			}
			

		} else {

			playerService.determinePlayerWinner(spadeGame);
			cardService.reAdjustCards(spadeGame);
			int curr = spadeGame.getCurrTurn().getCode() + 1;
			if (curr > MAX_TURN_PER_TRICK) {
				curr = curr - MAX_TURN_PER_TRICK;
			}
			spadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
			spadeGame.setTurnCount(spadeGame.getTurnCount() + 1);
			spadeGame.setGameNotification(SpadeNotifications.PLAY);
			playerService.determineTurn(spadeGame);

		}
		
		

	}



	
	
	


	

	



}
