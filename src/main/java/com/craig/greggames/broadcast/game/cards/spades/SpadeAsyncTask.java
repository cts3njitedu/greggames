package com.craig.greggames.broadcast.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class SpadeAsyncTask {

	@Autowired
	private SpadeAsyncTaskExecutor spadeAsyncTaskExecutor;
	
	
	private static final Logger logger = Logger.getLogger(SpadeAsyncTask.class);
	
	@Async("asyncTaskScheduler")
	public void performSpadeBot(SpadeGame spadeGame) {
		logger.info("Entering: " + getClass() + ":" + Thread.currentThread().getName());
		
		spadeAsyncTaskExecutor.execute(spadeGame);
	
		logger.info("Exiting: " + getClass() + ":" + Thread.currentThread().getName());
		//spadeService.playGame(spadeGame);
	}
}
