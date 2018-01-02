package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;;
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
	private SpadeValidationHandler validationHandler;
	
	@Autowired
	private SpadeMetaDataHandler metaDataService;
	
	public void createGame(SpadeGame newSpadeGame) {
		
		
		newSpadeGame.setSpadePlayed(false);
		
		newSpadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
		newSpadeGame.setGameNotification(SpadeNotifications.START);
		teamService.makeTeams(newSpadeGame);
		
		
	}
	
	public void startGame(SpadeGame newSpadeGame) {

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


	public void play(SpadeGame newSpadeGame) throws GreggamesException {

		newSpadeGame.setPreviousHand(null);
		newSpadeGame.setPreviousTrick(null);
		if (newSpadeGame.getPlayerNotification()==SpadeNotifications.BID) {

			// check the turn count. if 4 set bidding to false;
			
			
			
			bidderService.determineBid(newSpadeGame);
			newSpadeGame.setSpadePlayed(false);
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				newSpadeGame.setCurrTurn(newSpadeGame.getStartTurn());
			
				newSpadeGame.setGameNotification(SpadeNotifications.PLAY);

				newSpadeGame.setTurnCount(1);
				newSpadeGame.setTrickCount(1);
			} else {

				int currTurnCode = newSpadeGame.getCurrTurn().getCode() + 1;

				if (currTurnCode > MAX_TURN_PER_TRICK) {
					currTurnCode = currTurnCode - MAX_TURN_PER_TRICK;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);
				newSpadeGame.setGameNotification(SpadeNotifications.BID);
			}
			
			playerService.determineTurn(newSpadeGame);

		} else {

			
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				// determine who won the trick
				playerService.determinePlayerWinner(newSpadeGame);
				// determine the amount of points the winner has
				playerService.determinePlayerPoints(newSpadeGame);
				// reAdjust cards. should return boolean
				cardService.reAdjustCards(newSpadeGame);
				PlayerTable temWinnerPlayer = newSpadeGame.getTempWinner();
				newSpadeGame.setTempWinner(null);
				metaDataService.addPreviousTrick(newSpadeGame);
				
				
				if (newSpadeGame.getTrickCount() == MAX_TRICK_COUNT) {

					// determine the total team score;
					teamService.determineTeamPoints(newSpadeGame);
					// determine if someone won the game;
					teamService.determineGameWinner(newSpadeGame);
					metaDataService.addPreviousHand(newSpadeGame);
					int start = newSpadeGame.getStartHand().getCode() + 1;
					if (start > MAX_TURN_PER_TRICK) {
						start = start - MAX_TURN_PER_TRICK;
					}
					newSpadeGame.setBidding(true);
					newSpadeGame.setPlaying(false);
					newSpadeGame.setGameNotification(SpadeNotifications.BID);
					newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
					newSpadeGame.setStartTurn(newSpadeGame.getStartHand());
					newSpadeGame.setCurrTurn(newSpadeGame.getStartHand());

					cardService.distributeCards(newSpadeGame);
					teamService.cleanUpPoints(newSpadeGame);
					bidderService.cleanUpBid(newSpadeGame);

					newSpadeGame.setHandCount(newSpadeGame.getHandCount() + 1);
					newSpadeGame.setTrickCount(0);
					newSpadeGame.setTurnCount(1);
					newSpadeGame.setTrickOver(true);
					newSpadeGame.setHandOver(true);

				} else {

					newSpadeGame.setCurrTurn(temWinnerPlayer);
					newSpadeGame.setStartTurn(temWinnerPlayer);
					newSpadeGame.setTurnCount(1);
					newSpadeGame.setTrickCount(newSpadeGame.getTrickCount() + 1);
					newSpadeGame.setGameNotification(SpadeNotifications.PLAY);
					newSpadeGame.setTrickOver(true);

				}
				playerService.cleanUpWhoHasPlayed(newSpadeGame);
				cardService.removePlayingCard(newSpadeGame);
				
				

			
			} else {

				
				playerService.determinePlayerWinner(newSpadeGame);
				cardService.reAdjustCards(newSpadeGame);
				int curr = newSpadeGame.getCurrTurn().getCode() + 1;
				if (curr > MAX_TURN_PER_TRICK) {
					curr = curr - MAX_TURN_PER_TRICK;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);
				newSpadeGame.setGameNotification(SpadeNotifications.PLAY);

			}
			playerService.determineTurn(newSpadeGame);

		}

	}



	
	
	


	

	



}
