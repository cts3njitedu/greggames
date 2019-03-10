package com.craig.greggames.handler.bot.game.cards.spades;

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
		return position == POSIITON;
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

		Map<Integer, SpadePlayer> positionPlayerMap = spadeGameMetaData.getPositionPlayerMap();
		SpadePlayer positionThree = positionPlayerMap.get(3);

		boolean hasLeadingPlayerSuit = spadeGameMetaData.isHasLeadPlayerSuit();
		boolean hasOnlySpades = spadeGameMetaData.isHasOnlySpades();
		boolean canPlaySpades = spadeGameMetaData.isCanPlaySpades();
		boolean hasSpades = spadeGameMetaData.isHasSpades();
		Card winnerCard = currWinner.getPlayingCard();
		if (currPlayer.isBidNil()) {
			logger.info("Current player bid nil");
			if (hasLeadingPlayerSuit) {
				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					if (currPlayerTeamMate.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								currPlayerTeamMate.getPlayingCard());
						if (card != null) {
							return card;
						}
						return highestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				} else if (spadeGameMetaData.getLeadPlayer().getName() == currWinner.getName()) {
					Card card = cardHandler.findLargestCardLessThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							leadPlayerCard);
					if (card != null) {
						return card;
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				} else {
					if (positionThree.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								positionThree.getPlayingCard());
						if (card != null) {
							return card;
						}
						return highestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}

			} else {

				if (hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));

			}
		} else if (spadeGameMetaData.getCurrPlayerTeamMate().isBidNil()) {
			logger.info("Current player teammate bid nil");
			if (hasLeadingPlayerSuit) {
				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					if (currPlayerTeamMate.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = highestRemainingCards.get(leadPlayerCard.getSuit());
						if(card.getValue().getValue()>currPlayerTeamMate.getPlayingCard().getValue().getValue()) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {
				if (currPlayerTeamMate.getName() == currWinner.getName()) {
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
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				
			}
		} else if (spadeGameMetaData.getLeadPlayer().isBidNil()||positionThree.isBidNil()) {
			if (hasLeadingPlayerSuit) {
				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					if (currPlayerTeamMate.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								winnerCard);
						if (card != null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());

			} else {
				
				return cardHandler.findSmallestCard(spadeGameMetaData.getHighestRemainingCard().values());
			
			}

		}  else {
			logger.info("No one bid nil");
			if (spadeGameMetaData.isHasLeadPlayerSuit()) {
				if (spadeGameMetaData.getCurrPlayerTeamMate().getName() == spadeGameMetaData.getCurrWinner()
						.getName()) {
					return spadeGameMetaData.getLowestRemainingCard().get(leadPlayerCard.getSuit());
				} 
				else if(spadeGameMetaData.getLeadPlayer().getName() == currWinner.getName()) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
					if(card!=null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				else {
					if(positionThree.getPlayingCard().getSuit()==leadPlayerCard.getSuit()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), positionThree.getPlayingCard());
						if(card!=null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
			} else {
				if (spadeGameMetaData.getCurrPlayerTeamMate().getName() == spadeGameMetaData.getCurrWinner()
						.getName()) {
					if (hasOnlySpades) {
						return spadeGameMetaData.getLowestRemainingCard().get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(
							cardHandler.filterOutSpades(spadeGameMetaData.getLowestRemainingCard().values()));
				}
				if(winnerCard.getSuit()==CardSuit.SPADES) {
					Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES), winnerCard);
					if(card!=null) {
						return card;
					}
					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				}
				
				if(hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(lowestRemainingCards.values());
				
			}

		}

	}

}
