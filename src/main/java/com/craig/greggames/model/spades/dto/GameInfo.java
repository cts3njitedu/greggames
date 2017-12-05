package com.craig.greggames.model.spades.dto;

import com.craig.greggames.model.player.PlayerTable;

public class GameInfo {
	
	
	private String gameId;
	

	private boolean isBidding;

	private int pointsToWin;
	
	private PlayerTable startHand;
	private PlayerTable startTurn;

	private PlayerTable currTurn;
	private int trickCount;

	private int turnCount;

	private int handCount;
	private boolean isGameOver;
	
	private boolean isSpadePlayed;
	
	private boolean isDealing;
	private int numberDeals;
	private int bags;

	private int bagPoints;

	private int bidNilPoints;

	private int bidNil;

	private int numberOfPlayers;

	private int numberOfTeams;

	private int pointsToLose;

	private PlayerTable tempWinner;
	

}
