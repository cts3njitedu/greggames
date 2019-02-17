package com.craig.greggames.cleaners.games.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class SpadeGameCleanerEngine {

	
	@Autowired
	private List<AbstractSpadeGameCleaner> abstractSpadeGameCleaners;
	
	public void cleanse(SpadeGame spadeGame) {
		for(AbstractSpadeGameCleaner abstractSpadeGameCleaner: abstractSpadeGameCleaners) {
			abstractSpadeGameCleaner.clean(spadeGame);
		}
	}
}
