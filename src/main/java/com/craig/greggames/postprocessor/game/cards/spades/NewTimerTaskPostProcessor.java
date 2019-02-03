package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.preprocessor.game.cards.spades.LockFieldPreProcessor;

@Service
public class NewTimerTaskPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private Set<SpadeNotifications> spadeNotifications = new HashSet<>(
			Arrays.asList(SpadeNotifications.BID, SpadeNotifications.PLAY, SpadeNotifications.START));

	private Logger logger = Logger.getLogger(NewTimerTaskPostProcessor.class);
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		if (spadeNotifications.contains(spadeGame.getPlayerNotification())) {
			logger.info("Valid state to set task");
			spadeGameBroadCaster.removeGameFromScheduler(spadeGame);
			spadeGameBroadCaster.addGameToScheduler(spadeGame);
			if (spadeGame.isServingPlaying()) {
				logger.info("Broadcasting message for "+spadeGame.getGameId());
				simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId(), spadeGame);
			}
		}

		return spadeGame;
	}

}
