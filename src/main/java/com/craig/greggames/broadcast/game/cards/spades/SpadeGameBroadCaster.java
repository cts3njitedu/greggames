package com.craig.greggames.broadcast.game.cards.spades;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
@Service
public class SpadeGameBroadCaster {
	
	
	private static ConcurrentHashMap<String, ScheduledFuture<?>> scheduleFutureMap=new ConcurrentHashMap<>();
	
	
	private static int TIME_INTERVAL = 1000;

	
	
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private TaskCreator taskCreator;
	

	
	@PostConstruct
	private void initiateTimers() {
	
		List<SpadeGame> spadeGames = spadePersistenceDal.getGames();
		spadeGames.stream().forEach(spadeGame->addGameToScheduler(spadeGame));
	}
	
	
	public void removeGameFromScheduler(SpadeGame spadeGame) {
		scheduleFutureMap.remove(spadeGame.getGameId());
	}
	

	
	public boolean cancelGameScheduler(SpadeGame spadeGame) {
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
		
		ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(taskCreator.createTask(spadeGame), 
				Date.from(LocalDateTime.now().plusSeconds(10)
	            .atZone(ZoneId.systemDefault()).toInstant()),TIME_INTERVAL);
		scheduleFutureMap.put(spadeGame.getGameId(), scheduledFuture);
		
	}

}
