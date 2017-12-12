package com.craig.greggames.handler.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
public class SpadeValidationHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	public boolean validatePlayerCard(SpadeGame spadeGame) {

		SpadePlayer player = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers()
				.get(spadeGame.getCurrTurn());

		SpadePlayer leadPlayer = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getStartTurn(), spadeGame.getNumberOfTeams())).getPlayers()
				.get(spadeGame.getStartTurn());

		Card card = player.getPlayingCard();
		Card startCard = leadPlayer.getPlayingCard();

		Set<Card> cards = player.getRemainingCards();

		Map<CardSuit, Set<Card>> cardsForEachSuit = new EnumMap<>(CardSuit.class);
		for (CardSuit suit : CardSuit.values()) {

			cardsForEachSuit.put(suit, cardsToMatchSuit(suit, cards));
		}
		Set<Card> cardsToMatch = cardsForEachSuit.get(startCard.getSuit());

		if (cardsToMatch.size() > 0) {

			if (card.getSuit() != startCard.getSuit()) {

				return false;
			}

		} else {
			if (card.getSuit() == CardSuit.SPADES) {

				boolean playSpade = isSpadePlayable(cardsForEachSuit, startCard, card,spadeGame.isSpadePlayed());
				
				spadeGame.setSpadePlayed(playSpade);
				
				return playSpade;
			

			}

		}

		return true;

	}

	public boolean isSpadePlayable(Map<CardSuit, Set<Card>> cardsForEachSuit, Card startCard, Card card,
			boolean isSpadePlayed) {

		if (startCard != card) {
			return true;
		} else {
			if (!isSpadePlayed) {

				if (cardsForEachSuit.get(CardSuit.CLUBS).size() == 0
						&& cardsForEachSuit.get(CardSuit.HEARTS).size() == 0
						&& cardsForEachSuit.get(CardSuit.DIAMONDS).size() == 0) {

					return true;

				} else {
					return false;
				}

			}

		}
		return true;
	}

	public Set<Card> cardsToMatchSuit(CardSuit cardSuit, Set<Card> cards) {

		switch (cardSuit) {

		case SPADES:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.SPADES).collect(Collectors.toSet());

		case HEARTS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.HEARTS).collect(Collectors.toSet());

		case DIAMONDS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.DIAMONDS).collect(Collectors.toSet());

		case CLUBS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.CLUBS).collect(Collectors.toSet());

		default:
			break;
		}
		return null;
	}
}
