package com.craig.greggames.postprocessor.game.cards.spades;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;

@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class BroadcastPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private Set<SpadeNotifications> spadeNotifications = new HashSet<>(
			Arrays.asList(SpadeNotifications.BID, SpadeNotifications.PLAY, SpadeNotifications.START,
					SpadeNotifications.CREATE, SpadeNotifications.NEW_PLAYER,SpadeNotifications.LEAVE_GAME,
					SpadeNotifications.TRICK_OVER,SpadeNotifications.HAND_OVER,SpadeNotifications.GAME_OVER));

	private static final Logger logger = Logger.getLogger(BroadcastPostProcessor.class);
	
	private Set<SpadeNotifications> messageNotifications = new HashSet<>(Arrays.asList(
			SpadeNotifications.CLIENT_ERROR
	));

	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		if(spadeNotifications.contains(spadeGame.getPlayerNotification())) {
			simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId(), spadeGame);
		}
		logger.info("Exiting: " + getClass());
		return spadeGame;
	}

}
