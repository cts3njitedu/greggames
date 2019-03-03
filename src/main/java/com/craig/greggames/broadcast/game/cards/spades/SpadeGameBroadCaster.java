package com.craig.greggames.broadcast.game.cards.spades;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
@Service
public class SpadeGameBroadCaster {
	

	private static final Logger logger = Logger.getLogger(SpadeGameBroadCaster.class);
	
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private SpadeAsyncTask spadeAsyncTask;
	
	@Value ("${spade.setTimer:false}")
	private boolean setTimer;
	
	@Value ("${spade.scheduleTimer:1000}")
	private long scheduleTimer;
	@PostConstruct
	private void initiateTimers() {
	
		if(setTimer) {
			logger.info("Initiating timers....");

			threadPoolTaskScheduler.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					logger.info("Processing valid spade games....");
					List<SpadeGame> spadeGames = spadePersistenceDal.findGamesByGameNotification(Arrays.asList(SpadeNotifications.PLAY,SpadeNotifications.BID));
					
					spadeGames.forEach(spadeGame->spadeAsyncTask.performSpadeBot(spadeGame));
					
					
				}
			},scheduleTimer);
			
		}
		
	}
	
	
	@PreDestroy
	private void resetAsyncState() {
		
		
	}


}
