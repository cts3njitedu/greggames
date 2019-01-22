package com.craig.greggames.handler.game.cards;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(PlayerTable.getPlayer(dealCode), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode));
			spadePlayer.setRemainingCards(null);

			spadePlayer.getRemainingCards()
					.addAll(cards.subList(i, i + MAX_TRICK_COUNT));
			
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

		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		Card minCardValue = null;
		for (Card card : cardSet) {

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
	
	public Card findLargestCard(Collection<Card> cardSet) {
		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		Card maxCardValue = null;
		for (Card card : cardSet) {

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

	public boolean isAllPlayerCardsGreaterThanCard(Collection<Card>cardSet, Card cardToCompare) {
		
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		
		for(int i=CardValue.ACE.getValue(); i>=cardToCompare.getValue().getValue(); i--) {
			if(cardMap.get(CardValue.getCardValue(i))==null) {
				return false;
			}
		}
		return true;
	}
	public Card findCardToMatchSpecifiedCard(Collection<Card> cardSet, Card cardToCompare, boolean isUseLargestCard) {

		if(cardSet==null ||cardSet.size()==0) {
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
	
	public Card findLargestCardLessThanCard(Collection<Card>cardSet, Card cardToCompare) {
		
		if(cardToCompare==null) {
			return null;
		}
		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		
		
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		
		int cardToCompareValue = cardToCompare.getValue().getValue();
		
		for(int i= cardToCompareValue; i>1; i--) {
			if(cardMap.containsKey(CardValue.getCardValue(i))){
				return cardMap.get(CardValue.getCardValue(i));
			}
		}
		
		
		
		return null;
	}
	
	
	public Card findHighestSmallestCardContinous(Collection<Card> cardSet) {
		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		Card card = null;
		for(int i = CardValue.TWO.getValue(); i<=CardValue.ACE.getValue(); i++) {
			if(cardMap.get(CardValue.getCardValue(i))!=null) {
				card = cardMap.get(CardValue.getCardValue(i));
			}
			else {
				break;
			}
			
		}
		
		return card;
		
	}
	
	public boolean isAllCardsGreaterThanCardPlayed(Collection<Card>cardSet, Card cardToCompare) {
		
		if(cardSet==null ||cardSet.size()==0) {
			return false;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		
		
		
		for(int i = cardToCompare.getValue().getValue(); i<=CardValue.ACE.getValue(); i++) {
			
			if(cardMap.get(CardValue.getCardValue(i))==null) {
				return false;
			}
		}
		
		return true;
	}
	
	public Card findSmallestLargestCardContinous(Collection<Card> cardSet) {
		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		Card card = null;
		for(int i = CardValue.ACE.getValue(); i>=CardValue.TWO.getValue(); i--) {
			if(cardMap.get(CardValue.getCardValue(i))!=null) {
				card = cardMap.get(CardValue.getCardValue(i));
			}
			else {
				break;
			}
			
		}
		
		return card;
		
	}

	public List<Card> filterOutSpades(Collection<Card>cardSet){
		if(cardSet==null ||cardSet.size()==0) {
			return null;
		}
		List<Card> cards = new ArrayList<>();
		for(Card card: cardSet) {
			if(card==null) {
				continue;
			}
			if(CardSuit.SPADES!=card.getSuit()) {
				cards.add(card);
			}
		}
		return cards;
	}
	public Card findSmallestCardLargerThanCard(Set<Card> cardSet, Card cardToCompare) {
		if(cardToCompare==null) {
			return null;
		}
		
		List<Card> cards = cardSet.stream().collect(Collectors.toList());
		cards.sort((Card c1, Card c2) -> c1.getValue().getValue() - c2.getValue().getValue());
		
		Map<CardValue, Card> cardMap = cardSet.stream().collect(Collectors.toMap(Card::getValue, Function.identity()));
		
		int cardToCompareValue = cardToCompare.getValue().getValue();
		
		for(int i=cardToCompareValue; i<=CardValue.ACE.getValue(); i++) {
			if(cardMap.containsKey(CardValue.getCardValue(i))){
				return cardMap.get(CardValue.getCardValue(i));
			}
		}
		return null;
	}
	public void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTurn();

		Card oldPlayingCard = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers()

				.get(player).getPlayingCard();

		Set<Card> cardsLeft = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player)
				.getRemainingCards();

		Set<Card> cardsPlayed = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player)
				.getPlayedCards();
		cardsLeft.remove(oldPlayingCard);
		cardsPlayed.add(oldPlayingCard);

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

			Set<Card> cardSet = cardsToMatchSuit(suit, cards);
			if(cardSet!=null) {
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
	
	public SpadeTeam findOtherTeam(Map<TeamTable,SpadeTeam> teams, SpadeTeam currTeam) {
		
		for(Entry<TeamTable,SpadeTeam> team: teams.entrySet()) {
			
			if(team.getKey()!=currTeam.getName()) {
				return team.getValue();
			}
			
		}
		return null;
	}
	public SpadeGameMetaData getSpadeGameMetaData(SpadeGame newSpadeGame) {
		
		Map<TeamTable, SpadeTeam> teams = newSpadeGame.getTeams();
		SpadePlayer leadPlayer = teams
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getStartTurn());

		SpadeTeam currTeam = teams
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer currPlayer = currTeam.getPlayers().get(newSpadeGame.getCurrTurn());
		
		
		
		Map<PlayerTable, SpadePlayer> playersOnTeam = teams.get(currPlayer.getTeam()).getPlayers();
		SpadeTeam otherTeam =  findOtherTeam(teams, currTeam);
		Map<PlayerTable,SpadePlayer> playersOnOtherTeam = otherTeam.getPlayers();
		
		SpadePlayer teamMate = findTeamMate(playersOnTeam, currPlayer);
		
		Map<CardSuit, Set<Card>> currPlayerRemainingCards = distributeCardsAccordingToSuit(currPlayer.getRemainingCards());
		
		Set<Card> allCardsPlayed = new HashSet<>();
		
		for(Entry<PlayerTable, SpadePlayer>player: playersOnTeam.entrySet()) {
			allCardsPlayed.addAll(player.getValue().getPlayedCards());
		}
		
		for(Entry<PlayerTable, SpadePlayer>player: playersOnOtherTeam.entrySet()) {
			allCardsPlayed.addAll(player.getValue().getPlayedCards());
		}
		
		Map<CardSuit, Set<Card>> allPlayedCardsMap = distributeCardsAccordingToSuit(allCardsPlayed);
		SpadeGameMetaData spadeGameMetaData = new SpadeGameMetaData();
		spadeGameMetaData.setAllPlayedCards(allPlayedCardsMap);
		spadeGameMetaData.setCurrPlayerRemainingCards(currPlayerRemainingCards);
		spadeGameMetaData.setCurrPlayerPlayedCards(distributeCardsAccordingToSuit(currPlayer.getPlayedCards()));
		spadeGameMetaData.setTeamMatePlayedCards(distributeCardsAccordingToSuit(teamMate.getPlayedCards()));
		return spadeGameMetaData;
	}

}
