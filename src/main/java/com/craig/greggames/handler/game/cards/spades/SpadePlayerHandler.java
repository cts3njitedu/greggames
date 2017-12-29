package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

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
		currPlayer.setHasPlayed(true);
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

		return newSpadeGame.getTeams().get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(player);

	}

	public void determineTurn(SpadeGame newSpadeGame) {

		PlayerTable currPlayer = newSpadeGame.getCurrTurn();

		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				if (entryPlayer.getKey() == currPlayer) {

					entryPlayer.getValue().setTurn(true);
				} else {
					entryPlayer.getValue().setTurn(false);
				}
			}
		}
	}

	public void cleanUpErrors(SpadeGame newSpadeGame) {

		
		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				entryPlayer.getValue().setError(false);
				Map<SpadeErrors,String> messages= new EnumMap<SpadeErrors,String>(SpadeErrors.class);
				entryPlayer.getValue().setErrorMessages(messages);
			}
		}
	}

}
