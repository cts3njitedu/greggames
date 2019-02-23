package com.craig.greggames.broadcast.game.cards.spades;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.service.cards.spades.SpadeService;
@Service
public class SpadeGameBroadCaster {
	
	
	private static ConcurrentHashMap<String, ScheduledFuture<?>> scheduleFutureMap=new ConcurrentHashMap<>();
	
	
	private static int TIME_INTERVAL = 1000;

	private static final Logger logger = Logger.getLogger(SpadeGameBroadCaster.class);
	
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private TaskCreator taskCreator;
	
	@Value ("${spade.setTimer:false}")
	private boolean setTimer;
	
	@Autowired
	private SpadeService spadeService;
	
	@PostConstruct
	private void initiateTimers() {
	
		logger.info("Initiating timers....");
//		List<SpadeGame> spadeGames = spadePersistenceDal.getGames();
//		spadeGames.stream().filter(spadeGame->!SpadeNotifications.CREATE.equals(spadeGame.getPlayerNotification())).forEach(spadeGame->addGameToScheduler(spadeGame));
	}
	
	
	public void removeGameFromScheduler(SpadeGame spadeGame) {
		scheduleFutureMap.remove(spadeGame.getGameId());
	}
	

	
	public boolean cancelGameScheduler(SpadeGame spadeGame) {
		logger.info("Cancelling future for game id: " + spadeGame.getGameId());
		ScheduledFuture<?> scheduledFuture = scheduleFutureMap.get(spadeGame.getGameId());
		if(scheduledFuture!=null) {
			if(scheduledFuture.isDone()) {
				return false;
			}
			else {
				scheduledFuture.cancel(false);
			}
		}
		return false;
	}
	public void addGameToScheduler(SpadeGame spadeGame) {
		
		if(setTimer) {
			logger.info("Adding game for game id " + spadeGame.getGameId());
			ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(taskCreator.createTask(spadeGame), 
					Date.from(LocalDateTime.now().plusSeconds(10)
		            .atZone(ZoneId.systemDefault()).toInstant()),TIME_INTERVAL);
			scheduleFutureMap.put(spadeGame.getGameId(), scheduledFuture);
		}
		
		
	}
	public void addTrickGameToScheduler(SpadeGame spadeGame) {
		
		logger.info("Adding trick game for game id " + spadeGame.getGameId());
		ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(taskCreator.createTrickTask(spadeGame), 
				Date.from(LocalDateTime.now()
	            .atZone(ZoneId.systemDefault()).toInstant().plusSeconds(spadeGame.getMaxTime())),TIME_INTERVAL);

		scheduleFutureMap.put(spadeGame.getGameId(), scheduledFuture);
	}
	
	public void addHandGameToScheduler(SpadeGame spadeGame) {
		
		logger.info("Adding hand game for game id " + spadeGame.getGameId());
		ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(taskCreator.createHandTask(spadeGame), 
				Date.from(LocalDateTime.now()
	            .atZone(ZoneId.systemDefault()).toInstant()));
		scheduleFutureMap.put(spadeGame.getGameId(), scheduledFuture);
	}

}
