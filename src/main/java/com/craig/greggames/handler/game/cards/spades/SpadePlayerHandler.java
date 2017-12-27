package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;


@Service
public class SpadePlayerHandler {

	@Autowired
	private CardHandler cardService;
	@Autowired
	private SpadeTeamHandler teamService;



	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getCurrTurn());

		if (newSpadeGame.getTempWinner() == null) {
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				currPlayer.setWon(true);
			}

			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer tempWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card tempWinnerCard = tempWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (tempWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > tempWinnerCard.getValue().getValue()) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					tempWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						tempWinner.setWon(true);
					}
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					tempWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						tempWinner.setWon(true);
					}

					currPlayer.setWon(false);
				}
			}
		}

	}

	public void determinePlayerPoints(SpadeGame newSpadeGame) {

		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		spadePlayer.setWon(true);
		spadePlayer.setTurn(true);
		spadePlayer.setPlayerCurrentScore(spadePlayer.getPlayerCurrentScore() + POINTS_WON_PER_TRICK_BEFORE_OVERBID);

		newSpadeGame.setStartTurn(spadePlayer.getName());

	}
	
	public SpadePlayer retrievePlayer(PlayerTable player, SpadeGame newSpadeGame) {
		
		return newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(player);
		
	}

	
}
