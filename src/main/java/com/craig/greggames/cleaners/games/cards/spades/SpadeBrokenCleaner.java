package com.craig.greggames.cleaners.games.cards.spades;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeBroken;
import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class SpadeBrokenCleaner extends AbstractSpadeGameCleaner {


	@Override
	public void clean(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeBroken spadeBroken = spadeGame.getSpadeBroken();
		if(spadeBroken!=null) {
			
			spadeBroken.setFirstSpade(false);
		}

	}

}
