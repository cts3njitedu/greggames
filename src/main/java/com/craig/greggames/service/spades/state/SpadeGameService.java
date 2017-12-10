package com.craig.greggames.service.spades.state;

import static com.craig.greggames.constants.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.service.cards.CardService;;
@Service
public class SpadeGameService {

	private Random rand = new Random();
	
	@Autowired
	private SpadeBidderService bidderService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private SpadeTeamService teamService;
	
	@Autowired
	private SpadePlayerService playerService;
	
	public void createGame(SpadeGame newSpadeGame) {
		
		
		newSpadeGame.setSpadePlayed(false);
		newSpadeGame.setActivePlayers(newSpadeGame.getActivePlayers());
		newSpadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
		newSpadeGame.setStarting(true);
		teamService.makeTeams(newSpadeGame);
		
		
	}
	
	public void startGame(SpadeGame newSpadeGame) {

		int start = rand.nextInt(MAX_TURN_PER_TRICK) + 1;
		newSpadeGame.setBidding(true);
		newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
		newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
		newSpadeGame.setHandCount(1);
		cardService.distributeCards(newSpadeGame);
		newSpadeGame.setStarting(false);
		newSpadeGame.setTurnCount(1);
		bidderService.cleanUpBid(newSpadeGame);
	}

	public void joinGame(SpadeGame newSpadeGame) {
		
		
	}
	public void play(SpadeGame newSpadeGame) {

		if (newSpadeGame.isStarting()) {

			teamService.makeTeams(newSpadeGame);
			int start = rand.nextInt(MAX_TURN_PER_TRICK) + 1;
			newSpadeGame.setBidding(true);
			newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
			newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setHandCount(1);
			cardService.distributeCards(newSpadeGame);
			newSpadeGame.setStarting(false);
			newSpadeGame.setTurnCount(1);
			bidderService.cleanUpBid(newSpadeGame);

		}

		else if (newSpadeGame.isBidding()) {

			// check the turn count. if 4 set bidding to false;
			bidderService.determineBid(newSpadeGame);
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				newSpadeGame.setCurrTurn(newSpadeGame.getStartTurn());
				newSpadeGame.setBidding(false);

				newSpadeGame.setTurnCount(1);
				newSpadeGame.setTrickCount(1);
			} else {

				int currTurnCode = newSpadeGame.getCurrTurn().getCode() + 1;

				if (currTurnCode > MAX_TURN_PER_TRICK) {
					currTurnCode = currTurnCode - MAX_TURN_PER_TRICK;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);
			}

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

				if (newSpadeGame.getTrickCount() == MAX_TRICK_COUNT) {

					// determine the total team score;
					teamService.determineTeamPoints(newSpadeGame);
					// determine if someone won the game;
					teamService.determineGameWinner(newSpadeGame);
					int start = newSpadeGame.getStartHand().getCode() + 1;
					if (start > MAX_TURN_PER_TRICK) {
						start = start - MAX_TURN_PER_TRICK;
					}
					newSpadeGame.setBidding(true);
					newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
					newSpadeGame.setStartTurn(newSpadeGame.getStartHand());
					newSpadeGame.setCurrTurn(newSpadeGame.getStartHand());

					cardService.distributeCards(newSpadeGame);
					teamService.cleanUpPoints(newSpadeGame);
					bidderService.cleanUpBid(newSpadeGame);

					newSpadeGame.setHandCount(newSpadeGame.getHandCount() + 1);
					newSpadeGame.setTrickCount(0);
					newSpadeGame.setTurnCount(1);

				} else {

					newSpadeGame.setCurrTurn(temWinnerPlayer);
					newSpadeGame.setStartTurn(temWinnerPlayer);
					newSpadeGame.setTurnCount(1);
					newSpadeGame.setTrickCount(newSpadeGame.getTrickCount() + 1);

				}
				cardService.removePlayingCard(newSpadeGame);

			
			} else {

				cardService.reAdjustCards(newSpadeGame);
				playerService.determinePlayerWinner(newSpadeGame);
				int curr = newSpadeGame.getCurrTurn().getCode() + 1;
				if (curr > MAX_TURN_PER_TRICK) {
					curr = curr - MAX_TURN_PER_TRICK;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);

			}

		}

	}



	
	
	


	

	



}
