package com.craig.greggames.model.game.cards.spades;

import java.util.Set;
import java.util.Map;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerTable;

public class SpadeGameMetaData {
	
	Map<CardSuit,Set<Card>> teamMatePlayedCards;
	Map<CardSuit,Set<Card>> currPlayerPlayedCards;
	Map<CardSuit,Set<Card>> currPlayerRemainingCards;
	Map<CardSuit,Set<Card>> allPlayedCards;
	Map<PlayerTable,Card> currPlayingCards;
	public Map<CardSuit, Set<Card>> getTeamMatePlayedCards() {
		return teamMatePlayedCards;
	}
	public void setTeamMatePlayedCards(Map<CardSuit, Set<Card>> teamMatePlayedCards) {
		this.teamMatePlayedCards = teamMatePlayedCards;
	}
	public Map<CardSuit, Set<Card>> getCurrPlayerPlayedCards() {
		return currPlayerPlayedCards;
	}
	public void setCurrPlayerPlayedCards(Map<CardSuit, Set<Card>> currPlayerPlayedCards) {
		this.currPlayerPlayedCards = currPlayerPlayedCards;
	}
	public Map<CardSuit, Set<Card>> getCurrPlayerRemainingCards() {
		return currPlayerRemainingCards;
	}
	public void setCurrPlayerRemainingCards(Map<CardSuit, Set<Card>> currPlayerRemainingCards) {
		this.currPlayerRemainingCards = currPlayerRemainingCards;
	}
	public Map<CardSuit, Set<Card>> getAllPlayedCards() {
		return allPlayedCards;
	}
	public void setAllPlayedCards(Map<CardSuit, Set<Card>> allPlayedCards) {
		this.allPlayedCards = allPlayedCards;
	}
	public Map<PlayerTable, Card> getCurrPlayingCards() {
		return currPlayingCards;
	}
	public void setCurrPlayingCards(Map<PlayerTable, Card> currPlayingCards) {
		this.currPlayingCards = currPlayingCards;
	}
	
	
	

}
