package com.craig.greggames.service.spades.state;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;
import com.craig.greggames.model.team.TeamTable;

@Service
public class SpadeBidderService {

	@Autowired
	private SpadeTeamService teamService;
	public void determineBid(SpadeGame newSpadeGame) {

		SpadeTeam team = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));

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
			for(Entry<PlayerTable,SpadePlayer> player: team.getValue().getPlayers().entrySet()) {
				
				player.getValue().setPlayerBid(0);
			}
		}
	}
}
