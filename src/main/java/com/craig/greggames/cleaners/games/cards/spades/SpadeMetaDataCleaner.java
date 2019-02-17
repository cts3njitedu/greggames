package com.craig.greggames.cleaners.games.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.game.cards.spades.AbstractSpadeGameEnricher;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeMetaDataCleaner extends AbstractSpadeGameCleaner {

	@Override
	public void clean(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		spadeGame.setPreviousHand(null);
		spadeGame.setPreviousTrick(null);
		spadeGame.setTrickOver(false);
		spadeGame.setHandOver(false);
		spadeGame.setGameOver(false);
		
	}

	

}
