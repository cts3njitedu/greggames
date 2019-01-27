package com.craig.greggames.handler.bot.game.cards.spades;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

@Service
public class SpadeBotPlayerPositionOne implements SpadeBotPlayerPosition {

	private final static int POSIITON = 1;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;

	@Override
	public boolean validatePosition(int position) {
		// TODO Auto-generated method stub
		return position==POSIITON;
	}

	@Override
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		SpadeTeam currPlayerTeam = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()));

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(spadeGame.getCurrTurn());

		SpadePlayer currPlayerTeamMate = cardHandler.findTeamMate(currPlayerTeam.getPlayers(), currPlayer);
		Map<TeamTable, SpadeTeam> allTeams = spadeGame.getTeams();
		SpadeTeam otherTeam = allTeams.get(spadeTeamHandler.getOtherTeam(allTeams, currPlayerTeam.getName()));
		boolean hasOtherPlayerBidNil = false;
		
		for(Entry<PlayerTable, SpadePlayer> player: otherTeam.getPlayers().entrySet()) {
			if(player.getValue().isBidNil()) {
				hasOtherPlayerBidNil = true;
				break;
			}
		}
		
		Set<Card> spades = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.SPADES);
		Set<Card> clubs = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.CLUBS);
		Set<Card> hearts = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.HEARTS);
		Set<Card> diamonds = spadeGameMetaData.getCurrPlayerRemainingCards().get(CardSuit.DIAMONDS);

		Map<CardSuit, Card> lowestCardsPlayed = new HashMap<>();
		for (CardSuit suit :spadeGameMetaData.getAllPlayedCards().keySet() ) {
			lowestCardsPlayed.put(suit,
					cardHandler.findHighestSmallestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}

		lowestCardsPlayed.remove(null);
		Map<CardSuit, Card> highestCardsPlayed = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getAllPlayedCards().keySet()) {
			highestCardsPlayed.put(suit,
					cardHandler.findSmallestLargestCardContinous(spadeGameMetaData.getAllPlayedCards().get(suit)));
		}

		highestCardsPlayed.remove(null);
		Map<CardSuit, Card> lowestRemainingCards = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getCurrPlayerRemainingCards().keySet()) {
			lowestRemainingCards.put(suit,
					cardHandler.findSmallestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}
		lowestRemainingCards.remove(null);
		Map<CardSuit, Card> highestRemainingCards = new HashMap<>();

		for (CardSuit suit : spadeGameMetaData.getCurrPlayerRemainingCards().keySet()) {
			highestRemainingCards.put(suit,
					cardHandler.findLargestCard(spadeGameMetaData.getCurrPlayerRemainingCards().get(suit)));
		}
		highestCardsPlayed.remove(null);

		boolean canPlaySpades = (hearts.size()==0 && clubs.size()==0&&diamonds.size()==0) || spadeGame.isSpadePlayed();
		
		boolean hasOnlySpades = hearts.size()==0 && clubs.size()==0&&diamonds.size()==0;
		if (currPlayer.isBidNil()) {

			if(canPlaySpades) {
				return cardHandler.findSmallestCard(lowestRemainingCards.values());
			}
			return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
			
		} else if (currPlayerTeamMate.isBidNil()) {
			if(canPlaySpades) {
				return cardHandler.findLargestCard(highestRemainingCards.values());
			}
			

			return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
		} 
		else if(hasOtherPlayerBidNil) {
			if(canPlaySpades) {
				return cardHandler.findSmallestCard(lowestRemainingCards.values());
			}
			return cardHandler.findSmallestCard(cardHandler.filterOutSpades(lowestRemainingCards.values()));
		}
		else {

			if(canPlaySpades) {
				return cardHandler.findLargestCard(highestRemainingCards.values());	
			}
			else {
				return cardHandler.findLargestCard(cardHandler.filterOutSpades(highestRemainingCards.values()));
			}
		}
		
	}

}
