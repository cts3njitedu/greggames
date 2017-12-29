package com.craig.greggames.model.game.cards.spades;

public enum SpadeErrors {

	INVALID_SPADE("Spade Has Not Been Broken"),
	NOT_YOUR_TURN("It Is Not Your Turn"),
	INVALID_SUIT("Card Must Be Same Suit"),
	INVALID_PLAY("Currently Bidding"),
	NO_CARD_PLAYED("No Card Played");
	
	private String error;
	
	
	private SpadeErrors(String error) {
		this.error=error;
	}


	public String getError() {
		return error;
	}
	
	
}
