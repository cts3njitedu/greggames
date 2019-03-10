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
public class SpadeBotPlayerPositionOne implements SpadeBotPlayerPosition {

	private final static int POSIITON = 1;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeBotPlayerPositionOne.class);

	@Override
	public boolean validatePosition(int position) {
		// TODO Auto-generated method stub
		return position==POSIITON;
	}

	@Override
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering "+ getClass());
		SpadePlayer currPlayer = spadeGameMetaData.getCurrPlayer();

		SpadePlayer currPlayerTeamMate = spadeGameMetaData.getCurrPlayerTeamMate();
		boolean hasOtherPlayerBidNil = spadeGameMetaData.getOtherPlayerAfter().isBidNil();

		Map<CardSuit, Card> lowestCardsPlayed = spadeGameMetaData.getLowestCardPlayed();
		Map<CardSuit, Card> highestCardsPlayed = spadeGameMetaData.getHighestCardPlayed();
		Map<CardSuit, Card> lowestRemainingCards = spadeGameMetaData.getLowestRemainingCard();
		Map<CardSuit, Card> highestRemainingCards = spadeGameMetaData.getHighestRemainingCard();
		Map<Integer, SpadePlayer> positionPlayerMap = spadeGameMetaData.getPositionPlayerMap();
		SpadePlayer positionTwo = positionPlayerMap.get(2);
		SpadePlayer positionFour = positionPlayerMap.get(4);

		boolean canPlaySpades = spadeGameMetaData.isCanPlaySpades();
		
		boolean hasOnlySpades = spadeGameMetaData.isHasOnlySpades();
		if (currPlayer.isBidNil()) {
			logger.info("Current player bid nil");
			if(canPlaySpades) {
				return cardHandler.findSmallestCard(lowestRemainingCards.values());
			}
			return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
			
		} else if (currPlayerTeamMate.isBidNil()) {
			logger.info("Current player bid nil");
			if(canPlaySpades) {
				return cardHandler.findLargestCard(highestRemainingCards.values());
			}
			

			return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
		} 
		else if(positionTwo.isBidNil() || positionFour.isBidNil()) {
			logger.info("Other player bid nil");
			if(canPlaySpades) {
				return cardHandler.findSmallestCard(lowestRemainingCards.values());
			}
			return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
		}
		else {
			logger.info("No one bids nil");
			if(canPlaySpades) {
				return cardHandler.findLargestCard(highestRemainingCards.values());	
			}
			else {
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}
		
	}

}
