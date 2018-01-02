package com.craig.greggames.model.game.cards.spades.dao;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.team.TeamTable;

public class SpadePreviousHandDAO {
	
	private Map<TeamTable, SpadeTeamDAO> teams;
	
	private int numberOfTeams;
	private int handCount;
	
	
	public Map<TeamTable, SpadeTeamDAO> getTeams() {
		if (teams == null || teams.size()==0) {

			teams = new EnumMap<TeamTable, SpadeTeamDAO>(TeamTable.class);

			for (int code = 1; code <= numberOfTeams; code++) {

				teams.put(TeamTable.getTeam(code), new SpadeTeamDAO());
			}

		}
		return teams;
	}

	public void setTeams(Map<TeamTable, SpadeTeamDAO> teams) {
		this.teams = teams;
	}

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(int numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	public int getHandCount() {
		return handCount;
	}

	public void setHandCount(int handCount) {
		this.handCount = handCount;
	}

	
	
}
