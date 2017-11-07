package com.craig.greggames.service.spades.state;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;
import com.craig.greggames.service.cards.CardService;

@Service
public class SpadePlayerService {

	@Autowired
	private CardService cardService;
	public void determinePlayerCard(SpadeGame newSpadeGame) {

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(newSpadeGame.getCurrTurn().getCode(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getCurrTurn());
		if (currPlayer.isBot()) {

			SpadePlayer winPlayer = newSpadeGame.getTeams().get(
					TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(newSpadeGame.getTempWinner());
			Card winnerCard = winPlayer.getPlayingCard();
			List<Card>sortedCards = new ArrayList<Card>(currPlayer.getRemainingCards());
			
			cardService.sortCards(sortedCards);
			
			for(Card sortedCard: sortedCards) {
				
				
				//if(sortedCard.getValue().getValue()>)
			}
					
			//sortedCards.sort();
			

		}

	}

	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getCurrTurn().getCode();

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(code, newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(PlayerTable.getPlayer(code));

		if (newSpadeGame.getTempWinner() == null) {
			if (newSpadeGame.getTrickCount() == 4) {

				currPlayer.setWon(true);
			}

			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams().get(
					TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer gameWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {
					if (newSpadeGame.getTrickCount() == 4) {

						currPlayer.setWon(true);
					}
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTrickCount() == 4) {

						gameWinner.setWon(true);
					}
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					if (newSpadeGame.getTrickCount() == 4) {

						currPlayer.setWon(true);
					}
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTrickCount() == 4) {

						gameWinner.setWon(true);
					}

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
