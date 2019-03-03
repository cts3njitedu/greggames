package com.craig.greggames.broadcast.game.cards.spades;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.ServerMessage;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.util.TimeHelper;

@Service
@Order(2)
public class SpadeAsyncProcessingState implements SpadeAsyncStateProcess {

	@Autowired
	private TimeHelper timeHelper;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	
	private static final Logger logger = Logger.getLogger(SpadeAsyncProcessingState.class);
	
	private static final AsyncState ASYNC_STATE = AsyncState.PROCESSING;
	
	
	@Value ("${spade.processTimer:15}")
	private int processTimer;
	@Override
	public boolean isValidAsyncTask(AsyncState asyncState) {
		// TODO Auto-generated method stub
		return ASYNC_STATE==asyncState;
	}

	@Override
	public SpadeGame processAsyncTask(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		logger.info("Start delaying task: " + LocalDateTime.now());
		timeHelper.delayTask(processTimer);
		logger.info("End delaying task: " + LocalDateTime.now());
		
		
		
		
		
		spadeGame = spadePersistenceDal.updateActionField(spadeGame, AsyncState.NEAR_ACTION);
		if(spadeGame==null) {
			return null;
		}
		
		SpadePlayer spadePlayer = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getCurrTurn());
		
		Map<ServerMessage, String> serverMessages = spadePlayer.getServerMessages();
		serverMessages.put(ServerMessage.TIME_IS_ALMOST_UP, ServerMessage.TIME_IS_ALMOST_UP.getError());
		spadePlayer.setDisplayServerMessage(true);
		simpMessagingTemplate.convertAndSend("/topic/spades/" + spadeGame.getGameId(), spadeGame);
		spadePlayer.setServerMessages(null);
		spadePlayer.setDisplayServerMessage(false);
//		Set<String> excludedFields = new HashSet<>();
//		excludeFieldsUtility.excludeBotAndUserIdAndActivePlayers(spadeGame,excludedFields);
//		spadePersistenceDal.updateGame(spadeGame, excludedFields, new HashSet<>());
		logger.info("Exiting: " + getClass());
		return spadeGame;
		
	}

}
