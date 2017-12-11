package com.craig.greggames.handler.game.cards;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.util.DealUtility;

@Service
public class CardHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	public void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode() + 1;
		if (dealCode > 4) {
			dealCode = dealCode - 4;
		}

		for (int turn = 1; turn <= 4; turn++) {

			newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).setRemainingCards(null);

			newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).getRemainingCards()
					.addAll(cards.subList(i, i + 13));
			dealCode++;
			if (dealCode > 4) {
				dealCode = dealCode - 4;
			}
			i = i + 13;
		}

	}

	public Card smallestCard(Card a, Card b) {
		if (a == null) {
			return b;
		}
		if (b == null) {

			return a;
		}
		return (a.getValue().getValue() <= b.getValue().getValue()) ? a : b;
	}

	public Card findSmallestCard(Set<Card> cards) {

		Card minCardValue = null;
		for (Card card : cards) {

			if (minCardValue == null) {
				minCardValue = card;
			} else {
				if (card.getValue().getValue() < minCardValue.getValue().getValue()) {

					minCardValue = card;
				}
			}
		}

		return minCardValue;
	}

	// when winnerCard is null find the smallest card
	public Card findCardToBeatWinnerCard(Set<Card> cards, Card winnerCard, boolean isUseLargestCard) {

		Card maxCardValue = null;
		for (Card card : cards) {

			if (card.getValue().getValue() > winnerCard.getValue().getValue()) {

				if (!isUseLargestCard) {

					return card;
				}
				if (maxCardValue == null) {
					maxCardValue = card;
				} else {

					if (maxCardValue.getValue().getValue() < card.getValue().getValue()) {

						maxCardValue = card;
					}
				}
			}

		}

		return maxCardValue;

	}

	public void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTurn();

		Card oldPlayingCard = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers()

				.get(player).getPlayingCard();

		Set<Card> cardsLeft = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player)
				.getRemainingCards();

		cardsLeft.remove(oldPlayingCard);

	}

	public void removePlayingCard(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			newSpadeGame.getTeams().get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).setPlayingCard(null);

		}
	}

	public Set<Card> sortCards(Set<Card> cards) {

		return cards.stream().sorted((Card c1, Card c2) -> c1.getValue().getValue() - c2.getValue().getValue())
				.collect(Collectors.toSet());

	}

}
