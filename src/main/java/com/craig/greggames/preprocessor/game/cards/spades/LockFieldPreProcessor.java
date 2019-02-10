package com.craig.greggames.preprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.executor.game.cards.spades.SpadeGameExecutor;
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
	private Logger logger = Logger.getLogger(LockFieldPreProcessor.class);
	
	@Override
	boolean isValidState(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return spadeNotifications.contains(spadeGame.getPlayerNotification());
	}

	@Override
	SpadeGame preProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		SpadeGame newSpadeGame = spadePersistenceDal.updateLockingField(spadeGame);
		
		logger.info("Value of spade game is: " + newSpadeGame);
		if(newSpadeGame!=null) {
			spadeGame.setLock(newSpadeGame.isLock());
		}
		else {
			spadeGame=null;
		}
		logger.info("Exiting " + getClass());
		return spadeGame;
	
	}

	
}
