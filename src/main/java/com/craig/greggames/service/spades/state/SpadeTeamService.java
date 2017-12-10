package com.craig.greggames.service.spades.state;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static com.craig.greggames.constants.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;
import com.craig.greggames.model.team.TeamTable;

@Service
public class SpadeTeamService {

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
			int teamScore = 0;
			int teamTotalScore = 0;
			int bidNilPoints = 0;
			Map<PlayerTable, Boolean> isUnderBid = new HashMap<PlayerTable, Boolean>();

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {
				int books = entryPlayer.getValue().getPlayerCurrentScore() - entryPlayer.getValue().getPlayerBid();
				if (entryPlayer.getValue().getPlayerBid() == 0) {

					if (entryPlayer.getValue().getPlayerCurrentScore() != 0) {
						bidNilPoints -= newSpadeGame.getBidNilPoints();
					} else {

						bidNilPoints += newSpadeGame.getBidNilPoints();
					}

					isUnderBid.put(entryPlayer.getKey(), false);
				} else {

					if (books < 0) {

						isUnderBid.put(entryPlayer.getKey(), true);
					} else {

						isUnderBid.put(entryPlayer.getKey(), false);
					}

				}
				teamScore += entryPlayer.getValue().getPlayerCurrentScore();

			}

			int teamTotalBags = (teamScore - entry.getValue().getTotalBid()) + entry.getValue().getBags();
			if (teamScore < entry.getValue().getTotalBid()) {
				for (Entry<PlayerTable, Boolean> playerTable : isUnderBid.entrySet()) {

					if (playerTable.getValue()) {
						teamTotalScore -= entry.getValue().getPlayers().get(playerTable.getKey()).getPlayerBid();
					} else {

						teamTotalScore += entry.getValue().getPlayers().get(playerTable.getKey()).getPlayerBid();
					}
				}

			}

			else if (teamScore > entry.getValue().getTotalBid()) {

				teamTotalScore = entry.getValue().getTotalBid() + (teamScore - entry.getValue().getTotalBid()) / POINTS_WON_PER_TRICK_BEFORE_OVERBID;

				if (teamTotalBags >= newSpadeGame.getBags()) {
					teamTotalScore -= newSpadeGame.getBagPoints();
					newSpadeGame.getTeams().get(entry.getKey()).setOverBook(true);
					newSpadeGame.getTeams().get(entry.getKey()).setBags(teamTotalBags - newSpadeGame.getBags());

				} else {
					newSpadeGame.getTeams().get(entry.getKey()).setBags(teamTotalBags);

				}
			} else {

				teamTotalScore += entry.getValue().getTotalBid();
			}

			teamTotalScore += bidNilPoints;

			newSpadeGame.getTeams().get(entry.getKey())
					.setTotalScore(newSpadeGame.getTeams().get(entry.getKey()).getTotalScore() + teamTotalScore);

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
