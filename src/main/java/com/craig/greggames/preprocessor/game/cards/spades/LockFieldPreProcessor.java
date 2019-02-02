package com.craig.greggames.preprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LockFieldPreProcessor extends AbstractPreProcessor{

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	private Set<SpadeNotifications> spadeNotifications=
			new HashSet<>(Arrays.asList(SpadeNotifications.START,SpadeNotifications.BID, SpadeNotifications.PLAY));
	
	
	@Override
	boolean isValidState(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return spadeNotifications.contains(spadeGame.getPlayerNotification());
	}

	@Override
	SpadeGame preProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return spadePersistenceDal.updateLockingField(spadeGame);
	
	}

	
}
