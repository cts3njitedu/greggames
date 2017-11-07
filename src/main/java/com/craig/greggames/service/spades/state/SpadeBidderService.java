package com.craig.greggames.service.spades.state;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;

@Service
public class SpadeBidderService {

	public void determineBid(SpadeGame newSpadeGame) {

		SpadeTeam team = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(newSpadeGame.getCurrTurn().getCode(), newSpadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(newSpadeGame.getCurrTurn());
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;

		int totalBid = 0;
		if (player.isBot()) {

			List<Card> cards = player.getRemainingCards();
			for (Card card : cards) {

				switch (card.getSuit()) {

				case HEARTS:
					if (card.getValue().getValue() >= 13) {
						hearts++;
					}
					break;

				case SPADES:

					if (card.getValue().getValue() >= 9) {
						spades++;
					}

					break;
				case DIAMONDS:
					if (card.getValue().getValue() >= 13) {
						diamonds++;
					}
					break;
				case CLUBS:
					if (card.getValue().getValue() >= 13) {
						clubs++;
					}
					break;
				default:
					break;
				}

			}
			totalBid = 10 * (hearts + spades + diamonds + clubs);
			team.getPlayers().get(newSpadeGame.getCurrTurn()).setPlayerBid(totalBid);

		} else {

			totalBid = player.getPlayerBid();
		}
		team.setTotalBid(team.getTotalBid() + totalBid);

	}

	public void cleanUpBid(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, SpadeTeam> team : newSpadeGame.getTeams().entrySet()) {

			team.getValue().setTotalBid(0);
		}
	}
}
