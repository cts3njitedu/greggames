package com.craig.greggames.handler.bot.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeBotPlayerPositionFour implements SpadeBotPlayerPosition {

	private final static int POSIITON = 4;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeBotPlayerPositionFour.class);

	@Override
	public boolean validatePosition(int position) {
		// TODO Auto-generated method stub
		return position==POSIITON;
	}

	@Override
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		SpadeTeam leadingSpadeTeam = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getStartTurn(), spadeGame.getNumberOfTeams()));
		SpadeTeam currPlayerTeam = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()));
		SpadePlayer leadPlayer = leadingSpadeTeam.getPlayers().get(spadeGame.getStartTurn());

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(spadeGame.getCurrTurn());
		SpadePlayer currPlayerTeamMate = cardHandler.findTeamMate(currPlayerTeam.getPlayers(), currPlayer);
		Set<Card> spades = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES);
		Set<Card> clubs = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.CLUBS);
		Set<Card> hearts = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.HEARTS);
		Set<Card> diamonds = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.DIAMONDS);
		Card leadPlayerCard = leadPlayer.getPlayingCard();
		
		int code = currPlayer.getName().getCode() + 1;
		if(code>MAX_TURN_PER_TRICK) {
			code = code - MAX_TURN_PER_TRICK;
		}
		
		Map<TeamTable, SpadeTeam> allTeams = spadeGame.getTeams();
		
		
		SpadeTeam otherTeam = allTeams.get(spadeTeamHandler.getOtherTeam(allTeams, currPlayerTeam.getName()));
		boolean hasOtherPlayerBidNil = false;
		for(Entry<PlayerTable, SpadePlayer>player: otherTeam.getPlayers().entrySet()) {
			if(player.getValue().isBidNil()) {
				hasOtherPlayerBidNil = true;
				break;
			}
		}
		
		SpadePlayer currWinner = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getTempWinner(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getTempWinner());
		Map<CardSuit, Card> lowestCardsPlayed = new HashMap<>();
		for (CardSuit suit :spadeGameMetaData.getAllPlayedCards().keySet() ) {
			lowestCardsPlayed.put(suit,
					cardHandler.findHighestSmallestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}

		lowestCardsPlayed.remove(null);
		Map<CardSuit, Card> highestCardsPlayed = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getAllPlayedCards().keySet()) {
			highestCardsPlayed.put(suit,
					cardHandler.findSmallestLargestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}
		Map<CardSuit, Card> lowestRemainingCards = new HashMap<>();

		for (CardSuit suit : CardSuit.values()) {
			lowestRemainingCards.put(suit,
					cardHandler.findSmallestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}

		lowestRemainingCards.remove(null);

		Map<CardSuit, Card> highestRemainingCards = new HashMap<>();

		for (CardSuit suit : CardSuit.values()) {
			highestRemainingCards.put(suit,
					cardHandler.findLargestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}

		highestRemainingCards.remove(null);

		boolean hasLeadingPlayerSuit = spadeGameMetaData.getCurrPlayerRemainingCards()
				.containsKey(leadPlayerCard.getSuit());
		boolean hasOnlySpades = clubs.size() == 0 && hearts.size() == 0 && diamonds.size() == 0;
		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || spadeGame.isSpadePlayed();
		boolean hasSpades = spades.size()==0;
		Card winnerCard = currWinner.getPlayingCard();
		
		if(currPlayer.isBidNil()) {
			logger.info("Current player bid nil");
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare = winnerCard;
				}
				Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				if(card!=null) {
					return null;
				}
				return highestRemainingCards.get(leadPlayerCard.getSuit());
			}
			else {
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(hasSpades) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(winnerCard.getSuit()), winnerCard);
						if(card!=null) {
							return card;
						}
						if(hasOnlySpades) {
							return highestRemainingCards.get(CardSuit.SPADES);
						}
						return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
					}
				}
				if(hasOnlySpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}
		else if(currPlayerTeamMate.isBidNil()) {
			logger.info("Current player teammate bid nil");
			if(hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare=winnerCard;
				}
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
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
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				}
				
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
			}
		}
		else if(hasOtherPlayerBidNil) {
			logger.info("Player before bid nil");
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName() == currWinner.getName()) {
					if(currPlayerTeamMate.getPlayingCard().getSuit()==leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
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
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				}
				if(hasOnlySpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				
			}
			
		}
		else {
			logger.info("No one bid nil");
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName()==currWinner.getName()) {
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				else if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
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
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				}
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(hasSpades) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), winnerCard);
						if(card!=null) {
							return card;
						}
						if(hasOnlySpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				}
				if(hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
			}
			
		}
		
		
		
	}

	

}
