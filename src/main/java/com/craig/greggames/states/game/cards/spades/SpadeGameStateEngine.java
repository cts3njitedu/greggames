package com.craig.greggames.states.game.cards.spades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.states.GreggameStateEngine;

@Service
public class SpadeGameStateEngine implements GreggameStateEngine<SpadeGame>{

	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	private static final Logger logger = Logger.getLogger(SpadeGameStateEngine.class);
	@Autowired
	private List<SpadeGameState> abstractSpadeGameStates;
	@Override
	public SpadeGame machine(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " +getClass());
		for(SpadeGameState abstractSpadeGameState: abstractSpadeGameStates) {
			
			if(abstractSpadeGameState.validateState(spadeGame.getPlayerNotification())) {
				
				return abstractSpadeGameState.state(spadeGame);
			
			}
		}
		logger.info("Exiting " + getClass());
		return null;
		
	}

	@Override
	public GregGameChildTypes gregGameChildTypes() {
		// TODO Auto-generated method stub
		return greggame;
	}
	
	
}
