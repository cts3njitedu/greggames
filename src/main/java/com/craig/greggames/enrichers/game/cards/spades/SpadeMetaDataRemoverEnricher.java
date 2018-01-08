package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class SpadeMetaDataRemoverEnricher extends AbstractSpadeGameEnricher {

	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.BID);
		notificationSet.add(SpadeNotifications.PLAY);
		notificationSet.add(SpadeNotifications.START);
		notificationSet.add(SpadeNotifications.CREATE);

	}
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		spadeGame.setPreviousHand(null);
		spadeGame.setPreviousTrick(null);
		spadeGame.setTrickOver(false);
		spadeGame.setHandOver(false);
		spadeGame.setGameOver(false);
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
