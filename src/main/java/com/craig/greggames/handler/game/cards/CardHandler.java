package com.craig.greggames.handler.game.cards;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.DealUtility;;

@Service
public class CardHandler {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private DealUtility dealUtility;

	private static final Logger logger = Logger.getLogger(CardHandler.class);

	public void distributeCards(SpadeGame newSpadeGame) {

		logger.info("Distributing cards...");
		List<Card> cards = dealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode() + 1;
		if (dealCode > MAX_TURN_PER_TRICK) {
			dealCode = dealCode - MAX_TURN_PER_TRICK;
		}

		for (int turn = 1; turn <= MAX_TURN_PER_TRICK; turn++) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams().get(
					spadeTeamHandler.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode));
			spadePlayer.setRemainingCards(null);

			spadePlayer.getRemainingCards().addAll(cards.subList(i, i + MAX_TRICK_COUNT));
			logger.info("Player: " + spadePlayer.getName() + ", Cards: " + spadePlayer.getRemainingCards());
			spadePlayer.setPlayedCards(null);
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

	public Card findSmallestCard(Collection<Card> cardSet) {

		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
		return cardSet.stream().min(new Comparator<Card>() {

			@Override
			public int compare(Card o1, Card o2) {
				// TODO Auto-generated method stub
				return o1.getValue().getValue()-o2.getValue().getValue();
			}
		}).orElse(null);
	}

	public Card findLargestCard(Collection<Card> cardSet) {
		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
		return cardSet.stream().max(new Comparator<Card>() {

			@Override
			public int compare(Card o1, Card o2) {
				// TODO Auto-generated method stub
				return o1.getValue().getValue()-o2.getValue().getValue();
			}
		}).orElse(null);
	}

	public boolean isAllPlayerCardsGreaterThanCard(Collection<Card> cardSet, Card cardToCompare) {

		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));

		for (int i = CardValue.ACE.getValue(); i >= cardToCompare.getValue().getValue(); i--) {
			if (cardMap.get(CardValue.getCardValue(i)) == null) {
				return false;
			}
		}
		return true;
	}

	public Card findCardToMatchSpecifiedCard(Collection<Card> cardSet, Card cardToCompare, boolean isUseLargestCard) {

		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
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

	public Card findLargestCardLessThanCard(Collection<Card> cardSet, Card cardToCompare) {

		if (cardToCompare == null) {
			return null;
		}
		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}

		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));

		int cardToCompareValue = cardToCompare.getValue().getValue();

		for (int i = cardToCompareValue; i > 1; i--) {
			if (cardMap.containsKey(CardValue.getCardValue(i))) {
				return cardMap.get(CardValue.getCardValue(i));
			}
		}

		return null;
	}

	public Card findHighestSmallestCardContinous(Collection<Card> cardSet) {
		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		Card card = null;
		for (int i = CardValue.TWO.getValue(); i <= CardValue.ACE.getValue(); i++) {
			if (cardMap.get(CardValue.getCardValue(i)) != null) {
				card = cardMap.get(CardValue.getCardValue(i));
			} else {
				break;
			}

		}

		return card;

	}

	public boolean isAllCardsGreaterThanCardPlayed(Collection<Card> cardSet, Card cardToCompare) {

		if (cardSet == null || cardSet.size() == 0) {
			return false;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));

		for (int i = cardToCompare.getValue().getValue(); i <= CardValue.ACE.getValue(); i++) {

			if (cardMap.get(CardValue.getCardValue(i)) == null) {
				return false;
			}
		}

		return true;
	}

	public Card findSmallestLargestCardContinous(Collection<Card> cardSet) {
		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		Card card = null;
		for (int i = CardValue.ACE.getValue(); i >= CardValue.TWO.getValue(); i--) {
			if (cardMap.get(CardValue.getCardValue(i)) != null) {
				card = cardMap.get(CardValue.getCardValue(i));
			} else {
				break;
			}

		}

		return card;

	}

	public List<Card> filterOutSpades(Collection<Card> cardSet) {
		if (cardSet == null || cardSet.size() == 0) {
			return null;
		}
		List<Card> cards = new ArrayList<>();
		for (Card card : cardSet) {
			if (card == null) {
				continue;
			}
			if (CardSuit.SPADES != card.getSuit()) {
				cards.add(card);
			}
		}
		return cards;
	}

	public Card findSmallestCardLargerThanCard(Set<Card> cardSet, Card cardToCompare) {
		if (cardToCompare == null) {
			return null;
		}

		List<Card> cards = cardSet.stream().collect(Collectors.toList());
		cards.sort((Card c1, Card c2) -> c1.getValue().getValue() - c2.getValue().getValue());

		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));

		int cardToCompareValue = cardToCompare.getValue().getValue();

		for (int i = cardToCompareValue; i <= CardValue.ACE.getValue(); i++) {
			if (cardMap.containsKey(CardValue.getCardValue(i))) {
				return cardMap.get(CardValue.getCardValue(i));
			}
		}
		return null;
	}

	public void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTurn();

		Card oldPlayingCard = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers()

				.get(player).getPlayingCard();

		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(player);
		Set<Card> cardsLeft = spadePlayer.getRemainingCards().stream().collect(Collectors.toSet());
		Set<Card> cardsPlayed = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player)
				.getPlayedCards();
		cardsLeft.remove(oldPlayingCard);
		cardsPlayed.add(oldPlayingCard);
		spadePlayer.setRemainingCards(cardsLeft.stream().collect(Collectors.toList()));

	}

	public void removePlayingCard(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			newSpadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).setPlayingCard(null);

		}
	}

	public void sortAllPlayerCards(SpadeGame spadeGame) {
		for (Entry<TeamTable, SpadeTeam> teams : spadeGame.getTeams().entrySet()) {
			for (Entry<PlayerTable, SpadePlayer> players : teams.getValue().getPlayers().entrySet()) {
				List<Card> remainingCards = players.getValue().getRemainingCards().stream()
						.collect(Collectors.toList());

				players.getValue().setRemainingCards(sortCardsBySuitList(remainingCards));
			}
		}
	}

	public List<Card> sortCardsBySuitList(List<Card> cards) {

		Map<CardSuit, Set<Card>> cardMap = distributeCardsAccordingToSuit(cards.stream().collect(Collectors.toSet()));

		List<Card> sortedCards = new ArrayList<>();

		// clubs
		List<Card> listCard = cardMap.get(CardSuit.DIAMONDS).stream().collect(Collectors.toList());
		sortedCards.addAll(sortCards(listCard));

		listCard = cardMap.get(CardSuit.CLUBS).stream().collect(Collectors.toList());
		sortedCards.addAll(sortCards(listCard));

		listCard = cardMap.get(CardSuit.HEARTS).stream().collect(Collectors.toList());
		sortedCards.addAll(sortCards(listCard));

		listCard = cardMap.get(CardSuit.SPADES).stream().collect(Collectors.toList());
		sortedCards.addAll(sortCards(listCard));

		return sortedCards;
	}

	public List<Card> sortCards(Collection<Card> cardSet) {

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

	public Set<Card> sortCardsBySuit(List<Card> cards) {
		Set<Card> cardSet = cards.stream().collect(Collectors.toSet());
		Map<CardSuit, Set<Card>> distributeCards = distributeCardsAccordingToSuit(cardSet);
		Set<Card> newCardSet = new LinkedHashSet<Card>();
		for (Entry<CardSuit, Set<Card>> entry : distributeCards.entrySet()) {

			newCardSet.addAll(sortCards(entry.getValue()));
		}
		return newCardSet;
	}

	public Map<CardSuit, Set<Card>> distributeCardsAccordingToSuit(Set<Card> cards) {

		Map<CardSuit, Set<Card>> cardsForEachSuit = new EnumMap<>(CardSuit.class);
		for (CardSuit suit : CardSuit.values()) {

			Set<Card> cardSet = cardsToMatchSuit(suit, cards);
			if (cardSet != null) {
				cardsForEachSuit.put(suit, cardSet);
			}

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
			return cards.stream().filter(c -> c.getSuit() == CardSuit.CLUBS).collect(Collectors.toSet());

		default:
			break;
		}
		return null;
	}

	public SpadeTeam findOtherTeam(Map<TeamTable, SpadeTeam> teams, SpadeTeam currTeam) {

		for (Entry<TeamTable, SpadeTeam> team : teams.entrySet()) {

			if (team.getKey() != currTeam.getName()) {
				return team.getValue();
			}

		}
		return null;
	}

	public SpadeGameMetaData getSpadeGameMetaData(SpadeGame newSpadeGame) {

		logger.info("Getting spade game meta data...");
		
		SpadeGameMetaData spadeGameMetaData = new SpadeGameMetaData();
		
		settingTeamsAndPlayers(newSpadeGame, spadeGameMetaData);
	
		Map<CardSuit, Set<Card>> currPlayerRemainingCards = spadeGameMetaData.getCurrPlayer().getRemainingCards().stream()
				.collect(Collectors.groupingBy(Card::getSuit, Collectors.toSet()));

		Set<Card> allCardsPlayed = new HashSet<>();

		for (Entry<PlayerTable, SpadePlayer> player : spadeGameMetaData.getCurrPlayerTeam().getPlayers().entrySet()) {
			allCardsPlayed.addAll(player.getValue().getPlayedCards());
		}

		for (Entry<PlayerTable, SpadePlayer> player : spadeGameMetaData.getOtherTeam().getPlayers().entrySet()) {
			allCardsPlayed.addAll(player.getValue().getPlayedCards());
		}

		Map<CardSuit, Set<Card>> allPlayedCardsMap =  allCardsPlayed.stream()
				.collect(Collectors.groupingBy(Card::getSuit, Collectors.toSet()));
		
		spadeGameMetaData.setAllPlayedCards(allPlayedCardsMap);
		spadeGameMetaData.setCurrPlayerRemainingCards(currPlayerRemainingCards);
		spadeGameMetaData.setCurrPlayerPlayedCards(spadeGameMetaData.getCurrPlayer().getPlayedCards().stream()
				.collect(Collectors.groupingBy(Card::getSuit, Collectors.toSet())));
		spadeGameMetaData.setTeamMatePlayedCards(spadeGameMetaData.getCurrPlayerTeamMate().getPlayedCards().stream()
				.collect(Collectors.groupingBy(Card::getSuit, Collectors.toSet())));

		Map<CardSuit, Card> lowestCardsPlayed = new HashMap<>();

		spadeGameMetaData.getAllPlayedCards().forEach((suit, cards) -> {
			lowestCardsPlayed.put(suit, findSmallestCard(cards));
		});

		Map<CardSuit, Card> highestCardsPlayed = new HashMap<>();

		spadeGameMetaData.getAllPlayedCards().forEach((suit, cards) -> {
			highestCardsPlayed.put(suit, findLargestCard(cards));
		});

		Map<CardSuit, Card> lowestRemainingCards = new HashMap<>();

		spadeGameMetaData.getCurrPlayerRemainingCards().forEach((suit, cards) -> {
			lowestRemainingCards.put(suit, findSmallestCard(cards));
		});

		Map<CardSuit, Card> highestRemainingCards = new HashMap<>();

		spadeGameMetaData.getCurrPlayerRemainingCards().forEach((suit, cards) -> {
			highestRemainingCards.put(suit, findLargestCard(cards));
		});

		spadeGameMetaData.setHighestCardPlayed(highestCardsPlayed);
		spadeGameMetaData.setHighestRemainingCard(highestRemainingCards);
		spadeGameMetaData.setLowestCardPlayed(lowestCardsPlayed);
		spadeGameMetaData.setLowestRemainingCard(lowestRemainingCards);
		
		Set<Card> spades = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES);
		Set<Card> clubs = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.CLUBS);
		Set<Card> hearts = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.HEARTS);
		Set<Card> diamonds = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.DIAMONDS);
		
		int spadesSize = (spades==null)?0:spades.size();
		int heartsSize = (hearts==null)?0:hearts.size();
		int diamondsSize = (diamonds==null)?0:diamonds.size();
		int clubsSize = (clubs==null)?0:clubs.size();
		boolean hasOnlySpades = (heartsSize==0) && (clubsSize==0) && (diamondsSize==0);
		boolean hasLeadPlayerSuit = false;
		boolean hasSpades = (spadesSize!=0);
		boolean canPlaySpades = false;
		if(newSpadeGame.getStartTurn()!=newSpadeGame.getCurrTurn()) {
			hasLeadPlayerSuit = spadeGameMetaData.getCurrPlayerRemainingCards().containsKey(spadeGameMetaData.getLeadPlayer().getPlayingCard().getSuit());
			if(!hasLeadPlayerSuit) {
				canPlaySpades = hasSpades;
			}
			else {
				if(spadeGameMetaData.getLeadPlayer().getPlayingCard().getSuit()==CardSuit.SPADES) {
					canPlaySpades = true;
				}
			}
		}
		else {
			canPlaySpades = (hasOnlySpades) || newSpadeGame.isSpadePlayed();
		}
		
		spadeGameMetaData.setHasOnlySpades(hasOnlySpades);
		spadeGameMetaData.setHasSpades(hasSpades);
		spadeGameMetaData.setCanPlaySpades(canPlaySpades);
		spadeGameMetaData.setHasLeadPlayerSuit(hasLeadPlayerSuit);
		return spadeGameMetaData;
	}

	private void settingTeamsAndPlayers(SpadeGame newSpadeGame, SpadeGameMetaData spadeGameMetaData) {
		Map<TeamTable, SpadeTeam> teams = newSpadeGame.getTeams();
		SpadeTeam leadingSpadeTeam = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer leadPlayer = teams
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getStartTurn());

		SpadeTeam currTeam = teams
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer currPlayer = currTeam.getPlayers().get(newSpadeGame.getCurrTurn());

		Map<PlayerTable, SpadePlayer> playersOnTeam = teams.get(currPlayer.getTeam()).getPlayers();
		SpadeTeam otherTeam = findOtherTeam(teams, currTeam);

		SpadePlayer teamMate = findTeamMate(playersOnTeam, currPlayer);
		
		int playerTrickPosition = spadeTeamHandler.getTrickPlayerPosition(leadPlayer.getName(), currPlayer.getName());
		int afterCode = currPlayer.getName().getCode() + 1;
		if(afterCode>MAX_TURN_PER_TRICK) {
			afterCode = afterCode - MAX_TURN_PER_TRICK;
		}
		
		int beforeCode = currPlayer.getName().getCode() - 1;
		if(beforeCode<1) {
			beforeCode = MAX_TURN_PER_TRICK;
		}
		
		SpadePlayer nextPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(afterCode));
		SpadePlayer previousPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(beforeCode));
		
		
		if(playerTrickPosition>=1 && playerTrickPosition<MAX_TURN_PER_TRICK) {
			spadeGameMetaData.setOtherPlayerAfter(nextPlayer);
		}
		if(playerTrickPosition>1 && playerTrickPosition<=MAX_TURN_PER_TRICK) {
			spadeGameMetaData.setOtherPlayerBefore(previousPlayer);
		}
		spadeGameMetaData.setLeadPlayer(leadPlayer);
		spadeGameMetaData.setCurrPlayer(currPlayer);
		spadeGameMetaData.setCurrPlayerTeamMate(teamMate);
		spadeGameMetaData.setLeadingSpadeTeam(leadingSpadeTeam);
		spadeGameMetaData.setCurrPlayerTeam(currTeam);
		spadeGameMetaData.setOtherTeam(otherTeam);
		spadeGameMetaData.setTrickPlayerPosition(playerTrickPosition);
		
		if(newSpadeGame.getCurrTurn()!=newSpadeGame.getStartTurn()) {
			SpadePlayer currWinner = newSpadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(newSpadeGame.getTempWinner());
			spadeGameMetaData.setCurrWinner(currWinner);
		}
		
		
		
	
	}
}
