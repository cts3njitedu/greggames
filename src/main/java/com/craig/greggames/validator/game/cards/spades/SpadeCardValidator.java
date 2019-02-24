package com.craig.greggames.validator.game.cards.spades;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.spades.SpadeBroken;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
@Order(4)
public class SpadeCardValidator extends AbstractSpadeValidator {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	@Autowired
	private CardHandler cardHandler;
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.PLAY);

	}
	
	private static final Logger logger = Logger.getLogger(SpadeCardValidator.class);

	@Override
	public boolean validate(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		logger.info("Entering " + getClass());
		if(!notificationSet.contains(spadeGame.getPlayerNotification())) {
			return true;
		}
		if (spadeGame.getCurrTurn() == spadeGame.getStartTurn()) {

			return isValidWhenStarting(spadeGame);

		} 
		
		else {

			SpadePlayer player = spadeGame.getTeams()
					.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getCurrTurn());
			
			SpadePlayer leadPlayer = spadeGame.getTeams()
					.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getStartTurn(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getStartTurn());

			Card card = player.getPlayingCard();
			Card startCard = leadPlayer.getPlayingCard();

			Set<Card> cards = player.getRemainingCards().stream().collect(Collectors.toSet());

			Map<CardSuit, Set<Card>> cardsForEachSuit = cardHandler.distributeCardsAccordingToSuit(cards);

			Set<Card> cardsToMatch = cardsForEachSuit.get(startCard.getSuit());
			if (cardsToMatch.size() > 0) {

				if (card.getSuit() != startCard.getSuit()) {

					player.setError(true);
					player.getErrorMessages().put(SpadeErrors.INVALID_SUIT, SpadeErrors.INVALID_SUIT.getError());
					if (player.getPlayingCard() != null) {
						player.getRemainingCards().add(player.getPlayingCard());
					}
					player.setPlayingCard(null);
					player.setHasPlayed(false);
					return false;
				}
			
				if (card.getSuit() == CardSuit.SPADES) {

					if (!spadeGame.isSpadePlayed()) {

						SpadeBroken spadeBroken = new SpadeBroken();
						spadeBroken.setFirstSpade(true);
						spadeBroken.setPlayer(leadPlayer.getName());
						spadeBroken.setTrickCount(spadeGame.getTrickCount());
						spadeGame.setSpadeBroken(spadeBroken);
					}
					spadeGame.setSpadePlayed(true);
				}
				return true;

			} else {

				if (card.getSuit() == CardSuit.SPADES) {
					if (!spadeGame.isSpadePlayed()) {

						SpadeBroken spadeBroken = new SpadeBroken();
						spadeBroken.setFirstSpade(true);
						spadeBroken.setPlayer(leadPlayer.getName());
						spadeBroken.setTrickCount(spadeGame.getTrickCount());
						spadeGame.setSpadeBroken(spadeBroken);
					}

					spadeGame.setSpadePlayed(true);
				}
				return true;

			}
		}

	}

	public boolean isValidWhenStarting(SpadeGame spadeGame) {
		SpadePlayer leadPlayer = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers()
				.get(spadeGame.getCurrTurn());
		
		leadPlayer.setError(false);
		leadPlayer.getErrorMessages().clear();

		Card leadPlayerCard = leadPlayer.getPlayingCard();
		Set<Card> cards = leadPlayer.getRemainingCards().stream().collect(Collectors.toSet());

		Map<CardSuit, Set<Card>> cardsForEachSuit = cardHandler.distributeCardsAccordingToSuit(cards);

		int numOfClubs = cardsForEachSuit.get(CardSuit.CLUBS).size();
		int numOfHearts = cardsForEachSuit.get(CardSuit.HEARTS).size();
		int numOfDiamonds = cardsForEachSuit.get(CardSuit.DIAMONDS).size();
		System.out.println("clubs: " + numOfClubs + ", hearts: " + numOfHearts + ", diamonds: " + numOfDiamonds);

		if (leadPlayerCard.getSuit() == CardSuit.SPADES) {

			if (!spadeGame.isSpadePlayed()) {
				if (numOfClubs == 0 && numOfHearts == 0 && numOfDiamonds == 0) {

					SpadeBroken spadeBroken = new SpadeBroken();
					spadeBroken.setFirstSpade(true);
					spadeBroken.setPlayer(leadPlayer.getName());
					spadeBroken.setTrickCount(spadeGame.getTrickCount());
					spadeGame.setSpadeBroken(spadeBroken);
					spadeGame.setSpadePlayed(true);

					return true;
				} else {
					leadPlayer.setError(true);
					leadPlayer.getErrorMessages().put(SpadeErrors.INVALID_SPADE, SpadeErrors.INVALID_SPADE.getError());
					if (leadPlayer.getPlayingCard() != null) {
						leadPlayer.getRemainingCards().add(leadPlayer.getPlayingCard());
					}
					leadPlayer.setPlayingCard(null);

					return false;

				}
			}
			return true;
		}

		logger.info("Exiting " + getClass());
		return true;

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);

	}

}
