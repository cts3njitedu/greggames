package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpadeBroadCasterEnricher extends AbstractSpadeGameEnricher {
	
	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.BID);
		notificationSet.add(SpadeNotifications.PLAY);
		
		
	

	}
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		spadeGameBroadCaster.removeGame(spadeGame);
		
		
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
