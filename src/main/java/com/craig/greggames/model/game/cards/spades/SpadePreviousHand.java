package com.craig.greggames.model.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;

import com.craig.greggames.model.game.cards.team.TeamTable;

public class SpadePreviousHand {
	
	private Map<TeamTable, SpadeTeam> teams;
	
	private int numberOfTeams;
	
	private int handCount;
	
	
	public Map<TeamTable, SpadeTeam> getTeams() {
		if (teams == null || teams.size()==0) {

			teams = new EnumMap<TeamTable, SpadeTeam>(TeamTable.class);

			for (int code = 1; code <= numberOfTeams; code++) {

				teams.put(TeamTable.getTeam(code), new SpadeTeam());
			}

		}
		return teams;
	}

	public void setTeams(Map<TeamTable, SpadeTeam> teams) {
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
