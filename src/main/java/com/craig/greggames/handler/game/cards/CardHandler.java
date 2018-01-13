package com.craig.greggames.handler.game.cards;

import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.util.DealUtility;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;;
@Service
public class CardHandler {

	@Autowired
	private SpadeTeamHandler teamService;
	
	@Autowired
	private DealUtility dealUtility;

	public void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = dealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode() + 1;
		if (dealCode > MAX_TURN_PER_TRICK) {
			dealCode = dealCode - MAX_TURN_PER_TRICK;
		}

		for (int turn = 1; turn <= MAX_TURN_PER_TRICK; turn++) {

			newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).setRemainingCards(null);

			newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).getRemainingCards()
					.addAll(cards.subList(i, i + MAX_TRICK_COUNT));
			dealCode++;
			if (dealCode > MAX_TURN_PER_TRICK) {
				dealCode = dealCode - MAX_TURN_PER_TRICK;
			}
			i = i + MAX_TRICK_COUNT;
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
	
	public Card findLargestCard(Set<Card> cards) {

		Card maxCardValue = null;
		for (Card card : cards) {

			if (maxCardValue == null) {
				maxCardValue = card;
			} else {
				if (card.getValue().getValue() > maxCardValue.getValue().getValue()) {

					maxCardValue = card;
				}
			}
		}

		return maxCardValue;
	}

	
	public Card findCardToMatchSpecifiedCard(Set<Card> cardSet, Card cardToCompare, boolean isUseLargestCard) {

		Card maxCardValue = null;
		
		List<Card> cards = cardSet.stream().collect(Collectors.toList());
		cards.sort((Card c1, Card c2) -> c1.getValue().getValue() - c2.getValue().getValue());
		for (Card card : cards) {

			if (card.getValue().getValue() > cardToCompare.getValue().getValue()) {

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

	public List<Card> sortCards(Set<Card> cardSet) {

		List<Card> cards = cardSet.stream().collect(Collectors.toList());
		cards.sort((Card c1, Card c2) -> c1.getValue().getValue() - c2.getValue().getValue());
		

		return cards;

	}
	
	public SpadePlayer findTeamMate(Map<PlayerTable, SpadePlayer> playersOnTeam, SpadePlayer currPlayer) {
		
	
		for (Entry<PlayerTable, SpadePlayer> entry : playersOnTeam.entrySet()) {

			if (entry.getKey() != currPlayer.getName()) {

				return entry.getValue();
			}
		}
		
		return null;
	}
	
	public Set<Card> sortCardsBySuit(List<Card>cards) {
		Set<Card>cardSet = cards.stream().collect(Collectors.toSet());
		Map<CardSuit, Set<Card>> distributeCards = distributeCardsAccordingToSuit(cardSet);
		Set<Card>newCardSet = new LinkedHashSet<Card>();
		for(Entry<CardSuit,Set<Card>> entry: distributeCards.entrySet()) {
			
			newCardSet.addAll(sortCards(entry.getValue()));
		}
		return newCardSet;
	}
	public Map<CardSuit,Set<Card>> distributeCardsAccordingToSuit(Set<Card>cards){
		
		Map<CardSuit, Set<Card>> cardsForEachSuit = new EnumMap<>(CardSuit.class);
		for (CardSuit suit : CardSuit.values()) {

			cardsForEachSuit.put(suit, cardsToMatchSuit(suit, cards));
		}
		return cardsForEachSuit;
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
			System.out.println(cards.toString());
			return cards.stream().filter(c -> c.getSuit() == CardSuit.CLUBS).collect(Collectors.toSet());

		default:
			break;
		}
		return null;
	}

}
