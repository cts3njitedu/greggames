package com.craig.greggames.handler.cards.spades;

import java.util.List;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

import static com.craig.greggames.constants.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

@Service
public class SpadeBotHandler {

	public int getBotBid(SpadePlayer player) {
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;

		List<Card> cards = player.getRemainingCards();
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
}
