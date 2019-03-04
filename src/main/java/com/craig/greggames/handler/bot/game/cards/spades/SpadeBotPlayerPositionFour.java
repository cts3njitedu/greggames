package com.craig.greggames.handler.bot.game.cards.spades;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

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
		
	
		SpadePlayer currPlayer = spadeGameMetaData.getCurrPlayer();
		SpadePlayer currPlayerTeamMate = spadeGameMetaData.getCurrPlayerTeamMate();

		Card leadPlayerCard = spadeGameMetaData.getLeadPlayer().getPlayingCard();

		SpadePlayer otherPlayer = spadeGameMetaData.getOtherPlayerAfter();
		SpadePlayer previousPlayer = spadeGameMetaData.getOtherPlayerBefore();
		logger.info(spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getTempWinner(), spadeGame.getNumberOfTeams())));
		SpadePlayer currWinner = spadeGameMetaData.getCurrWinner();
		Map<CardSuit, Card> lowestCardsPlayed = spadeGameMetaData.getLowestCardPlayed();

		Map<CardSuit, Card> highestCardsPlayed = spadeGameMetaData.getHighestCardPlayed();
		Map<CardSuit, Card> lowestRemainingCards = spadeGameMetaData.getLowestRemainingCard();

		Map<CardSuit, Card> highestRemainingCards = spadeGameMetaData.getHighestRemainingCard();

		boolean hasLeadingPlayerSuit = spadeGameMetaData.isHasLeadPlayerSuit();
		boolean hasOnlySpades = spadeGameMetaData.isHasOnlySpades();
		boolean canPlaySpades = spadeGameMetaData.isCanPlaySpades();
		boolean hasSpades = spadeGameMetaData.isHasSpades();
		Card winnerCard = currWinner.getPlayingCard();
		if(spadeGameMetaData.getCurrPlayer().isBidNil()) {
			logger.info("Current player bid nil");
			if(spadeGameMetaData.isHasLeadPlayerSuit()) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare = winnerCard;
				}
				Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				if(card!=null) {
					return card;
				}
				return spadeGameMetaData.getHighestRemainingCard().get(leadPlayerCard.getSuit());
				
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
						return cardHandler.findLargestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
					}
					return cardHandler.findLargestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
				}
				if(hasOnlySpades) {
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
			}
		}
		else if(spadeGameMetaData.getCurrPlayerTeamMate().isBidNil()) {
			logger.info("Current player teammate bid nil");
			if(spadeGameMetaData.isHasLeadPlayerSuit()) {
				Card cardToCompare = leadPlayerCard;
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					cardToCompare=winnerCard;
				}
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
					if(card!=null) {
						return card;
					}
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
			}
			else {
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					if(currPlayerTeamMate.getPlayingCard().getSuit()==CardSuit.SPADES) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), currPlayerTeamMate.getPlayingCard());
						if(card!=null) {
							return card;
						}
						if(hasOnlySpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
					}
					if(hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				}
				
				if(spadeGameMetaData.isHasOnlySpades()) {
					return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getLowestRemainingCard().values()));
			}
		}
		else if(spadeGameMetaData.getLeadPlayer().isBidNil()) {
			if(hasLeadingPlayerSuit) {
				if(currPlayerTeamMate.getName() == currWinner.getName()) {
					if(currPlayerTeamMate.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
						if(card!=null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
					if(card!=null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
			
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
				
			}
			else {
				if(currPlayerTeamMate.getName() ==currWinner.getName()) {
					return cardHandler.findSmallestCard(spadeGameMetaData.getHighestRemainingCard().values());
				}
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(hasOnlySpades) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), winnerCard);
						if(card!=null) {
							return card;
						}
						return highestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				}
				if(hasOnlySpades) {
					
					return highestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
			
		}
		else if(spadeGameMetaData.getOtherPlayerBefore().isBidNil()) {
			logger.info("Player before bid nil");
			if(spadeGameMetaData.isHasLeadPlayerSuit()) {
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName() == spadeGameMetaData.getCurrWinner().getName()) {
					if(spadeGameMetaData.getCurrPlayerTeamMate().getPlayingCard().getSuit()==leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
						if(card!=null) {
							return card;
						}
						return spadeGameMetaData.getHighestRemainingCard().get(leadPlayerCard.getSuit());
					}
	
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				if(spadeGameMetaData.getOtherPlayerBefore().getName() == spadeGameMetaData.getCurrWinner().getName()) {
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				if(spadeGameMetaData.getLeadPlayer().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				return spadeGameMetaData.getHighestRemainingCard().get(leadPlayerCard.getSuit());
			}
			else {
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName() == spadeGameMetaData.getCurrWinner().getName()) {
					if(spadeGameMetaData.isHasOnlySpades()) {
						return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
				}
				if(spadeGameMetaData.getOtherPlayerBefore().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					Card otherPlayerBeforeCard = spadeGameMetaData.getOtherPlayerBefore().getPlayingCard();
					boolean hasOtherPlayerSuit = spadeGameMetaData.getCurrPlayerRemainingCards().get(otherPlayerBeforeCard.getSuit())!=null;
					if(hasOtherPlayerSuit) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerPlayedCards().get(otherPlayerBeforeCard.getSuit()), otherPlayerBeforeCard);
						if(card!=null) {
							return card;
						}
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
					}
					if(hasOnlySpades) {
						spadeGameMetaData.getHighestRemainingCard().get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
					
				}
				if(spadeGameMetaData.getLeadPlayer().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					if(hasOnlySpades) {
						spadeGameMetaData.getHighestRemainingCard().get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getHighestRemainingCard().values()));
				}
				return cardHandler.findSmallestCard(spadeGameMetaData.getHighestRemainingCard().values());
			}
			
		}
		else {
			logger.info("No one bid nil");
			if(spadeGameMetaData.isHasLeadPlayerSuit()) {
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				else if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), winnerCard);
					if(card!=null) {
						return card;
					}
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				}
				return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
			}
			else {
				if(spadeGameMetaData.getCurrPlayerTeamMate().getName()==spadeGameMetaData.getCurrWinner().getName()) {
					if(spadeGameMetaData.isHasOnlySpades()) {
						return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getLowestRemainingCard().values()));
				}
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					if(spadeGameMetaData.isHasSpades()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), winnerCard);
						if(card!=null) {
							return card;
						}
						if(spadeGameMetaData.isHasOnlySpades()) {
							return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
						}
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(spadeGameMetaData.getLowestRemainingCard().values()));
					}
					return cardHandler.findSmallestCard(spadeGameMetaData.getLowestRemainingCard().values());
				}
				if(spadeGameMetaData.isHasSpades()) {
					return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(spadeGameMetaData.getLowestRemainingCard().values());
			}
			
		}
		
		
		
	}

	

}
