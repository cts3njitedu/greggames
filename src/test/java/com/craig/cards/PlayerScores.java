package com.craig.cards;

public class PlayerScores {

	private int p1_bid;
	private int p1_score;

	private int p2_bid;
	private int p2_score;
	private int p3_bid;
	private int p3_score;
	private int p4_bid;
	private int p4_score;

	private int team1_exp;
	private int team2_exp;

	public int getP1_bid() {
		return p1_bid;
	}

	public void setP1_bid(int p1_bid) {
		this.p1_bid = p1_bid;
	}

	public int getP1_score() {
		return p1_score;
	}

	public void setP1_score(int p1_score) {
		this.p1_score = p1_score;
	}

	public int getP2_bid() {
		return p2_bid;
	}

	public void setP2_bid(int p2_bid) {
		this.p2_bid = p2_bid;
	}

	public int getP2_score() {
		return p2_score;
	}

	public void setP2_score(int p2_score) {
		this.p2_score = p2_score;
	}

	public int getP3_bid() {
		return p3_bid;
	}

	public void setP3_bid(int p3_bid) {
		this.p3_bid = p3_bid;
	}

	public int getP3_score() {
		return p3_score;
	}

	public void setP3_score(int p3_score) {
		this.p3_score = p3_score;
	}

	public int getP4_bid() {
		return p4_bid;
	}

	public void setP4_bid(int p4_bid) {
		this.p4_bid = p4_bid;
	}

	public int getP4_score() {
		return p4_score;
	}

	public void setP4_score(int p4_score) {
		this.p4_score = p4_score;
	}

	public int getTeam1_exp() {
		return team1_exp;
	}

	public void setTeam1_exp(int team1_exp) {
		this.team1_exp = team1_exp;
	}

	public int getTeam2_exp() {
		return team2_exp;
	}

	public void setTeam2_exp(int team2_exp) {
		this.team2_exp = team2_exp;
	}

	@Override
	public String toString() {
		return "PlayerScores [p1_bid=" + p1_bid + ", p1_score=" + p1_score + ", p2_bid=" + p2_bid + ", p2_score="
				+ p2_score + ", p3_bid=" + p3_bid + ", p3_score=" + p3_score + ", p4_bid=" + p4_bid + ", p4_score="
				+ p4_score + "]";
	}

}
