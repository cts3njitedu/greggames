package com.craig.greggames.handler.bot.game.cards.spades;

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
import com.craig.greggames.model.game.cards.spades.SpadeTeam;

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
		return position == POSIITON;
	}

	@Override
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		SpadeTeam leadingSpadeTeam = spadeGameMetaData.getLeadingSpadeTeam();
		SpadeTeam currPlayerTeam = spadeGameMetaData.getCurrPlayerTeam();
		SpadePlayer leadPlayer = spadeGameMetaData.getLeadPlayer();

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(spadeGame.getCurrTurn());
		SpadePlayer currPlayerTeamMate = cardHandler.findTeamMate(currPlayerTeam.getPlayers(), currPlayer);

		Card leadPlayerCard = leadPlayer.getPlayingCard();

		SpadeTeam otherTeam = spadeGameMetaData.getOtherTeam();

		SpadePlayer otherPlayer = spadeGameMetaData.getOtherPlayerAfter();

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
				Card card = highestRemainingCards.get(leadPlayerCard.getSuit());
				if ((card.getValue().getValue() - 2) >= leadPlayerCard.getValue().getValue()) {
					return card;
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (hasSpades) {

					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(lowestRemainingCards.values());

			}

		} else if (leadPlayer.isBidNil()) {
			logger.info("Previous player bid nil");
			if (hasLeadingPlayerSuit) {
				Card card = cardHandler.findLargestCardLessThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if (card != null) {
					return card;
				}
				return highestRemainingCards.get(leadPlayerCard.getSuit());

			} else {
				if (leadPlayerCard.getValue().getValue() >= CardValue.NINE.getValue()) {
					if (hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				} else {
					if (hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(highestRemainingCards.values());
				}
			}
		} else if (positionThree.isBidNil()) {
			logger.info("Next player bid nil");
			if (hasLeadingPlayerSuit) {
				Card card = cardHandler.findSmallestCardLargerThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if (card != null) {
					return card;
				}

				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (leadPlayerCard.getValue().getValue() >= CardValue.JACK.getValue()) {

					if (hasSpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(highestRemainingCards.values());

				}

				if (hasOnlySpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		} else {
			logger.info("No one bid nil");
			if (hasLeadingPlayerSuit) {
				Card card = highestRemainingCards.get(leadPlayerCard.getSuit());
				if (card.getValue().getValue() > leadPlayerCard.getValue().getValue()) {

					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}

				return cardHandler.findSmallestCard(lowestRemainingCards.values());

			}
		}
	}

}
