package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.HierarchicalThemeSource;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;

@Service
public class SpadeBotHandler {

	@Autowired
	private SpadeTeamHandler teamService;

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
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					hearts++;
				}
				break;

			case SPADES:

				if (card.getValue().getValue() >= CardValue.NINE.getValue()) {
					spades++;
				}

				break;
			case DIAMONDS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					diamonds++;
				}
				break;
			case CLUBS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					clubs++;
				}
				break;
			default:
				break;
			}

		}
		return POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs);

	}

	public void determineBotCard(SpadeGame spadeGame) {
		
		SpadePlayer player = spadeGame.getTeams().get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers().get(spadeGame.getCurrTurn());
		if(player.isBot()) {
			player.setPlayingCard(getBotCard(spadeGame));
		}
		
	}
	private Card getBotCard(SpadeGame newSpadeGame) {

		SpadeTeam leadingSpadeTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()));
		SpadeTeam currPlayerTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer leadPlayer = leadingSpadeTeam.getPlayers().get(newSpadeGame.getStartTurn());

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(newSpadeGame.getCurrTurn());

		SpadeGameMetaData spadeGameMetaData = cardService.getSpadeGameMetaData(newSpadeGame);

		int playerTrickPosition = teamService.getTrickPlayerPosition(leadPlayer.getName(), currPlayer.getName());

		if (playerTrickPosition == 1) {

			return firstPlayerPlaying(spadeGameMetaData, newSpadeGame);
		}

		else if (playerTrickPosition == 2) {
			return secondPlayerPlaying(spadeGameMetaData, newSpadeGame);
		}
		return null;

	}

	private Card firstPlayerPlaying(SpadeGameMetaData spadeGameMetaData, SpadeGame newSpadeGame) {

		SpadeTeam leadingSpadeTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()));
		SpadeTeam currPlayerTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(newSpadeGame.getCurrTurn());

		SpadePlayer currPlayerTeamMate = cardService.findTeamMate(currPlayerTeam.getPlayers(), currPlayer);

		Set<Card> spades = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES);
		Set<Card> clubs = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.CLUBS);
		Set<Card> hearts = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.HEARTS);
		Set<Card> diamonds = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.DIAMONDS);

		Map<CardSuit, Card> lowestCardsPlayed = new HashMap<>();
		for (CardSuit suit :spadeGameMetaData.getAllPlayedCards().keySet() ) {
			lowestCardsPlayed.put(suit,
					cardService.findHighestSmallestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}

		lowestCardsPlayed.remove(null);
		Map<CardSuit, Card> highestCardsPlayed = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getAllPlayedCards().keySet()) {
			highestCardsPlayed.put(suit,
					cardService.findSmallestLargestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}

		highestCardsPlayed.remove(null);
		Map<CardSuit, Card> lowestRemainingCards = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getCurrPlayerRemainingCards().keySet()) {
			lowestRemainingCards.put(suit,
					cardService.findSmallestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}
		lowestRemainingCards.remove(null);
		Map<CardSuit, Card> highestRemainingCards = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getCurrPlayerRemainingCards().keySet()) {
			highestRemainingCards.put(suit,
					cardService.findLargestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}
		highestCardsPlayed.remove(null);

		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || newSpadeGame.isSpadePlayed();
		boolean hasOnlySpades = (hearts.size()==0&&clubs.size()==0&&diamonds.size()==0);
	
		if (currPlayer.isBidNil()) {

			Map<CardSuit, Card> highestLowestCards = new HashMap<>();

			for (CardSuit suit : CardSuit.values()) {
				if (CardSuit.SPADES == suit) {
					if (canPlaySpades) {
						if (lowestCardsPlayed.get(suit) != null) {
							highestLowestCards.put(suit,
									cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit), lowestCardsPlayed.get(suit)));
						}
					}

				}

				else {
					if (lowestCardsPlayed.get(suit) != null) {
						highestLowestCards.put(suit,
								cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit), lowestCardsPlayed.get(suit)));
					}
				}
			}

			if (highestLowestCards.size() == 0) {
				if(canPlaySpades) {
					return cardService.findSmallestCard(lowestRemainingCards.values());
				}
			
				return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				
			}
			if(canPlaySpades) {
				return cardService.findLargestCard(highestLowestCards.values());
			}
			return cardService.findLargestCard(cardService.filterOutSpades(highestLowestCards.values()));
			
		} else if (currPlayerTeamMate.isBidNil()) {
			Map<CardSuit, Card> lowestHighestCard = new HashMap<>();

			for (CardSuit suit : CardSuit.values()) {
				if (CardSuit.SPADES == suit) {
					if (canPlaySpades) {
						if (lowestCardsPlayed.get(suit) != null) {
							lowestHighestCard.put(suit,
									cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit), highestCardsPlayed.get(suit)));
						}
					}

				}

				else {
					if (lowestCardsPlayed.get(suit) != null) {
						lowestHighestCard.put(suit,
								cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit), highestCardsPlayed.get(suit)));
					}       
				}
			}
			
			if (lowestHighestCard.size() == 0) {
				if(canPlaySpades) {
					return cardService.findLargestCard(highestRemainingCards.values());
				}
				return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}

			return cardService.findSmallestCard(lowestHighestCard.values());
		} else {

			if(canPlaySpades) {
				return cardService.findLargestCard(highestRemainingCards.values());	
			}
			else {
				return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}
		}
	}

	private Card secondPlayerPlaying(SpadeGameMetaData spadeGameMetaData, SpadeGame newSpadeGame) {
		SpadeTeam leadingSpadeTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()));
		SpadeTeam currPlayerTeam = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer leadPlayer = leadingSpadeTeam.getPlayers().get(newSpadeGame.getStartTurn());

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(newSpadeGame.getCurrTurn());
		SpadePlayer currPlayerTeamMate = cardService.findTeamMate(currPlayerTeam.getPlayers(), currPlayer);
		Set<Card> spades = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES);
		Set<Card> clubs = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.CLUBS);
		Set<Card> hearts = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.HEARTS);
		Set<Card> diamonds = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.DIAMONDS);
		Card leadPlayerCard = leadPlayer.getPlayingCard();
		Map<CardSuit, Card> lowestRemainingCards = new HashMap<>();

		for (CardSuit suit : CardSuit.values()) {
			lowestRemainingCards.put(suit,
					cardService.findSmallestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}

		lowestRemainingCards.remove(null);

		Map<CardSuit, Card> highestRemainingCards = new HashMap<>();

		for (CardSuit suit : CardSuit.values()) {
			highestRemainingCards.put(suit,
					cardService.findLargestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}

		highestRemainingCards.remove(null);

		boolean hasLeadingPlayerSuit = spadeGameMetaData.getCurrPlayerRemainingCards()
				.containsKey(leadPlayerCard.getSuit());
		boolean hasOnlySpades = clubs.size() == 0 && hearts.size() == 0 && diamonds.size() == 0;
		if (currPlayer.isBidNil()) {
			if (hasLeadingPlayerSuit) {
				Card card = cardService.findLargestCardLessThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if (card != null) {
					return card;
				}
				return cardService.findSmallestCardLargerThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
			} else {
				if (hasOnlySpades) {

					return lowestRemainingCards.get(CardSuit.SPADES);
				}

				List<Card> suitsBesidesSpades = new ArrayList<>();
				for (Card card : highestRemainingCards.values()) {
					if (card.getSuit() != CardSuit.SPADES) {
						suitsBesidesSpades.add(card);
					}
				}
				return cardService.findLargestCard(suitsBesidesSpades);
			}
		}

		else if (currPlayerTeamMate.isBidNil()) {
			if (hasLeadingPlayerSuit) {
				Card card = cardService
						.findLargestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()));

				if ((card.getValue().getValue()) > leadPlayerCard.getValue().getValue()) {
					return card;
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (hasOnlySpades) {

					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				List<Card> suitsBesidesSpades = new ArrayList<>();
				for (Card card : lowestRemainingCards.values()) {
					if (card.getSuit() != CardSuit.SPADES) {
						suitsBesidesSpades.add(card);
					}
				}
				return cardService.findSmallestCard(suitsBesidesSpades);
			}

		}
		else {
			if(hasLeadingPlayerSuit) {
				if(highestRemainingCards.get(leadPlayerCard.getSuit()).getValue().getValue()>leadPlayerCard.getValue().getValue()) {
					
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				else {
					
					if(spades.size()>0) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					List<Card> suitsBesidesSpades = new ArrayList<>();
					for (Card card : lowestRemainingCards.values()) {
						if (card.getSuit() != CardSuit.SPADES) {
							suitsBesidesSpades.add(card);
						}
					}
					return cardService.findSmallestCard(suitsBesidesSpades);
					
				}
				
			}
		}
	}


	public void determineBots(SpadeGame newSpadeGame) {

		System.out.println("Determining Bots");
		int numberOfActivePlayers = 0;
		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player);
			if (spadePlayer.getUserId() != null) {

				spadePlayer.setBot(false);
				numberOfActivePlayers++;
			} else {

				spadePlayer.setBot(true);
			}
		}
		newSpadeGame.setActivePlayers(numberOfActivePlayers);

	}
}
