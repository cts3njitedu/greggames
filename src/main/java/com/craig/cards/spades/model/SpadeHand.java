package com.craig.cards.spades.model;

import java.util.ArrayList;
import java.util.List;

public class SpadeHand {

	private List<Trick> tricks;

	public List<Trick> getTricks() {
		if (tricks == null) {
			tricks = new ArrayList<Trick>();
		}
		return tricks;
	}

	public void setTricks(List<Trick> tricks) {
		this.tricks = tricks;
	}

}
