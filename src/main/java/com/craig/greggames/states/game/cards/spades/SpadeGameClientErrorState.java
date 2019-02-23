package com.craig.greggames.states.game.cards.spades;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.error.game.cards.spades.SpadePlayerMessage;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
@Order(9)
public class SpadeGameClientErrorState extends AbstractSpadeGameState {

	
	@Autowired
	private SpadeGameHandler spadeGameHandler;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	private final SpadeNotifications spadeNotification = SpadeNotifications.CLIENT_ERROR;
	
	private static final Logger logger = Logger.getLogger(SpadeGameClientErrorState.class);

	@Value ("${spade.maxClientError:4}")
	private long maxClientError;
	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		SpadePlayerMessage spadePlayerMessage = new SpadePlayerMessage();
		spadePlayerMessage.setError(true);
		SpadePlayer spadePlayer = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
		.getPlayers().get(spadeGame.getGameModifier());
		spadePlayerMessage.setErrorMessages(spadePlayer.getErrorMessages());
		spadePlayerMessage.setPlayer(spadePlayer.getName());
		spadePlayerMessage.setNotification(spadeGame.getPlayerNotification());
		simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId() + "/" + spadeGame.getGameModifier(), spadePlayerMessage);
		
		try {
			TimeUnit.SECONDS.sleep(2);
			spadePlayer.getErrorMessages().values().clear();
			simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId() + "/" + spadeGame.getGameModifier(), new SpadePlayerMessage());
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Exiting: " + getClass());
		return spadeGame;
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
