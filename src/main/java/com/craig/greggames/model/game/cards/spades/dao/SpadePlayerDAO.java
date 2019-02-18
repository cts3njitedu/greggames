package com.craig.greggames.model.game.cards.spades.dao;

import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

public class SpadePlayerDAO extends PlayerDAO{

	private int playerBid;
	private int playerCurrentScore;
	private int playerFinalScore;
	private boolean failBid;
	private SpadeNotifications notification;
	private boolean overBook;
	private int playerBags;
	private boolean bidding;
	private boolean bidNil;
	
	private boolean bot;
	public int getPlayerBid() {
		return playerBid;
	}

	public void setPlayerBid(int playerBid) {
		this.playerBid = playerBid;
	}

	public int getPlayerCurrentScore() {
		return playerCurrentScore;
	}

	public void setPlayerCurrentScore(int playerCurrentScore) {
		this.playerCurrentScore = playerCurrentScore;
	}

	public int getPlayerBags() {
		return playerBags;
	}

	public void setPlayerBags(int playerBags) {
		this.playerBags = playerBags;
	}

	public boolean isBidding() {
		return bidding;
	}

	public void setBidding(boolean isBidding) {
		this.bidding = isBidding;
	}

	public int getPlayerFinalScore() {
		return playerFinalScore;
	}

	public void setPlayerFinalScore(int playerFinalScore) {
		this.playerFinalScore = playerFinalScore;
	}

	public boolean isFailBid() {
		return failBid;
	}

	public void setFailBid(boolean isFailBid) {
		this.failBid = isFailBid;
	}

	public boolean isOverBook() {
		return overBook;
	}

	public void setOverBook(boolean isOverBook) {
		this.overBook = isOverBook;
	}

	public boolean isBidNil() {
		return bidNil;
	}

	public void setBidNil(boolean isBidNil) {
		this.bidNil = isBidNil;
	}

	public boolean isBot() {
		return bot;
	}

	public void setBot(boolean isBot) {
		this.bot = isBot;
	}

	public SpadeNotifications getNotification() {
		return notification;
	}

	public void setNotification(SpadeNotifications notification) {
		this.notification = notification;
	}
	
	
	


}
