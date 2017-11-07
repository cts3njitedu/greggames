package com.craig.greggames.service.cards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.util.DealUtility;

@Service
public class CardService {

	public void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode() + 1;
		if (dealCode > 4) {
			dealCode = dealCode - 4;
		}

		for (int turn = 1; turn <= 4; turn++) {

			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(dealCode, newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).getRemainingCards()
					.addAll(cards.subList(i, i + 13));
			dealCode++;
			if (dealCode > 4) {
				dealCode = dealCode - 4;
			}
			i = i + 13;
		}

	}
	
	public void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTurn();

		Card oldPlayingCard = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()

				.get(player).getPlayingCard();

		List<Card> cardsLeft = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(player).getRemainingCards();

		cardsLeft.remove(oldPlayingCard);

	}

	public void removePlayingCard(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).setPlayingCard(null);

		}
	}
	
	
	public void sortCards(List<Card>cards) {
		
		
		cards.sort((Card c1, Card c2)->c1.getValue().getValue()-c2.getValue().getValue());
		
	}
}
