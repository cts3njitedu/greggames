package com.craig.greggames.preprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
public class CancelTimerPreProcessor extends AbstractPreProcessor{

	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;
	
	private Set<SpadeNotifications> spadeNotifications=
			new HashSet<>(Arrays.asList(SpadeNotifications.START,SpadeNotifications.BID, SpadeNotifications.PLAY,SpadeNotifications.TRICK_OVER));
	
	private Logger logger = Logger.getLogger(CancelTimerPreProcessor.class);
	
	@Override
	boolean isValidState(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return spadeNotifications.contains(spadeGame.getPlayerNotification());
	}

	@Override
	SpadeGame preProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: "+getClass());
		spadeGameBroadCaster.cancelGameScheduler(spadeGame);
		logger.info("Exiting: "+getClass());
		return spadeGame;
	
	}

	
}
