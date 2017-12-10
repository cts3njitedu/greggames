package com.craig.greggames.handler.cards.spades;

import static com.craig.greggames.constants.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;


@Service
public class SpadePlayerHandler {

	@Autowired
	private CardHandler cardService;
	@Autowired
	private SpadeTeamHandler teamService;

	public void determinePlayerCard(SpadeGame newSpadeGame) {
//
//		SpadePlayer currPlayer = newSpadeGame.getTeams()
//				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
//				.getPlayers().get(newSpadeGame.getCurrTurn());
//
//		SpadePlayer winPlayer = newSpadeGame.getTeams()
//				.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
//				.getPlayers().get(newSpadeGame.getTempWinner());
//		Card winnerCard = winPlayer.getPlayingCard();
//
//		Card startCard = newSpadeGame.getTeams()
//				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
//				.getPlayers().get(newSpadeGame.getStartTurn()).getPlayingCard();
//		// if they aren't on the same team they bot should try to beat current winners
//		// card
//		List<Card> cards = new ArrayList<Card>(currPlayer.getRemainingCards());
//
//		cardService.sortCards(cards);
//
//		List<Card> spades = cards.stream().filter(c -> c.getSuit() == CardSuit.SPADES).collect(Collectors.toList());
//		List<Card> clubs = cards.stream().filter(c -> c.getSuit() == CardSuit.CLUBS).collect(Collectors.toList());
//		List<Card> hearts = cards.stream().filter(c -> c.getSuit() == CardSuit.HEARTS).collect(Collectors.toList());
//		List<Card> diamonds = cards.stream().filter(c -> c.getSuit() == CardSuit.DIAMONDS).collect(Collectors.toList());
//
//		List<Card> cardsToMatch = cardsToMatchLeadingSuit(startCard, cards);
//		Card playingCard = null;
//		if (cardsToMatch.size()>0) {
//
//			if (currPlayer.getPlayingCard() == null) {
//
//				if (winnerCard.getSuit() == startCard.getSuit()) {
//
//					if (winPlayer.getTeam() == currPlayer.getTeam()) {
//						currPlayer.setPlayingCard(cardService.cardToUse(cardsToMatch, null));
//					} else {
//						currPlayer.setPlayingCard(cardService.cardToUse(cardsToMatch, winnerCard));
//					}
//				} else {
//
//					currPlayer.setPlayingCard(cardService.cardToUse(cardsToMatch, null));
//				}
//			}
//
//			else {
//				
//				if(startCard.getSuit()!=currPlayer.getPlayingCard().getSuit()) {
//					
//					
//				}
//			}
//		}
//
//		Card clubCardToUse = null;
//		Card heartCardToUse = null;
//		Card diamondCardToUse = null;
//
//		if (currPlayer.getTeam() != winPlayer.getTeam()) {
//
//			switch (winPlayer.getPlayingCard().getSuit()) {
//
//			case SPADES:
//				playingCard = cardService.cardToUse(spades, winnerCard);
//				if (playingCard != null) {
//
//					currPlayer.setPlayingCard(playingCard);
//					return;
//
//				}
//
//				break;
//			case HEARTS:
//				playingCard = cardService.cardToUse(hearts, spades, winnerCard);
//				if (playingCard != null) {
//					currPlayer.setPlayingCard(playingCard);
//					return;
//				}
//				clubCardToUse = cardService.cardToUse(clubs, null);
//				diamondCardToUse = cardService.cardToUse(diamonds, null);
//
//				currPlayer.setPlayingCard(cardService.smallestCard(clubCardToUse, diamondCardToUse));
//
//				return;
//			case DIAMONDS:
//				playingCard = cardService.cardToUse(diamonds, spades, winnerCard);
//				if (playingCard != null) {
//					currPlayer.setPlayingCard(playingCard);
//					return;
//				}
//				clubCardToUse = cardService.cardToUse(clubs, null);
//				heartCardToUse = cardService.cardToUse(hearts, null);
//
//				currPlayer.setPlayingCard(cardService.smallestCard(clubCardToUse, heartCardToUse));
//
//				return;
//			case CLUBS:
//				playingCard = cardService.cardToUse(clubs, spades, winnerCard);
//				if (playingCard != null) {
//					currPlayer.setPlayingCard(playingCard);
//					return;
//				}
//
//				heartCardToUse = cardService.cardToUse(hearts, null);
//				diamondCardToUse = cardService.cardToUse(diamonds, null);
//
//				currPlayer.setPlayingCard(cardService.smallestCard(heartCardToUse, diamondCardToUse));
//
//				return;
//
//			default:
//				break;
//
//			}
//		}

	}

	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getCurrTurn().getCode();

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(PlayerTable.getPlayer(code));

		if (newSpadeGame.getTempWinner() == null) {
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				currPlayer.setWon(true);
			}

			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams()
					.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer gameWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						gameWinner.setWon(true);
					}
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						gameWinner.setWon(true);
					}

					currPlayer.setWon(false);
				}
			}
		}

	}

	public void determinePlayerPoints(SpadeGame newSpadeGame) {

		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		spadePlayer.setWon(true);
		spadePlayer.setTurn(true);
		spadePlayer.setPlayerCurrentScore(spadePlayer.getPlayerCurrentScore() + POINTS_WON_PER_TRICK_BEFORE_OVERBID);

		newSpadeGame.setStartTurn(spadePlayer.getName());

	}

	
}
