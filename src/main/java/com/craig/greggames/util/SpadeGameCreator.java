package com.craig.greggames.util;

import java.util.List;
import java.util.Map;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.controller.spades.model.Team;
import com.craig.greggames.model.Card;
import com.craig.greggames.model.CardSuit;
import com.craig.greggames.model.Player;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;

public class SpadeGameCreator {

	public SpadeGame execute(SpadeGame spadeGame) {
		SpadeGame newSpadeGame = spadeGame;

		if (newSpadeGame.isStarting()) {

			distributeCards(newSpadeGame);
			newSpadeGame.setStarting(false);
		}
		if (newSpadeGame.getHandCount() != 0) {

			if (newSpadeGame.getCurrTrick() == newSpadeGame.getEndTrick()) {

				determinePlayerWinner(newSpadeGame);

			} else {

			}

			if (newSpadeGame.getTrickCount() == 13) {

				determineTeamPoints(newSpadeGame);
				
				determineGameWinner(newSpadeGame);

				distributeCards(newSpadeGame);
			}

		}
		
		updateHandAndTrick(newSpadeGame);
		return newSpadeGame;

	}

	private void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		Team one = newSpadeGame.getTeams().get(TeamTable.TEAM1);
	
		Map<PlayerTable, Player> onePlayers = one.getPlayers();

		SpadePlayer spadePlayer1 = (SpadePlayer) onePlayers.get(PlayerTable.PLAYER1);
		SpadePlayer spadePlayer3 = (SpadePlayer) onePlayers.get(PlayerTable.PLAYER3);

		spadePlayer1.getRemainingCards().addAll(cards.subList(0, 13));
		spadePlayer3.getRemainingCards().addAll(cards.subList(26, 39));

		onePlayers.put(PlayerTable.PLAYER1, spadePlayer1);
		onePlayers.put(PlayerTable.PLAYER3, spadePlayer3);

		Team two = newSpadeGame.getTeams().get(TeamTable.TEAM2);

		Map<PlayerTable, Player> twoPlayers = two.getPlayers();

		SpadePlayer spadePlayer2 = (SpadePlayer) twoPlayers.get(PlayerTable.PLAYER2);
		SpadePlayer spadePlayer4 = (SpadePlayer) twoPlayers.get(PlayerTable.PLAYER4);

		spadePlayer2.getRemainingCards().addAll(cards.subList(13, 26));
		spadePlayer4.getRemainingCards().addAll(cards.subList(39, 52));


	}

	public SpadePlayer determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getStartTrick().getCode();

		SpadePlayer gameWinner = getPlayerInfo(newSpadeGame, code);

		for (int i = 1; i <= 3; i++) {

			code++;
			if (code == 5) {
				code = 1;
			}
			SpadePlayer currPlayer = getPlayerInfo(newSpadeGame, code);
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {

				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {
					
					gameWinner = currPlayer;
					gameWinner.setWon(true);
				}

			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					gameWinner = currPlayer;
				}
			}

		}

		determinePlayerPoints(newSpadeGame, gameWinner);
		return gameWinner;
	}

	private void determineTeamPoints(SpadeGame newSpadeGame) {

		Team one = newSpadeGame.getTeams().get(TeamTable.TEAM1);

		Team two = newSpadeGame.getTeams().get(TeamTable.TEAM2);

		SpadePlayer spadePlayer1 = (SpadePlayer) one.getPlayers().get(PlayerTable.PLAYER1);
		SpadePlayer spadePlayer2 = (SpadePlayer) two.getPlayers().get(PlayerTable.PLAYER2);
		SpadePlayer spadePlayer3 = (SpadePlayer) one.getPlayers().get(PlayerTable.PLAYER3);
		SpadePlayer spadePlayer4 = (SpadePlayer) two.getPlayers().get(PlayerTable.PLAYER4);

		int teamOneHandScore = spadePlayer1.getPlayerCurrentScore() + spadePlayer3.getPlayerCurrentScore();

		int overBookOne = teamOneHandScore - one.getTotalBid();
		if (overBookOne > 0) {
			int totalBags = overBookOne + one.getBags();
			if (totalBags >= newSpadeGame.getBags()) {

				teamOneHandScore = teamOneHandScore - newSpadeGame.getOverBook();
				one.setOverBook(true);
				one.setBags(totalBags - newSpadeGame.getBags());
			}
		}

		int teamTwoHandScore = spadePlayer2.getPlayerCurrentScore() + spadePlayer4.getPlayerCurrentScore();

		int overBookTwo = teamTwoHandScore - one.getTotalBid();
		if (overBookTwo > 0) {
			int totalBags = overBookTwo + two.getBags();
			if (totalBags >= newSpadeGame.getBags()) {

				teamTwoHandScore = teamTwoHandScore - newSpadeGame.getOverBook();
				two.setOverBook(true);
				two.setBags(totalBags - newSpadeGame.getBags());
			}

		}
		one.setTotalScore(one.getTotalScore() + teamOneHandScore);

		two.setTotalScore(two.getTotalScore() + teamTwoHandScore);

	}

	private void determinePlayerPoints(SpadeGame newSpadeGame, SpadePlayer gameWinner) {

		Team one = newSpadeGame.getTeams().get(TeamTable.TEAM1);

		Team two = newSpadeGame.getTeams().get(TeamTable.TEAM2);
		
		for(PlayerTable player: PlayerTable.values()) {
			
			SpadePlayer spadePlayer = null;
			if(player.getCode()%2==0) {
				spadePlayer = (SpadePlayer) two.getPlayers().get(player);
				
			}
			else {
				spadePlayer = (SpadePlayer) one.getPlayers().get(player);
			}
			
			if(spadePlayer.getName()==gameWinner.getName()) {
				
				spadePlayer.setWon(true);
				spadePlayer.setTurn(true);
				newSpadeGame.setCurrTrick(spadePlayer.getName());
			}
			else {
				spadePlayer.setWon(false);
				spadePlayer.setTurn(false);
				
			}
		}

	}

	private SpadePlayer getPlayerInfo(SpadeGame newSpadeGame, int code) {

		SpadePlayer playerInfo = null;
		Team one = newSpadeGame.getTeams().get(TeamTable.TEAM1);

		Team two = newSpadeGame.getTeams().get(TeamTable.TEAM2);

		if (code % 2 == 0) {

			playerInfo = (SpadePlayer) two.getPlayers().get(PlayerTable.getPlayer(code));

		} else {

			playerInfo = (SpadePlayer) one.getPlayers().get(PlayerTable.getPlayer(code));
		}
		return playerInfo;

	}

	private void determineGameWinner(SpadeGame newSpadeGame) {

		Team one = newSpadeGame.getTeams().get(TeamTable.TEAM1);

		Team two = newSpadeGame.getTeams().get(TeamTable.TEAM2);

		if (one.getTotalScore() > two.getTotalScore()) {

			if (one.getTotalScore() >= newSpadeGame.getPointsToWin()) {

				one.setWon(true);
				two.setWon(false);
				newSpadeGame.setGameOver(true);
			}
		}
		if (two.getTotalScore() > one.getTotalScore()) {

			if (two.getTotalScore() >= newSpadeGame.getPointsToWin()) {

				two.setWon(true);
				one.setWon(false);
				newSpadeGame.setGameOver(true);
			}
		}

	}

	private void readjustCards(SpadeGame newSpadeGame) {
		
		
		
	}
	private void updateHandAndTrick(SpadeGame newSpadeGame) {
		
		
		
		if(newSpadeGame.getTrickCount()==13) {
			
			newSpadeGame.setTrickCount(0);
			newSpadeGame.setHandCount(newSpadeGame.getHandCount()+1);
			getStartHand(newSpadeGame);
			
			
			

		}
		else {
			newSpadeGame.setTrickCount(newSpadeGame.getTrickCount()+1);
		}
		
		
		
	}
	
	private void getStartTrick(SpadeGame newSpadeGame, boolean isNewHand) {
		int newStartCount = newSpadeGame.getStartHand().getCode();
		
		if(true) {
			
			newSpadeGame.setStartTrick(newSpadeGame.getStartHand());
			newSpadeGame.setCurrTrick(newSpadeGame.getStartHand());
			//newSpadeGame.set
			
		}
		
		
	}
	private void getStartHand(SpadeGame newSpadeGame){
		
		int playerCount = newSpadeGame.getStartHand().getCode();
		int newStartCount = ((playerCount+1)%4)+1;
		

		
		newSpadeGame.setStartHand(PlayerTable.getPlayer(newStartCount));
		
		
	
			
		
	}
}
