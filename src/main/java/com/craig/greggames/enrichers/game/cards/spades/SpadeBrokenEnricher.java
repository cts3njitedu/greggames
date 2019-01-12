package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeBroken;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeBrokenEnricher extends AbstractSpadeGameEnricher {

	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.PLAY);
	
		
	

	}
	
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeBroken spadeBroken = spadeGame.getSpadeBroken();
		if(spadeBroken!=null) {
			
			spadeBroken.setFirstSpade(false);
		}

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
