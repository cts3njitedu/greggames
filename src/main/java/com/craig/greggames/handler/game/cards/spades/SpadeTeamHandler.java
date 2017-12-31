package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeTeamHandler {

	public void makeTeams(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			TeamTable team = getTeamByPlayer(player, newSpadeGame.getNumberOfTeams());

			SpadePlayer spadePlayer = new SpadePlayer();
			spadePlayer.setTeam(team);
			spadePlayer.setName(player);
			spadePlayer.setBidding(true);

			newSpadeGame.getTeams().get(team).getPlayers().put(player, spadePlayer);
			newSpadeGame.getTeams().get(team).setName(team);
		}

	}

	public TeamTable getTeamByPlayer(PlayerTable player, int numberOfTeams) {
		
		if(numberOfTeams==2) {
			if(player.getCode()%2==0) {
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
			
		}

	}

	public void determineGameWinner(SpadeGame newSpadeGame) {

		// List<SpadeTeam> spadeTeams = new ArrayList<SpadeTeam>();

		SpadeTeam maxScoreTeam = newSpadeGame.getTeams().values().stream()
				.max(Comparator.comparing(SpadeTeam::getTotalScore)).get();
		SpadeTeam minScoreTeam = newSpadeGame.getTeams().values().stream()
				.min(Comparator.comparing(SpadeTeam::getTotalScore)).get();

		List<SpadeTeam> winnerTeams = new ArrayList<SpadeTeam>();
		for (SpadeTeam team : newSpadeGame.getTeams().values()) {

			if (team.getTotalScore() == maxScoreTeam.getTotalScore()) {

				winnerTeams.add(team);
			}
		}

		if (maxScoreTeam.getTotalScore() >= newSpadeGame.getPointsToWin()) {

			for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

				if (entry.getValue().getTotalScore() == maxScoreTeam.getTotalScore()) {

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

}
