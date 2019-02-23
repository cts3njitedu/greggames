package com.craig.greggames.handler.bot.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
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

@Service
public class SpadeBotPlayerPositionTwo implements SpadeBotPlayerPosition {

	private final static int POSIITON = 2;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;
	private static final Logger logger = Logger.getLogger(SpadeBotPlayerPositionTwo.class);

	@Override
	public boolean validatePosition(int position) {
		// TODO Auto-generated method stub
		return position==POSIITON;
	}

	@Override
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
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
		
		SpadePlayer otherPlayer = otherTeam.getPlayers().get(PlayerTable.getPlayer(code));
		
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
		if (currPlayer.isBidNil()) {
			logger.info("Current player bid nil");
			if (hasLeadingPlayerSuit) {
				Card card = cardHandler.findLargestCardLessThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if (card != null) {
					return card;
				}
				return cardHandler.findSmallestCardLargerThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
			} else {
				if (hasOnlySpades) {

					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}

		else if (currPlayerTeamMate.isBidNil()) {
			logger.info("Current player teammate bid nil");
			if (hasLeadingPlayerSuit) {
				return highestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (canPlaySpades) {

					if(hasSpades) {
						
						return lowestRemainingCards.get(CardSuit.SPADES);			
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				}
				else {
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				}
			}

		}
		else if(leadPlayer.isBidNil()) {
			logger.info("Previous player bid nil");
			if(hasLeadingPlayerSuit) {
				Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
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
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}
		else if(otherPlayer.isBidNil()) {
			logger.info("Next player bid nil");
			if(hasLeadingPlayerSuit) {
				Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if(card!=null) {
					if((card.getValue().getValue()-2)>=leadPlayerCard.getValue().getValue()) {
						return card;
					}
					card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
					if(card!=null) {
						return card;
					}
					lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
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
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
					}
				}
				
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}
		else {
			logger.info("No one bid nil");
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
				
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
					
				}
				
			}
		}
	}

}
