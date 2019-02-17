package com.craig.greggames.response.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameView;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
public class SpadeResponseBuilder {
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	
	
	public SpadeGameView getSpadeGameView(SpadeGame spadeGame) {
		SpadeGameView spadeGameView = new SpadeGameView();
		spadeGameView.setSpadeGames(spadePersistenceDal.getGames());
		if(spadeGame!=null) {
			if(SpadeNotifications.CREATE==spadeGame.getPlayerNotification()) {
				spadeGameView.setNewGame(spadeGame);
				
			}
			
		}
		
		return spadeGameView;
		
	}
	
	

}
