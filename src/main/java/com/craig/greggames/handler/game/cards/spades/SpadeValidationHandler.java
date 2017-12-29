package com.craig.greggames.handler.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
public class SpadeValidationHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private CardHandler cardService;
	public boolean validateBid(SpadeGame spadeGame) {

		if (spadeGame.getGameModifier() == null) {

			return false;
		}

		SpadePlayer player = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getGameModifier());
		player.setError(false);
		player.getErrorMessages().clear();
		if (spadeGame.getGameModifier() != spadeGame.getCurrTurn()) {
			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());

			player.getRemainingCards().add(player.getPlayingCard());
			player.setPlayingCard(null);
			return false;

		}

		if (!player.isTurn()) {

			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());

			player.getRemainingCards().add(player.getPlayingCard());
			player.setPlayingCard(null);
			return false;
		} else {

			if (player.getPlayingCard() != null) {

				player.setError(true);
				player.getRemainingCards().add(player.getPlayingCard());
				player.setPlayingCard(null);
				player.getErrorMessages().put(SpadeErrors.INVALID_PLAY, SpadeErrors.INVALID_PLAY.getError());

				return false;
			}
		}

		return true;
	}

	public boolean validateTurn(SpadeGame spadeGame) {

		SpadePlayer player = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getGameModifier());
		player.setError(false);
		player.getErrorMessages().clear();
		if (spadeGame.getGameModifier() != spadeGame.getCurrTurn()) {
			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());
		
			player.getRemainingCards().add(player.getPlayingCard());
			player.setPlayingCard(null);
			return false;

		}
//
//		for (Entry<TeamTable, SpadeTeam> entry : spadeGame.getTeams().entrySet()) {
//
//			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {
//
//				if (entryPlayer.getValue().getPlayingCard() != null) {
//
//					if (!entryPlayer.getValue().isHasPlayed()) {
//
//						if (!entryPlayer.getValue().isTurn()) {
//
//							entryPlayer.getValue().setError(true);
//							entryPlayer.getValue().getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN,
//									SpadeErrors.NOT_YOUR_TURN.getError());
//							entryPlayer.getValue().getRemainingCards().add(entryPlayer.getValue().getPlayingCard());
//							entryPlayer.getValue().setPlayingCard(null);
//
//							return false;
//						}
//					
//					}
//				}
//			}
//		}

		return true;

	}

	public boolean validatePlayerCard(SpadeGame spadeGame) {

		
		if (spadeGame.getCurrTurn() == spadeGame.getStartTurn()) {

			return isValidWhenStarting(spadeGame);

		} else {

			SpadePlayer player = spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers()
					.get(spadeGame.getCurrTurn());

			SpadePlayer leadPlayer = spadeGame.getTeams()
					.get(teamService.getTeamByPlayer(spadeGame.getStartTurn(), spadeGame.getNumberOfTeams())).getPlayers()
					.get(spadeGame.getStartTurn());

			Card card = player.getPlayingCard();
			Card startCard = leadPlayer.getPlayingCard();

		
			Set<Card> cards = player.getRemainingCards();

			Map<CardSuit, Set<Card>> cardsForEachSuit = cardService.distributeCardsAccordingToSuit(cards);
			
			Set<Card> cardsToMatch = cardsForEachSuit.get(startCard.getSuit());
			if (cardsToMatch.size() > 0) {

				if (card.getSuit() != startCard.getSuit()) {

					player.setError(true);
					player.getErrorMessages().put(SpadeErrors.INVALID_SUIT, SpadeErrors.INVALID_SUIT.getError());
					player.getRemainingCards().add(player.getPlayingCard());
					player.setPlayingCard(null);
					player.setHasPlayed(false);
					return false;
				}
				return true;

			} else {
				return true;

			}
		}



	}
	
	public boolean isValidWhenStarting(SpadeGame spadeGame) {
		SpadePlayer leadPlayer = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers()
				.get(spadeGame.getCurrTurn());
		leadPlayer.setError(false);
		leadPlayer.getErrorMessages().clear();
		
		Card leadPlayerCard = leadPlayer.getPlayingCard();
		Set<Card> cards = leadPlayer.getRemainingCards();

		Map<CardSuit, Set<Card>> cardsForEachSuit = cardService.distributeCardsAccordingToSuit(cards);
		
		int numOfClubs = cardsForEachSuit.get(CardSuit.CLUBS).size();
		int numOfHearts = cardsForEachSuit.get(CardSuit.HEARTS).size();
		int numOfDiamonds = cardsForEachSuit.get(CardSuit.DIAMONDS).size();
		System.out.println("clubs: " +numOfClubs + ", hearts: "+numOfHearts+", diamonds: "+numOfDiamonds);
		
		if(leadPlayerCard.getSuit()==CardSuit.SPADES) {
			
			if(!spadeGame.isSpadePlayed()) {
				if(numOfClubs==0&&numOfHearts==0&&numOfDiamonds==0) {
					
					spadeGame.setSpadePlayed(true);
					return true;
				}
				else {
					leadPlayer.setError(true);
					leadPlayer.getErrorMessages().put(SpadeErrors.INVALID_SPADE, SpadeErrors.INVALID_SPADE.getError());
					leadPlayer.getRemainingCards().add(leadPlayer.getPlayingCard());
					leadPlayer.setPlayingCard(null);
				
					return false;
			
				}
			}
			return true;
		}
		
		return true;
		
	}


	public boolean isSpadePlayable(Map<CardSuit, Set<Card>> cardsForEachSuit, Card startCard, Card card,
			boolean isSpadePlayed) {

		if (startCard != card) {
			return true;
		} else {
			if (!isSpadePlayed) {

				if (cardsForEachSuit.get(CardSuit.CLUBS).size() == 0
						&& cardsForEachSuit.get(CardSuit.HEARTS).size() == 0
						&& cardsForEachSuit.get(CardSuit.DIAMONDS).size() == 0) {

					return true;

				} else {
					return false;
				}

			}

		}
		return true;
	}

	
}
