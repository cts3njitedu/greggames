package com.craig.greggames.model.game.cards.spades;

import com.craig.greggames.model.game.cards.player.Player;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(Include.NON_NULL)
public class SpadePlayer extends Player {

	private int playerBid;
	private int playerCurrentScore;
	private int playerFinalScore;
	private boolean isFailBid;
	private boolean isOverBook;
	private int playerBags;
	private boolean isBidding;
	private boolean isBidNil;
	private boolean isBot;
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
		return isBidding;
	}

	public void setBidding(boolean isBidding) {
		this.isBidding = isBidding;
	}

	public int getPlayerFinalScore() {
		return playerFinalScore;
	}

	public void setPlayerFinalScore(int playerFinalScore) {
		this.playerFinalScore = playerFinalScore;
	}

	public boolean isFailBid() {
		return isFailBid;
	}

	public void setFailBid(boolean isFailBid) {
		this.isFailBid = isFailBid;
	}

	public boolean isOverBook() {
		return isOverBook;
	}

	public void setOverBook(boolean isOverBook) {
		this.isOverBook = isOverBook;
	}

	public boolean isBidNil() {
		return isBidNil;
	}

	public void setBidNil(boolean isBidNil) {
		this.isBidNil = isBidNil;
	}

	public boolean isBot() {
		return (getUserId()==null||getUserId().length()==0)?true:false;
	}

	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}
	
	

	
}
