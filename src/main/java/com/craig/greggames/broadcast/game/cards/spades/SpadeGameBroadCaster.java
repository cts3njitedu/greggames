package com.craig.greggames.broadcast.game.cards.spades;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.util.GregMapper;
@Service
public class SpadeGameBroadCaster {
	
	
	private static ConcurrentHashMap<String, Timer> GAME_TIMERS=new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, TimerTask> GAME_TIMER_TASKS=new ConcurrentHashMap<>();
	
	private static int TIME_INTERVAL = 1000;

	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	
	@Autowired
	private GregMapper gregMapper;
	
	public void addGame(SpadeGame spadeGame) {
		createGameTimer(spadeGame, TIME_INTERVAL);
	}
	
	@PostConstruct
	private void initiateTimers() {
		List<SpadeGame> spadeGames = spadePersistenceDal.getGames();
		spadeGames.stream().forEach(spadeGame->createGameTimer(spadeGame, TIME_INTERVAL));
	}
	
	public boolean removeGame(SpadeGame spadeGame) {
		Timer timer = GAME_TIMERS.get(spadeGame.getGameId());
		TimerTask task = GAME_TIMER_TASKS.get(spadeGame.getGameId());
		if(timer!=null && task!=null) {
			timer.cancel();
			timer.purge();
			task.cancel();
			GAME_TIMERS.remove(spadeGame.getGameId());
			GAME_TIMER_TASKS.remove(spadeGame.getGameId());
			
			return true;
		}
		return false;
	}
	
	public boolean gameExists(SpadeGame spadeGame) {
		return GAME_TIMERS.containsKey(spadeGame.getGameId()) && GAME_TIMER_TASKS.containsKey(spadeGame.getGameId());
	}
	
	private void createGameTimer(SpadeGame spadeGame, int timeDelay) {

		
		String objectString = gregMapper.convertObjectToString(spadeGame);
		Timer timer = new Timer(spadeGame.getGameId());
		TimerTask task = new TimerTask() {
			private long maxTime = spadeGame.getMaxTime();
			private SpadeGame timeGame = (SpadeGame) gregMapper.convertStringToObject(objectString, SpadeGame.class);
			@Override
			public void run() {
				maxTime-=TIME_INTERVAL;
				if(maxTime<=0) {
					timer.cancel();
				}
				timeGame = spadePersistenceDal.findGame(timeGame.getGameId());
				System.out.println(timeGame.getGameId() + ":"+maxTime);
				timeGame.setMaxTime(maxTime);
				spadePersistenceDal.saveGame(timeGame);
				simpMessagingTemplate.convertAndSend("/topic/spades/"+timeGame.getGameId(),timeGame);
			}
		};
	
		
		timer.scheduleAtFixedRate(task, 10*TIME_INTERVAL, TIME_INTERVAL);
	
		GAME_TIMER_TASKS.put(spadeGame.getGameId(), task);
		GAME_TIMERS.put(spadeGame.getGameId(), timer);
	
		
	}

}
