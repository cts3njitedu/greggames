package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
public class PostProcessorExecutor {

	@Autowired
	private List<AbstractPostProcessor> abstractPostProcessors;
	
	private Set<SpadeNotifications> messageNotifications = new HashSet<>(Arrays.asList(
			SpadeNotifications.CLIENT_ERROR
	));
	
	private static final Logger logger = Logger.getLogger(PostProcessorExecutor.class);
	
	public SpadeGame postProcess(SpadeGame spadeGame) {
		logger.info("Entering " + getClass());
		SpadeGame newSpadeGame = spadeGame;
		if(messageNotifications.contains(spadeGame.getPlayerNotification())) {
			logger.info("Doesn't contain correct notification: " + spadeGame.getPlayerNotification());
			return spadeGame;
		}
		for(AbstractPostProcessor abstractPostProcessor: abstractPostProcessors) {
			newSpadeGame = abstractPostProcessor.postProcess(newSpadeGame);
			
		}
		
		logger.info("Exiting "+ getClass());
		
		return newSpadeGame;
	}
}
