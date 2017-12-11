package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
public class SpadeBotHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadeValidationHandler validationService;
	
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
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					hearts++;
				}
				break;

			case SPADES:

				if (card.getValue().getValue() >= CardValue.NINE.getValue()) {
					spades++;
				}

				break;
			case DIAMONDS:
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					diamonds++;
				}
				break;
			case CLUBS:
				if (card.getValue().getValue() >= CardValue.KING.getValue()) {
					clubs++;
				}
				break;
			default:
				break;
			}

		}
		return POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs);

	}

	public Card getPlayerCard(SpadeGame newSpadeGame) {

		SpadePlayer leadPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getStartTurn());

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getCurrTurn());

		SpadePlayer currWinner = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());
		
		//SpadePlayer teamPlayer = newSpadeGame.getTeams().get(currPlayer.getTeam()).getPlayers()

		Card leadPlayerCard = leadPlayer.getPlayingCard();
		
		Card currWinnerCard = currWinner.getPlayingCard();

		Set<Card> currPlayerCards = currPlayer.getRemainingCards();

		currPlayerCards = cardService.sortCards(currPlayerCards);
		Set<Card> currPlayerCardsSameSuit = validationService.cardsToMatchSuit(leadPlayerCard.getSuit(),
				currPlayerCards);
		
		Card playingCard = null;
		if(currPlayerCardsSameSuit.size()>0) {
			
			if(currWinnerCard.getSuit()==CardSuit.SPADES) {
				
				playingCard = cardService.findSmallestCard(currPlayerCardsSameSuit);
				currPlayer.setPlayingCard(playingCard);
			}
			
			if(currWinner.getTeam()!=currPlayer.getTeam()) {
				
			
				playingCard = cardService.findCardToBeatWinnerCard(currPlayerCardsSameSuit, currWinnerCard,true);
				if(playingCard!=null) {
					
					currPlayer.setPlayingCard(playingCard);
				}
				else {
					
					playingCard = cardService.findSmallestCard(currPlayerCardsSameSuit);
					currPlayer.setPlayingCard(playingCard);
				}
				
			}
			
			
		}

		return null;

	}
	
	
}
