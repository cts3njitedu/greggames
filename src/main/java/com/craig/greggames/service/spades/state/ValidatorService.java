package com.craig.greggames.service.spades.state;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;

@Service
public class ValidatorService {

	@Autowired
	private SpadeTeamService teamService;

	public boolean validate(SpadeGame spadeGame) {
		
		SpadePlayer player = spadeGame.getTeams().get(teamService
				.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).
				getPlayers().get(spadeGame.getCurrTurn());
		
		SpadePlayer leadPlayer = spadeGame.getTeams().get(teamService
				.getTeamByPlayer(spadeGame.getStartTurn(), spadeGame.getNumberOfTeams())).
				getPlayers().get(spadeGame.getStartTurn());
	
		Card card = player.getPlayingCard();
		Card startCard = leadPlayer.getPlayingCard();
		if(card.getSuit()==CardSuit.SPADES) {
			
			if(!spadeGame.isSpadePlayed()) {
				
				return false;
			}
		}
		List<Card>cards = player.getRemainingCards();
		
		List<Card>cardsToMatch = cardsToMatchSuit(startCard.getSuit(), cards);
		
		if(cardsToMatch.size()>0) {
			
			if(card.getSuit()!=startCard.getSuit()) {
				
				return false;
			}
			
		}
		
		
		return true;
		
		
	}

	public List<Card> cardsToMatchSuit(CardSuit cardSuit, List<Card> cards) {

		switch (cardSuit) {

		case SPADES:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.SPADES).collect(Collectors.toList());

		case HEARTS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.HEARTS).collect(Collectors.toList());

		case DIAMONDS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.DIAMONDS).collect(Collectors.toList());

		case CLUBS:

			return cards.stream().filter(c -> c.getSuit() == CardSuit.CLUBS).collect(Collectors.toList());

		default:
			break;
		}
		return null;
	}
}
