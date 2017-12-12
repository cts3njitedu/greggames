package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
public class SpadeBotHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadeValidationHandler validationService;

	@Autowired
	private CardHandler cardService;

	public int getBotBid(SpadePlayer player) {
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;

		Set<Card> cards = player.getRemainingCards();
		for (Card card : cards) {

			switch (card.getSuit()) {

			case HEARTS:
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					hearts++;
				}
				break;

			case SPADES:

				if (card.getValue().getValue() >= CardValue.NINE.getValue()) {
					spades++;
				}

				break;
			case DIAMONDS:
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					diamonds++;
				}
				break;
			case CLUBS:
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					clubs++;
				}
				break;
			default:
				break;
			}

		}
		return POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs);

	}

	public Card getBotCard(SpadeGame newSpadeGame) {

		SpadePlayer leadPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getStartTurn());

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getCurrTurn());

		SpadePlayer currWinner = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		Map<PlayerTable, SpadePlayer> playersOnTeam = newSpadeGame.getTeams().get(currPlayer.getTeam()).getPlayers();

		Set<Card> currPlayerCards = currPlayer.getRemainingCards();

		currPlayerCards = cardService.sortCards(currPlayerCards);

		Map<CardSuit, Set<Card>> distributedCardsBySuit = cardService.distributeCardsAccordingToSuit(currPlayerCards);
		if (currPlayer.getName() == newSpadeGame.getStartTurn()) {

			return retrieveCardWhenLeadPlayer(distributedCardsBySuit, newSpadeGame.isSpadePlayed());
		}
		SpadePlayer teamPlayer = cardService.findTeamMate(playersOnTeam, currPlayer);
		Card leadPlayerCard = leadPlayer.getPlayingCard();

		Set<Card> sameSuitCards = distributedCardsBySuit.get(leadPlayerCard.getSuit());
		if (sameSuitCards.size() > 0) {
			return retrieveCardSameWhenSameSuit(sameSuitCards, teamPlayer, leadPlayer, currWinner, currPlayer,
					newSpadeGame.isSpadePlayed());
		} else {

			distributedCardsBySuit.remove(leadPlayerCard.getSuit());

		}
		return null;

	}

	public Card retrieveCardWhenLeadPlayer(Map<CardSuit, Set<Card>> distributedCards, boolean isSpadePlayed) {

		if (isSpadePlayed) {

			return cardService.findLargestCard(distributedCards.get(CardSuit.SPADES));
		} else {

			Set<Card> hearts = distributedCards.get(CardSuit.HEARTS);
			Set<Card> diamonds = distributedCards.get(CardSuit.DIAMONDS);
			Set<Card> clubs = distributedCards.get(CardSuit.CLUBS);

			Card heartCard = cardService.findLargestCard(hearts);
			Card diamondCard = cardService.findLargestCard(diamonds);
			Card clubCard = cardService.findLargestCard(clubs);
			Set<Card> suitCards = new HashSet<Card>();
			suitCards.add(diamondCard);
			suitCards.add(heartCard);
			suitCards.add(clubCard);
			return cardService.findLargestCard(suitCards);
		}

	}

	public Card retrieveCardSameWhenSameSuit(Set<Card> sameSuitCards, SpadePlayer teamPlayer, SpadePlayer leadPlayer,
			SpadePlayer currWinner, SpadePlayer currPlayer, boolean isSpadePlayed) {
		Card playingCard = null;
		if (teamPlayer.getName() == currWinner.getName()) {
			if (teamPlayer.isBidNil()) {

				if (teamPlayer.getPlayingCard().getSuit() == leadPlayer.getPlayingCard().getSuit()) {

					return retrieveCardWhenTeamMateBidsNil(sameSuitCards, teamPlayer.getPlayingCard(), false);
				}
				return cardService.findSmallestCard(sameSuitCards);
			} else {

				if (teamPlayer.getPlayingCard().getValue().getValue() >= CardValue.QUEEN.getValue()) {
					return cardService.findSmallestCard(sameSuitCards);
				} else {
					return cardService.findLargestCard(sameSuitCards);
				}
			}

		} else {
			if (currWinner.getPlayingCard().getValue().getValue() >= CardValue.QUEEN.getValue()) {

				return cardService.findSmallestCard(sameSuitCards);
			} else {

				if (teamPlayer.isBidNil()) {
					if (currWinner.getName() == leadPlayer.getName()) {

						return retrieveCardWhenTeamMateBidsNil(sameSuitCards, currWinner.getPlayingCard(), true);
					} else {

						return cardService.findSmallestCard(sameSuitCards);
					}

				} else {

					playingCard = cardService.findLargestCard(sameSuitCards);
					if (playingCard.getValue().getValue() >= CardValue.KING.getValue()) {

						return playingCard;
					} else {
						return cardService.findSmallestCard(sameSuitCards);
					}
				}
			}

		}

	}

	public Card retrieveCardWhenNotSameSuit(Map<CardSuit, Set<Card>> distributedCards, SpadePlayer teamPlayer,
			SpadePlayer leadPlayer, SpadePlayer currWinner, SpadePlayer currPlayer, boolean isSpadePlayed) {

		if (teamPlayer.getName() == currWinner.getName()) {

			if (teamPlayer.isBidNil()) {

				return retrieveCardWhenTeamMateBidsNil(distributedCards.get(teamPlayer.getPlayingCard().getSuit()),
						teamPlayer.getPlayingCard(), false);
			} else {

				if (teamPlayer.getPlayingCard().getValue().getValue() >= CardValue.QUEEN.getValue()) {
					return cardService.findSmallestCard(distributedCards.get(teamPlayer.getPlayingCard().getSuit()));
				} else {
					return cardService.findLargestCard(distributedCards.get(teamPlayer.getPlayingCard().getSuit()));
				}
			}

		}
	

		return null;
	}

	public Card retrieveCardWhenTeamMateBidsNil(Set<Card> cards, Card cardToCompare, boolean isUseLargestCard) {

		Card playingCard = null;
		playingCard = cardService.findCardToMatchSpecifiedCard(cards, cardToCompare, isUseLargestCard);

		if (playingCard == null) {
			return cardService.findSmallestCard(cards);
		}
		return playingCard;
	}
}
