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
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.util.GregMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.LowerCaseStrategy;

@Service
public class SpadeBotPlayerPositionThree implements SpadeBotPlayerPosition {

	private final static int POSIITON = 3;

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;

	private static final Logger logger = Logger.getLogger(SpadeBotPlayerPositionThree.class);

	@Autowired
	private GregMapper gregMapper;

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
		SpadePlayer positionTwo = positionPlayerMap.get(2);
		SpadePlayer positionFour = positionPlayerMap.get(4);

		boolean hasLeadingPlayerSuit = spadeGameMetaData.isHasLeadPlayerSuit();
		boolean hasOnlySpades = spadeGameMetaData.isHasOnlySpades();
		boolean canPlaySpades = spadeGameMetaData.isCanPlaySpades();
		boolean hasSpades = spadeGameMetaData.isHasSpades();
		Card winnerCard = currWinner.getPlayingCard();

		logger.info("Current player card " + gregMapper.convertObjectToString(currPlayer.getPlayingCard())
				+ " Current teammate card " + gregMapper.convertObjectToString(currPlayerTeamMate.getPlayingCard())
				+ " Player before card: " + gregMapper.convertObjectToString(otherPlayer.getPlayingCard()));
		if (currPlayer.isBidNil()) {
			logger.info("Current player bid nil");
			if (hasLeadingPlayerSuit) {
				if (spadeGameMetaData.getLeadPlayer().getName() == currWinner.getName()) {
					Card card = cardHandler.findLargestCardLessThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							leadPlayerCard);
					if (card != null) {
						return card;
					}
					return cardHandler.findSmallestCardLargerThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							leadPlayerCard);
				} else {
					if (positionFour.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								positionFour.getPlayingCard());
						if (card != null) {
							return card;
						}
						return cardHandler.findSmallestCardLargerThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								positionFour.getPlayingCard());
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}

			}

			else {

				if (hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));

			}
		}
		if (currPlayerTeamMate.isBidNil()) {
			logger.info("Current player teammate bid nil");
			if (hasLeadingPlayerSuit) {
				if (currWinner.getName() == currPlayerTeamMate.getName()) {
					if (currWinner.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(
								spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
								currPlayerTeamMate.getPlayingCard());

						if (card != null) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());

					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {
				if (currWinner.getName() == currPlayerTeamMate.getName()) {
					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				} else {
					if (hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				}
			}
		} else if (positionTwo.isBidNil()) {
			logger.info("Previous player bid nil");
			if (hasLeadingPlayerSuit) {
				if (positionTwo.getName() == currWinner.getName()) {

					if (positionTwo.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerPlayedCards()
								.get(positionTwo.getPlayingCard().getSuit()), positionTwo.getPlayingCard());
						if (card != null) {
							return card;
						}
						return highestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				} else {
					// teammate is winning
					Card card = cardHandler.findSmallestCardLargerThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							leadPlayerCard);
					if (card != null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}

			} else {

				if (hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));

			}
		} else if (positionFour.isBidNil()) {
			logger.info("Next player bid nil");
			if (hasLeadingPlayerSuit) {
				if (spadeGameMetaData.getLeadPlayer().getName() == currWinner.getName()) {
					Card card = cardHandler.findLargestCardLessThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							leadPlayerCard);
					if (card != null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				} else {
					if (positionTwo.getName() == currWinner.getName()) {
						Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData
								.getCurrPlayerRemainingCards().get(positionTwo.getPlayingCard().getSuit()),
								positionTwo.getPlayingCard());
						if (card != null) {
							return card;
						}
						return cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards()
								.get(positionTwo.getPlayingCard().getSuit()), positionTwo.getPlayingCard());
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
			} else {
				if(hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}

		else {
			logger.info("No one bid nil");
			if (hasLeadingPlayerSuit) {
				Card card = highestRemainingCards.get(leadPlayerCard.getSuit());
				
				if(spadeGameMetaData.getLeadPlayer().getName() == currWinner.getName()) {
					if((card.getValue().getValue()-2)>=leadPlayerCard.getValue().getValue()) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				else {
					if(positionTwo.getPlayingCard().getSuit() == leadPlayerCard.getSuit()) {
						if(card.getValue().getValue() > positionTwo.getPlayingCard().getValue().getValue()) {
							return card;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit()); 
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
			} else {

				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					if(leadPlayerCard.getValue().getValue()>=CardValue.JACK.getValue()) {
						if(hasOnlySpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
					}
					if(hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values())); 
				}
				if (hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}

				return cardHandler.findSmallestCard(lowestRemainingCards.values());

			}

		}
	}

}
