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
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.GregMapper;

import ch.qos.logback.core.joran.conditional.IfAction;

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
				Card cardToCompare = leadPlayerCard;
				if (winnerCard.getSuit() == cardToCompare.getSuit()) {
					cardToCompare = winnerCard;
				}

				Card card = cardHandler.findLargestCardLessThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(cardToCompare.getSuit()), cardToCompare);

				if (card != null) {
					return card;
				}

				return cardHandler.findSmallestCardLargerThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(cardToCompare.getSuit()), cardToCompare);

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

				if (hasSpades) {
					if (currWinner.getName() == currPlayerTeamMate.getName()) {
						if (hasSpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						} else {
							return cardHandler.findSmallestCard(lowestRemainingCards.values());
						}
					}
					if (hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
				} else {

					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				}
			}
		} else if (previousPlayer.isBidNil()) {
			logger.info("Previous player bid nil");
			if (hasLeadingPlayerSuit) {
				if (previousPlayer.getName() == currWinner.getName()) {

					Card card = cardHandler.findLargestCardLessThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), previousPlayer.getPlayingCard());
					if(card!=null) {
						return card;
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}
				// teammate is winning
				Card card = cardHandler.findSmallestCardLargerThanCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), leadPlayerCard);
				if(card!=null) {
					return card;
				}
				return highestRemainingCards.get(leadPlayerCard.getSuit());

			} else {

				if (previousPlayer.getName() == currWinner.getName()) {

					if (hasOnlySpades) {
						if (previousPlayer.getPlayingCard().getSuit() == CardSuit.SPADES) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return highestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				} else {

					if (hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				}

			}
		} else if (otherPlayer.isBidNil()) {
			logger.info("Next player bid nil");
			if (hasLeadingPlayerSuit) {
				Card cardToCompare = leadPlayerCard;
				if (winnerCard.getSuit() == leadPlayerCard.getSuit()) {
					cardToCompare = winnerCard;
				}
				Card card = cardHandler.findSmallestCardLargerThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);
				if (card != null) {
					if ((card.getValue().getValue() - 2) >= cardToCompare.getValue().getValue()) {
						return card;
					}
					card = cardHandler.findLargestCardLessThanCard(
							spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()),
							cardToCompare);
					if (card != null) {
						return card;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());

				}
				return cardHandler.findLargestCardLessThanCard(
						spadeGameMetaData.getCurrPlayerRemainingCards().get(leadPlayerCard.getSuit()), cardToCompare);

			} else {
				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					if (hasOnlySpades) {
						return lowestRemainingCards.get(CardSuit.SPADES);
					}
					return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
				}
				return cardHandler.findSmallestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}

		else {
			logger.info("No one bid nil");
			if (hasLeadingPlayerSuit) {
				Card playerCard = highestRemainingCards.get(leadPlayerCard.getSuit());
				Card card = currPlayerTeamMate.getPlayingCard();

				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					
					if(card.getSuit()==leadPlayerCard.getSuit()) {
						if((playerCard.getValue().getValue()-2)>=card.getValue().getValue()) {
							return playerCard;
						}
						return lowestRemainingCards.get(leadPlayerCard.getSuit());
					}
					return highestRemainingCards.get(leadPlayerCard.getSuit());
				}
				
				if(winnerCard.getSuit()==leadPlayerCard.getSuit()) {
					if((playerCard.getValue().getValue()-2)>=winnerCard.getValue().getValue()) {
						return playerCard;
					}
					return lowestRemainingCards.get(leadPlayerCard.getSuit());
				}
				
				return lowestRemainingCards.get(leadPlayerCard.getSuit());
			} else {

				if (currPlayerTeamMate.getName() == currWinner.getName()) {
					Card card = currPlayerTeamMate.getPlayingCard();
					if (card.getSuit() == leadPlayerCard.getSuit()) {
						if(hasOnlySpades) {
							return lowestRemainingCards.get(CardSuit.SPADES);
						}
						return cardHandler.findSmallestCard(lowestRemainingCards.values());
					}
					
					return cardHandler.findSmallestCard(lowestRemainingCards.values());
				}
				if (hasSpades) {
					return lowestRemainingCards.get(CardSuit.SPADES);
				}

				return cardHandler.findSmallestCard(lowestRemainingCards.values());

			}

		}
	}

}
