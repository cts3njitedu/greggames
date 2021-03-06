package com.craig.greggames.validator.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(1)
public class SpadeGameValidator extends AbstractSpadeValidator {

	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.PLAY);
		notificationSet.add(SpadeNotifications.BID);

	}
	
	private static final Logger logger = Logger.getLogger(SpadeGameValidator.class);
	@Override
	public boolean validate(SpadeGame spadeGame) {
		logger.info("Entering " + getClass());
		if(!notificationSet.contains(spadeGame.getPlayerNotification())) {
			return true;
		}
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
		return notificationSet.contains(spadeNotification);
	}

}
