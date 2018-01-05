package com.craig.greggames.states.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.states.GreggameStateEngine;

public class SpadeGameStateEngine implements GreggameStateEngine<SpadeGame> {

	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	
	@Autowired
	private List<AbstractSpadeGameState> gameStates;
	@Override
	public void execute(SpadeGame greggame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GregGameChildTypes gregGameChildTypes() {
		// TODO Auto-generated method stub
		return greggame;
	}

	
	
}
