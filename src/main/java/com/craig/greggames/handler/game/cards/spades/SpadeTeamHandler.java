package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.TWO_TEAMS;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeTeamHandler {

	private Logger logger = Logger.getLogger(SpadeTeamHandler.class);
	public void makeTeams(SpadeGame newSpadeGame) {

		logger.info("Making Teams...");
		for (PlayerTable player : PlayerTable.values()) {

			TeamTable team = getTeamByPlayer(player, newSpadeGame.getNumberOfTeams());

			SpadePlayer spadePlayer = new SpadePlayer();
			spadePlayer.setTeam(team);
			spadePlayer.setName(player);
			spadePlayer.setBidding(true);
			spadePlayer.setBot(true);
			newSpadeGame.getTeams().get(team).getPlayers().put(player, spadePlayer);
			newSpadeGame.getTeams().get(team).setName(team);
		}

		logger.info("Teams: " +  newSpadeGame.getTeams());
	}

	public TeamTable getTeamByPlayer(PlayerTable player, int numberOfTeams) {
		logger.info("Player: "+ player + " Number of teams: " + numberOfTeams );
		if(numberOfTeams==TWO_TEAMS) {
			if(player.getCode()%TWO_TEAMS==0) {
				return TeamTable.TEAM2;
			}
			else {
				return TeamTable.TEAM1;
			}
		}
		else{
			
			return TeamTable.getTeam(player.getCode());
		}
		
	}
	public void determineTeamPoints(SpadeGame newSpadeGame) {

		logger.info("Determing team points...");
		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {
			
			int totalSuccessfulBid = 0;
			int totalFailedBid = 0;
			int totalOverBookScore = 0;
			int totalBagScore = 0;
			int totalBidNilScore = 0;
			int totalRoundBagScore = 0;
			int totalPreviousScore = entry.getValue().getTotalScore();
			int totalPreviousBags = entry.getValue().getTotalBags();
			int totalRoundScore = 0;
		
			logger.info("Total Bid is: " + entry.getValue().getTotalBid() + " for team "+ entry.getKey());
			int totalTricks = 0;
			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {
				if (entryPlayer.getValue().getPlayerBid() == 0) {

					if (entryPlayer.getValue().getPlayerCurrentScore() != 0) {
						totalBidNilScore -= newSpadeGame.getBidNilPoints();
					} else {

						totalBidNilScore += newSpadeGame.getBidNilPoints();
					}

				}
				totalTricks += entryPlayer.getValue().getPlayerCurrentScore();

				logger.info("Total bags for " + entryPlayer.getKey() + " is "+ totalTricks);
			}
			
			int totalRoundBags = totalTricks - entry.getValue().getTotalBid();
			if(totalRoundBags==0) {
				
				totalSuccessfulBid = entry.getValue().getTotalBid();
				
				entry.getValue().setTotalRoundScore(totalSuccessfulBid);
				
				
			}
			else if(totalRoundBags>0) {
				
				totalSuccessfulBid = entry.getValue().getTotalBid();
				totalRoundBagScore = totalRoundBags/POINTS_WON_PER_TRICK_BEFORE_OVERBID;
				
				totalBagScore = totalRoundBags + entry.getValue().getTotalBags();
				
				if(totalBagScore>=newSpadeGame.getBags()) {
					
					totalOverBookScore -= newSpadeGame.getBagPoints();
					totalBagScore = totalBagScore-newSpadeGame.getBags();
				}
			}
			else {
				
				totalFailedBid -= entry.getValue().getTotalBid();
				totalRoundBags=0;
				
			}

		
			
			totalRoundScore = totalSuccessfulBid + totalBidNilScore + totalFailedBid+totalRoundBagScore+totalOverBookScore;
			
			entry.getValue().setTotalBags(totalBagScore);
			entry.getValue().setTotalPreviousBags(totalPreviousBags);
			entry.getValue().setTotalBidNilScore(totalBidNilScore);
			entry.getValue().setTotalFailedBids(totalFailedBid);
			entry.getValue().setTotalSuccessfulBids(totalSuccessfulBid);
			entry.getValue().setTotalRoundBags(totalRoundBags);
			entry.getValue().setTotalPreviousScore(totalPreviousScore);
			entry.getValue().setTotalOverBookScore(totalOverBookScore);
			entry.getValue().setTotalRoundScore(totalRoundScore);
			entry.getValue().setTotalTricks(totalTricks);
			entry.getValue().setTotalScore(totalPreviousScore+totalRoundScore);
			
			logger.info("Total bags: " + totalBagScore + ", Total Previous Bags: "+ totalPreviousBags + ", Total Bid Nil Score: " 
			+ totalBidNilScore+ ", Total Failed Bid: "+ totalFailedBid+ ", Total Successful Bid: "+ totalSuccessfulBid
			+ ", Total Round Bags: " + totalRoundBags + " Total Previous Score: " + totalPreviousScore 
			+ ", Total Over Book Score: " + totalOverBookScore + ", Total Round Score: "+ totalRoundScore
			+ ", Total Tricks: " + totalTricks + ", Total Score: "+ entry.getValue().getTotalScore()+ " for " + 
			entry.getKey());
			
		}

	}

	public void determineGameWinner(SpadeGame newSpadeGame) {

		// List<SpadeTeam> spadeTeams = new ArrayList<SpadeTeam>();

		logger.info("Determing game winner...");
		SpadeTeam maxScoreTeam = newSpadeGame.getTeams().values().stream()
				.max(Comparator.comparing(SpadeTeam::getTotalScore)).get();
		SpadeTeam minScoreTeam = newSpadeGame.getTeams().values().stream()
				.min(Comparator.comparing(SpadeTeam::getTotalScore)).get();

		List<SpadeTeam> winnerTeams = new ArrayList<SpadeTeam>();
		for (SpadeTeam team : newSpadeGame.getTeams().values()) {
			logger.info("Team: " + team.getName() + ", Score: "+ team.getTotalScore());
			if (team.getTotalScore() == maxScoreTeam.getTotalScore()) {

				winnerTeams.add(team);
			}
		}

		//what if there is a tie?
		if (maxScoreTeam.getTotalScore() >= newSpadeGame.getPointsToWin()) {
			
			for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

				if (entry.getValue().getTotalScore() == maxScoreTeam.getTotalScore()) {
					logger.info(entry.getKey() + " has won game with score of " + entry.getValue().getTotalScore() );
					entry.getValue().setWon(true);
				} else {
					entry.getValue().setWon(false);
				}
			}
			newSpadeGame.setGameOver(true);

		}

		else {
			
			if(minScoreTeam.getTotalScore()<=newSpadeGame.getPointsToLose()) {
				
				for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

					if (entry.getValue().getTotalScore() == maxScoreTeam.getTotalScore()) {
						logger.info(entry.getKey() + " has won game with score of " + entry.getValue().getTotalScore());
						entry.getValue().setWon(true);
					} else {
						entry.getValue().setWon(false);
					}
				}
				newSpadeGame.setGameOver(true);
			}
			

		}

	}

	public void cleanUpPoints(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers()
					.get(player);

			spadePlayer.setPlayerCurrentScore(0);
			spadePlayer.setPlayerBid(0);

		}

	}
	
	public int getTrickPlayerPosition(PlayerTable leadPlayer, PlayerTable currPlayer) {
		int playerCode = leadPlayer.getCode();
		
		int currPlayerCode = currPlayer.getCode();
		
		if(currPlayerCode>=playerCode) {
			return (currPlayerCode-playerCode) +1;
		}
		else {
			return ((MAX_TURN_PER_TRICK+currPlayerCode) - playerCode) + 1;
		}
	}
	
	public TeamTable getOtherTeam(Map<TeamTable,SpadeTeam>teams, TeamTable teamTable) {
		
		for(Entry<TeamTable, SpadeTeam> team: teams.entrySet()) {
			if(team.getKey()!=teamTable) {
				return team.getKey();
			}
		}
		
		return null;
	}

}
