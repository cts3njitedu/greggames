package com.craig.greggames.model.team;

public enum TeamTable {

	TEAM1(1),
	TEAM2(2),
	TEAM3(3),
	TEAM4(4);
	
	private int code;

	private TeamTable(int code) {
		this.code = code;
	}
	
	
	public static TeamTable getTeam(int code) {
		
		for(TeamTable team: TeamTable.values()) {
			
			if(team.getCode()==code) {
				return team;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}
	
	
	
}
