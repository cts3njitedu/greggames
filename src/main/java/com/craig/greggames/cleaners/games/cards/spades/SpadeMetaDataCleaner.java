package com.craig.greggames.cleaners.games.cards.spades;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeMetaDataCleaner extends AbstractSpadeGameCleaner {

	@Override
	public void clean(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		if(spadeGame.getPlayerNotification()==SpadeNotifications.TRICK_OVER) {
			spadeGame.setPreviousTrick(null);
			spadeGame.setTrickOver(false);
		}
		else if(spadeGame.getPlayerNotification()==SpadeNotifications.HAND_OVER) {
			
			spadeGame.setPreviousHand(null);
			spadeGame.setHandOver(false);
			
		}
		else {
			
			
			spadeGame.setPreviousHand(null);
			spadeGame.setPreviousTrick(null);
			spadeGame.setTrickOver(false);
			spadeGame.setHandOver(false);
			spadeGame.setGameOver(false);
		}
		
		
	}

	

}
