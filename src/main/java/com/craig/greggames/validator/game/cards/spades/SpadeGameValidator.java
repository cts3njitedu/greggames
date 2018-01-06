package com.craig.greggames.validator.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(1)
public class SpadeGameValidator implements AbstractSpadeValidator {

	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.PLAY);
		notificationSet.add(SpadeNotifications.CREATE);
		notificationSet.add(SpadeNotifications.START);
		notificationSet.add(SpadeNotifications.BID);

	}
	@Override
	public boolean validate(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		if(spadeGame.getGameModifier()==null) {
			return false;
		}
		if(spadeGame.getCurrTurn()==null) {
			
			return false;
		}
		return true;
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		if(notificationSet.contains(spadeNotification)) {
			return true;
		}
		return false;
	}

}
