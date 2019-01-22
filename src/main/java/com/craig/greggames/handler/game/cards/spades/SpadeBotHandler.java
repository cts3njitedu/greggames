package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;


import java.util.HashMap;
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
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

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
		
		else if(playerTrickPosition==3) {
			return thirdPlayerPlaying(spadeGameMetaData, newSpadeGame);
		}
		else if(playerTrickPosition==4) {
			return fourthPlayerPlaying(spadeGameMetaData, newSpadeGame);
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
		Map<TeamTable, SpadeTeam> allTeams = newSpadeGame.getTeams();
		SpadeTeam otherTeam = allTeams.get(teamService.getOtherTeam(allTeams, currPlayerTeam.getName()));
		boolean hasOtherPlayerBidNil = false;
		
		for(Entry<PlayerTable, SpadePlayer> player: otherTeam.getPlayers().entrySet()) {
			if(player.getValue().isBidNil()) {
				hasOtherPlayerBidNil = true;
				break;
			}
		}
		
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

			if(canPlaySpades) {
				return cardService.findSmallestCard(lowestRemainingCards.values());
			}
			return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
			
		} else if (currPlayerTeamMate.isBidNil()) {
			if(canPlaySpades) {
				return cardService.findLargestCard(highestRemainingCards.values());
			}
			

			return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
		} 
		else if(hasOtherPlayerBidNil) {
			if(canPlaySpades) {
				return cardService.findSmallestCard(lowestRemainingCards.values());
			}
			return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
		}
		else {

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
		
		int code = currPlayer.getName().getCode() + 1;
		if(code>MAX_TURN_PER_TRICK) {
			code = code - MAX_TURN_PER_TRICK;
		}
		
		
		Map<TeamTable, SpadeTeam> allTeams = newSpadeGame.getTeams();
		
		
		SpadeTeam otherTeam = allTeams.get(teamService.getOtherTeam(allTeams, currPlayerTeam.getName()));
		
		SpadePlayer otherPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(code));
		
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
		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || newSpadeGame.isSpadePlayed();
		boolean hasSpades = spades.size()==0;
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
				return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}
		}

		else if (currPlayerTeamMate.isBidNil()) {
			if (hasLeadingPlayerSuit) {
				return highestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (canPlaySpades) {

					if(hasSpades) {
						
						return lowestRemainingCards.get(CardSuit.SPADES);			
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				else {
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
			}

		}
		else if(leadPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if(card==null) {
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return card;
				
			}
			else {
				if(canPlaySpades) {
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}
		}
		else if(otherPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card card = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if(card!=null) {
					if((card.getValue().getValue()-2)>=leadPlayerCard.getValue().getValue()) {
						return card;
					}
					card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
					if(card!=null) {
						return card;
					}
					lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if(card!=null) {
					return card;
				}
				
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				
				if(leadPlayerCard.getValue().getValue()>CardValue.JACK.getValue()) {
					if(canPlaySpades) {
						if(hasSpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
					}
				}
				
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
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
					
					if(hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
				
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
					
				}
				
			}
		}
	}
	
	private Card thirdPlayerPlaying(SpadeGameMetaData spadeGameMetaData, SpadeGame newSpadeGame) {
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
		
		int code = currPlayer.getName().getCode() + 1;
		if(code>MAX_TURN_PER_TRICK) {
			code = code - MAX_TURN_PER_TRICK;
		}
		
		Map<TeamTable, SpadeTeam> allTeams = newSpadeGame.getTeams();
		
		
		SpadeTeam otherTeam = allTeams.get(teamService.getOtherTeam(allTeams, currPlayerTeam.getName()));
		code = currPlayer.getName().getCode() - 1;
		
		if(code<1) {
			code = MAX_TURN_PER_TRICK;
		}
		SpadePlayer otherPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(code));
		SpadePlayer previousPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(code));
		SpadePlayer currWinner = newSpadeGame.getTeams().get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfPlayers()))
				.getPlayers().get(newSpadeGame.getTempWinner());
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
		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || newSpadeGame.isSpadePlayed();
		boolean hasSpades = spades.size()==0;
		Card winnerCard = currWinner.getPlayingCard();
		if(currPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==cardToCompare.getSuit()) {
					cardToCompare = winnerCard;
				}
				
				Card card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(cardToCompare.getSuit()), cardToCompare);
				
				if(card!=null) {
					return card;
				}
				
				return cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(cardToCompare.getSuit()), cardToCompare);
				
			}
			
			else {
				if(canPlaySpades) {
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				
				return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				
			}
		}
		if(currPlayerTeamMate.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				if(currWinner.getName()==currPlayerTeamMate.getName()) {
					if(currWinner.getPlayingCard().getSuit()==leadPlayerCard.getSuit()) {
						Card card  = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), currPlayerTeamMate.getPlayingCard());
						
						if(card!=null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
						
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				
				if(canPlaySpades) {
					if(currWinner.getName()==currPlayerTeamMate.getName()) {
						if(hasSpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						else {
							return cardService.findSmallestCard(lowestRemainingCards.values());
						}
					}
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				else {
					
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
			}
		}
		else if(previousPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				if(previousPlayer.getName()==currWinner.getName()) {

					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				//teammate is winning
				return highestRemainingCards.get(leadPlayerCard.getSuit());
				
			}
			else {
				
				if(previousPlayer.getName()==currWinner.getName()) {
					
					if(hasOnlySpades) {
						if(previousPlayer.getPlayingCard().getSuit()==CardSuit.SPADES) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return highestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				else {
					
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				
				
			}
		}
		else if(otherPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare = winnerCard;
				}
				Card card = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				if(card!=null) {
					if((card.getValue().getValue()-2)>=cardToCompare.getValue().getValue()) {
						return card;
					}	
					card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
					if(card!=null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
							
				}
				return cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				
			}
			else {
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}
		}
		
		else {
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					Card card = currPlayerTeamMate.getPlayingCard();
					if(card.getSuit()==leadPlayerCard.getSuit()) {
						if(card.getValue().getValue()>=highestCardsPlayed.get(card.getSuit()).getValue().getValue()) {
							boolean isAllPlayersGreaterThanCard = cardService.isAllCardsGreaterThanCardPlayed(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), card);
							if(isAllPlayersGreaterThanCard) {
								return lowestRemainingCards.get(leadPlayerCard.getSuit());
							}
							if((highestRemainingCards.get(leadPlayerCard.getSuit()).getValue().getValue()-1)>currPlayerTeamMate.getPlayingCard().getValue().getValue()) {
								return highestRemainingCards.get(leadPlayerCard.getSuit());
							}
							return lowestRemainingCards.get(leadPlayerCard.getSuit());
						}
						return highestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return highestRemainingCards.get(leadPlayerCard.getSuit());	
			}
			else {
				
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					Card card = currPlayerTeamMate.getPlayingCard();
					if(card.getSuit()==leadPlayerCard.getSuit()) {
						if(card.getValue().getValue()>=highestCardsPlayed.get(card.getSuit()).getValue().getValue()) {
							if(hasOnlySpades) {
								return lowestRemainingCards.get(CardSuit.SPADES);
							}
							return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
						}
						if(hasSpades) {
							return highestRemainingCards.get(CardSuit.SPADES);
						}
						
						return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
					}
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				if(hasSpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				
				return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
					
			}
			
			
		}
		
		
	}

	private Card fourthPlayerPlaying(SpadeGameMetaData spadeGameMetaData, SpadeGame newSpadeGame) {
		
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
		
		int code = currPlayer.getName().getCode() + 1;
		if(code>MAX_TURN_PER_TRICK) {
			code = code - MAX_TURN_PER_TRICK;
		}
		
		Map<TeamTable, SpadeTeam> allTeams = newSpadeGame.getTeams();
		
		
		SpadeTeam otherTeam = allTeams.get(teamService.getOtherTeam(allTeams, currPlayerTeam.getName()));
		boolean hasOtherPlayerBidNil = false;
		for(Entry<PlayerTable, SpadePlayer>player: otherTeam.getPlayers().entrySet()) {
			if(player.getValue().isBidNil()) {
				hasOtherPlayerBidNil = true;
				break;
			}
		}
		
		SpadePlayer currWinner = newSpadeGame.getTeams().get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfPlayers()))
				.getPlayers().get(newSpadeGame.getTempWinner());
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
		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || newSpadeGame.isSpadePlayed();
		boolean hasSpades = spades.size()==0;
		Card winnerCard = currWinner.getPlayingCard();
		
		if(currPlayer.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare = winnerCard;
				}
				Card card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				if(card!=null) {
					return null;
				}
				return highestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(hasSpades) {
						Card card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(winnerCard.getSuit()), winnerCard);
						if(card!=null) {
							return card;
						}
						if(hasOnlySpades) {
							return highestRemainingCards.get(CardSuit.SPADES);
						}
						return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
					}
				}
				if(hasOnlySpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardService.findLargestCard(cardService.filterOutSpades(highestRemainingCards.values()));
			}
		}
		else if(currPlayerTeamMate.isBidNil()) {
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare=winnerCard;
				}
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					Card card = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
					if(card!=null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					if(hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
			}
		}
		else if(hasOtherPlayerBidNil) {
			
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName() == currWinner.getName()) {
					if(currPlayerTeamMate.getPlayingCard().getSuit()==leadPlayerCard.getSuit()) {
						Card card = cardService.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
						if(card!=null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				if(currPlayerTeamMate.getName() == currWinner.getName()) {
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				}
				if(hasOnlySpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(highestRemainingCards.values()));
				
			}
			
		}
		else {
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				else if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					Card card = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
					if(card!=null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				if(currWinner.getName()==currPlayerTeamMate.getName()) {
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(hasSpades) {
						Card card = cardService.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), winnerCard);
						if(card!=null) {
							return card;
						}
						if(hasOnlySpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
					}
					return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
				}
				if(hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardService.findSmallestCard(cardService.filterOutSpades(lowestRemainingCards.values()));
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
