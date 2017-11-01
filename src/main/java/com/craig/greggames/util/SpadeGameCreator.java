package com.craig.greggames.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.controller.spades.model.Team;
import com.craig.greggames.model.Card;
import com.craig.greggames.model.CardSuit;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;

public class SpadeGameCreator {

	private Random rand = new Random();

	public SpadeGame execute(SpadeGame spadeGame) {
		SpadeGame newSpadeGame = spadeGame;
		playGame(newSpadeGame);
		return newSpadeGame;

	}

	private void playGame(SpadeGame newSpadeGame) {
		
		
		if (newSpadeGame.isStarting()) {
			
			makeTeams(newSpadeGame);
			int start = rand.nextInt(4)+1;
			newSpadeGame.setBidding(true);
			newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
			newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setHandCount(1);
			distributeCards(newSpadeGame);
			newSpadeGame.setStarting(false);
			newSpadeGame.setTurnCount(1);
			
			
		}
		
		else if(newSpadeGame.isBidding()) {
			
			
			//check the turn count. if 4 set bidding to false;
			
			if(newSpadeGame.getTurnCount()==4) {
				
				newSpadeGame.setCurrTurn(newSpadeGame.getStartTurn());
				newSpadeGame.setBidding(false);
				
				newSpadeGame.setTurnCount(1);
				newSpadeGame.setTrickCount(1);
			}
			else {
				
				int currTurnCode = newSpadeGame.getCurrTurn().getCode() + 1;
				
				if(currTurnCode>4) {
					currTurnCode = currTurnCode - 4;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount()+1);
			}
		
			
		}
		else {
			
			if(newSpadeGame.getTurnCount()==4) {
				
				//determine who won the trick
				SpadePlayer spadePlayer = determinePlayerWinner(newSpadeGame);
				//determine the amount of points the winner has
				determinePlayerPoints(newSpadeGame,spadePlayer);
				//reAdjust cards. should return boolean
				boolean isCardsLeft = reAdjustCards(newSpadeGame);
				
				if(!isCardsLeft) {
					
					//determine the total team score;
					determineTeamPoints(newSpadeGame);
					//determine if someone won the game;
					determineGameWinner(newSpadeGame);
					int start = newSpadeGame.getStartHand().getCode()+1;
					if(start>4) {
						start = start - 4;
					}
					newSpadeGame.setBidding(true);
					newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
					newSpadeGame.setStartTurn(newSpadeGame.getStartHand());
					newSpadeGame.setCurrTurn(newSpadeGame.getStartHand());
					newSpadeGame.setHandCount(1);
					distributeCards(newSpadeGame);
					newSpadeGame.setHandCount(newSpadeGame.getHandCount());
					newSpadeGame.setTrickCount(0);
					newSpadeGame.setTurnCount(0);
					
				}
				else {
					
					newSpadeGame.setCurrTurn(spadePlayer.getName());
					newSpadeGame.setStartTurn(spadePlayer.getName());
					newSpadeGame.setTurnCount(0);
					newSpadeGame.setTrickCount(newSpadeGame.getTrickCount());
					
				}
			}
			
		}
		
		
		
	}



	private void makeTeams(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			TeamTable team = TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams());

			SpadePlayer spadePlayer = new SpadePlayer();
			spadePlayer.setTeam(team);
			spadePlayer.setName(player);
			spadePlayer.setBidding(true);

			newSpadeGame.getTeams().get(team).getPlayers().put(player, spadePlayer);
		}

	}

	private void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode()+1;
		if(dealCode>4) {
			dealCode = dealCode - 4;
		}
		
		for(int turn=1; turn<=4; turn++) {
			
			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(dealCode, newSpadeGame.getNumberOfTeams()))
			.getPlayers().get(PlayerTable.getPlayer(dealCode)).getRemainingCards().addAll(cards.subList(i, i + 13));
			dealCode++;
			if(dealCode>4) {
				dealCode=dealCode-4;
			}
			i=i+13;
		}
		

	}

	public SpadePlayer determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getStartTurn().getCode();
		

		SpadePlayer gameWinner = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(code, newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(PlayerTable.getPlayer(code));

		for (int i = 1; i <= 3; i++) {

			code++;
			if (code == 5) {
				code = 1;
			}
			SpadePlayer currPlayer = newSpadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(code, newSpadeGame.getNumberOfTeams())).getPlayers()
					.get(PlayerTable.getPlayer(code));
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

		return gameWinner;
	}
	
	private void determinePlayerPoints(SpadeGame newSpadeGame, SpadePlayer gameWinner) {

		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()
					.get(PlayerTable.getPlayer(player.getCode()));

			if (spadePlayer.getName() == gameWinner.getName()) {

				spadePlayer.setWon(true);
				spadePlayer.setTurn(true);
				newSpadeGame.setStartTurn(spadePlayer.getName());

			} else {
				spadePlayer.setWon(false);
				spadePlayer.setTurn(false);

			}
		}

	}

	private void determineTeamPoints(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, Team> entry : newSpadeGame.getTeams().entrySet()) {
			int teamScore = 0;
			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				teamScore = teamScore + entryPlayer.getValue().getPlayerCurrentScore();
			}

			int overBookOne = teamScore - entry.getValue().getTotalBid();
			if (overBookOne > 0) {
				int totalBags = overBookOne + entry.getValue().getBags();
				if (totalBags >= newSpadeGame.getBags()) {

					teamScore = teamScore - newSpadeGame.getOverBook();
					newSpadeGame.getTeams().get(entry.getKey()).setOverBook(true);
					newSpadeGame.getTeams().get(entry.getKey()).setBags(totalBags - newSpadeGame.getBags());
					newSpadeGame.getTeams().get(entry.getKey())
							.setTotalScore(newSpadeGame.getTeams().get(entry.getKey()).getTotalScore() + teamScore);
				}
			}
		}

	}

	

	private void determineGameWinner(SpadeGame newSpadeGame) {

		int maxScore = 0;
		boolean isEqual = false;
		TeamTable maxTeam = null;
		for (Entry<TeamTable, Team> entry : newSpadeGame.getTeams().entrySet()) {

			if (entry.getValue().getTotalScore() == maxScore) {

				if (maxScore >= newSpadeGame.getPointsToWin()) {
					isEqual = true;
				}
			} else {
				if (entry.getValue().getTotalScore() > maxScore) {
					maxScore = entry.getValue().getTotalScore();
					maxTeam = entry.getKey();
				}
			}

		}
		if (newSpadeGame.getTeams().get(maxTeam).getTotalScore() >= newSpadeGame.getPointsToWin()) {

			if (!isEqual) {

				newSpadeGame.getTeams().get(maxTeam).setWon(true);
				newSpadeGame.setGameOver(true);
			}

		}

	}

	private boolean reAdjustCards(SpadeGame newSpadeGame) {

		int finishCount = 0;
		for (PlayerTable player : PlayerTable.values()) {

			Card oldPlayingCard = newSpadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()

					.get(player).getPlayingCard();

			List<Card> cardsLeft = newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).getRemainingCards();
			
			if(cardsLeft.size()==1) {
				
				finishCount++;
			}
			else {
				cardsLeft.remove(oldPlayingCard);
			}
			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).setPlayingCard(null);

		}
		
		if(finishCount==4) {
			return false;
		}
		return true;

	}

}
