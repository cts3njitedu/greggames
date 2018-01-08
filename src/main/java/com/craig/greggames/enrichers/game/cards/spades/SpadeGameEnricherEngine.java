package com.craig.greggames.enrichers.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.GreggameEnricherEngine;
import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
@Service
public class SpadeGameEnricherEngine implements GreggameEnricherEngine<SpadeGame>{

	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	
	@Autowired
	private List<SpadeGameEnricher> spadeEnrichers;
	
	@Override
	public void enricherEngine(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		for(SpadeGameEnricher spadeEnricher: spadeEnrichers) {
			
			if(spadeEnricher.validateState(spadeGame.getGameNotification())) {
				
				spadeEnricher.enricher(spadeGame);
			}
		}
		
	}

	@Override
	public GregGameChildTypes getGregType() {
		// TODO Auto-generated method stub
		return greggame;
	}

}
