package com.craig.greggames.model;

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
		
		if(code==0) {
			code = 2;
		}
		else {
			code =1;
		}
		for(TeamTable team: TeamTable.values()) {
			
			if(team.getCode()==code) {
				
				return team;
			}
		}
		return null;
	}
	
	public static TeamTable getTeamByName(int code) {
		
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
