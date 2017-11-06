package com.craig.greggames.service.spades.state;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;

@Service
public class SpadePlayerService {
	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getCurrTurn().getCode();

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(code, newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(PlayerTable.getPlayer(code));

		if (newSpadeGame.getTempWinner() == null) {

			currPlayer.setWon(true);
			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams().get(
					TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer gameWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {

					currPlayer.setWon(true);
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					currPlayer.setWon(true);
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {

					currPlayer.setWon(false);
				}
			}
		}

	}

	public void determinePlayerPoints(SpadeGame newSpadeGame) {

		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		spadePlayer.setWon(true);
		spadePlayer.setTurn(true);
		spadePlayer.setPlayerCurrentScore(spadePlayer.getPlayerCurrentScore() + 10);

		newSpadeGame.setStartTurn(spadePlayer.getName());

	}

	

}
