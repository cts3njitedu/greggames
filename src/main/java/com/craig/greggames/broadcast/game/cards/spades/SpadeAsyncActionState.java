package com.craig.greggames.broadcast.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.service.cards.spades.SpadeService;

@Service
@Order(4)
public class SpadeAsyncActionState implements SpadeAsyncStateProcess {
	
	@Autowired
	private SpadeService spadeService;
	
	private static final Logger logger = Logger.getLogger(SpadeAsyncActionState.class);
	
	private static final AsyncState ASYNC_STATE = AsyncState.ACTION;
	@Override
	public boolean isValidAsyncTask(AsyncState asyncState) {
		// TODO Auto-generated method stub
		return ASYNC_STATE==asyncState;
	}

	@Override
	public SpadeGame processAsyncTask(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		spadeGame.setServerPlaying(true);
		spadeGame.setGameModifier(spadeGame.getCurrTurn());
		spadeGame.setPlayerNotification(spadeGame.getGameNotification());
		logger.info("Exiting: " + getClass());
		return spadeService.playGame(spadeGame);
	
		
	}

}
