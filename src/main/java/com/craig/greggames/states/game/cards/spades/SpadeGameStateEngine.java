package com.craig.greggames.states.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.states.GreggameStateEngine;

@Service
public class SpadeGameStateEngine implements GreggameStateEngine<SpadeGame>{

	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	
	@Autowired
	private List<AbstractSpadeGameState> abstractSpadeGameStates;
	@Override
	public SpadeGame machine(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		for(AbstractSpadeGameState abstractSpadeGameState: abstractSpadeGameStates) {
			
			if(abstractSpadeGameState.validateState(spadeGame.getGameNotification())) {
				
				return abstractSpadeGameState.state(spadeGame);
			
			}
		}
		
		return null;
		
	}

	@Override
	public GregGameChildTypes gregGameChildTypes() {
		// TODO Auto-generated method stub
		return greggame;
	}
	
	
}
