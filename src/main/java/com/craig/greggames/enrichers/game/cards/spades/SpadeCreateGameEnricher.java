package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpadeCreateGameEnricher extends AbstractSpadeGameEnricher {

	
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.CREATE);

		
	

	}
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		spadeGame.setPlayerNotification(spadeGame.getGameNotification());
		
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
