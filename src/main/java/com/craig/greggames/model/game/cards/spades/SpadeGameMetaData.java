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
	
	Map<CardSuit, Card> highestCardPlayed;
	Map<CardSuit, Card> lowestCardPlayed;
	Map<CardSuit, Card> highestRemainingCard;
	Map<CardSuit, Card> lowestRemainingCard;
	
	boolean hasOnlySpades;
	boolean hasLeadPlayerSuit;
	boolean hasSpades;
	boolean canPlaySpades;
	
	SpadePlayer currPlayer;
	
	SpadePlayer currPlayerTeamMate;
	
	SpadePlayer leadPlayer;
	SpadeTeam leadingSpadeTeam;
	
	SpadePlayer otherPlayerBefore;
	SpadePlayer otherPlayerAfter;
	
	SpadeTeam currPlayerTeam;
	
	SpadePlayer currWinner;
	
	SpadeTeam otherTeam;
	
	int trickPlayerPosition;
	
	public SpadePlayer getCurrPlayer() {
		return currPlayer;
	}
	public void setCurrPlayer(SpadePlayer currPlayer) {
		this.currPlayer = currPlayer;
	}
	public SpadePlayer getCurrPlayerTeamMate() {
		return currPlayerTeamMate;
	}
	public void setCurrPlayerTeamMate(SpadePlayer currPlayerTeamMate) {
		this.currPlayerTeamMate = currPlayerTeamMate;
	}
	public SpadePlayer getLeadPlayer() {
		return leadPlayer;
	}
	public void setLeadPlayer(SpadePlayer leadPlayer) {
		this.leadPlayer = leadPlayer;
	}
	public SpadeTeam getLeadingSpadeTeam() {
		return leadingSpadeTeam;
	}
	public void setLeadingSpadeTeam(SpadeTeam leadingSpadeTeam) {
		this.leadingSpadeTeam = leadingSpadeTeam;
	}
	public SpadePlayer getOtherPlayerBefore() {
		return otherPlayerBefore;
	}
	public void setOtherPlayerBefore(SpadePlayer otherPlayerBefore) {
		this.otherPlayerBefore = otherPlayerBefore;
	}
	public SpadePlayer getOtherPlayerAfter() {
		return otherPlayerAfter;
	}
	public void setOtherPlayerAfter(SpadePlayer otherPlayerAfter) {
		this.otherPlayerAfter = otherPlayerAfter;
	}
	public SpadePlayer getCurrWinner() {
		return currWinner;
	}
	public void setCurrWinner(SpadePlayer currWinner) {
		this.currWinner = currWinner;
	}
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
	public Map<CardSuit, Card> getHighestCardPlayed() {
		return highestCardPlayed;
	}
	public void setHighestCardPlayed(Map<CardSuit, Card> highestCardPlayed) {
		this.highestCardPlayed = highestCardPlayed;
	}
	public Map<CardSuit, Card> getLowestCardPlayed() {
		return lowestCardPlayed;
	}
	public void setLowestCardPlayed(Map<CardSuit, Card> lowestCardPlayed) {
		this.lowestCardPlayed = lowestCardPlayed;
	}
	public Map<CardSuit, Card> getHighestRemainingCard() {
		return highestRemainingCard;
	}
	public void setHighestRemainingCard(Map<CardSuit, Card> highestRemainingCard) {
		this.highestRemainingCard = highestRemainingCard;
	}
	public Map<CardSuit, Card> getLowestRemainingCard() {
		return lowestRemainingCard;
	}
	public void setLowestRemainingCard(Map<CardSuit, Card> lowestRemainingCard) {
		this.lowestRemainingCard = lowestRemainingCard;
	}
	public boolean isHasOnlySpades() {
		return hasOnlySpades;
	}
	public void setHasOnlySpades(boolean hasOnlySpades) {
		this.hasOnlySpades = hasOnlySpades;
	}
	public boolean isHasLeadPlayerSuit() {
		return hasLeadPlayerSuit;
	}
	public void setHasLeadPlayerSuit(boolean hasLeadPlayerSuit) {
		this.hasLeadPlayerSuit = hasLeadPlayerSuit;
	}
	public boolean isHasSpades() {
		return hasSpades;
	}
	public void setHasSpades(boolean hasSpades) {
		this.hasSpades = hasSpades;
	}
	public boolean isCanPlaySpades() {
		return canPlaySpades;
	}
	public void setCanPlaySpades(boolean canPlaySpades) {
		this.canPlaySpades = canPlaySpades;
	}
	public SpadeTeam getCurrPlayerTeam() {
		return currPlayerTeam;
	}
	public void setCurrPlayerTeam(SpadeTeam currPlayerTeam) {
		this.currPlayerTeam = currPlayerTeam;
	}
	public SpadeTeam getOtherTeam() {
		return otherTeam;
	}
	public void setOtherTeam(SpadeTeam otherTeam) {
		this.otherTeam = otherTeam;
	}
	public int getTrickPlayerPosition() {
		return trickPlayerPosition;
	}
	public void setTrickPlayerPosition(int trickPlayerPosition) {
		this.trickPlayerPosition = trickPlayerPosition;
	}
	
	
	
	
	

}
