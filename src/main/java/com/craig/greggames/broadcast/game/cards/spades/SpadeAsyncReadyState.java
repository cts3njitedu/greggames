package com.craig.greggames.broadcast.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.AsyncState;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(1)
public class SpadeAsyncReadyState implements SpadeAsyncStateProcess {


	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	
	private static final Logger logger = Logger.getLogger(SpadeAsyncReadyState.class);
	
	private static final AsyncState ASYNC_STATE = AsyncState.READY;
	@Override
	public boolean isValidAsyncTask(AsyncState asyncState) {
		// TODO Auto-generated method stub
		return ASYNC_STATE==asyncState;
	}

	@Override
	public SpadeGame processAsyncTask(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering: " + getClass());
		
		logger.info("Exiting: " + getClass());
		return spadePersistenceDal.updateActionField(spadeGame, AsyncState.PROCESSING);
		
	}

}
