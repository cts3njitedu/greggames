package com.craig.greggames.broadcast.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.craig.greggames.cleaners.games.cards.spades.SpadeGameCleanerEngine;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.postprocessor.game.cards.spades.PostProcessorExecutor;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.util.GregMapper;

@Component
// @Scope("prototype")
public class TaskCreator {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;

	@Autowired
	private SpadeGameCleanerEngine spadeGameCleanerEngine;

	@Autowired
	private GregMapper gregMapper;

	@Autowired
	private SpadeService spadeService;

	@Autowired
	private PostProcessorExecutor postProcessorExecutor;
	
	private static int TIME_INTERVAL = 1000;

	private static final Logger logger = Logger.getLogger(TaskCreator.class);

	public Runnable createTrickTask(SpadeGame spadeGame) {

		return new Runnable() {
			private long time = spadeGame.getMaxTime();
	
			private SpadeGame timeGame = (SpadeGame) gregMapper
					.convertStringToObject(gregMapper.convertObjectToString(spadeGame), SpadeGame.class);

			@Override
			public void run() {
				// TODO Auto-generated method stub
				logger.info("Starting trick task...");
				timeGame.setTrickOver(false);
				timeGame.setPlayerNotification(SpadeNotifications.TRICK_OVER);
				spadeService.playGame(timeGame);

			}
		};
	}

	public Runnable createHandTask(SpadeGame spadeGame) {

		return new Runnable() {
			private long time = spadeGame.getMaxTime();
			private SpadeGame timeGame = (SpadeGame) gregMapper
					.convertStringToObject(gregMapper.convertObjectToString(spadeGame), SpadeGame.class);

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(time * TIME_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				timeGame.setHandOver(false);
				timeGame.setPlayerNotification(SpadeNotifications.HAND_OVER);
				spadeService.playGame(timeGame);

			}
		};
	}

	public Runnable createTask(SpadeGame spadeGame) {
		return new Runnable() {
			private long time = spadeGame.getMaxTime();
			private SpadeGame timeGame = (SpadeGame) gregMapper
					.convertStringToObject(gregMapper.convertObjectToString(spadeGame), SpadeGame.class);

			@Override
			public void run() {
				// TODO Auto-generated method stub
				time--;
				timeGame = spadePersistenceDal.findGame(timeGame.getGameId());
				// System.out.println(timeGame.getGameId() + ":"+time);
				timeGame.setMaxTime(time);
				spadeGameCleanerEngine.cleanse(timeGame);
				spadePersistenceDal.saveGame(timeGame);
				if (time % 10 == 0) {
					logger.info("Time for game " + timeGame.getGameId() + " is " + time);
				}
				if (time % 30 == 0 || time <= 10) {
					simpMessagingTemplate.convertAndSend("/topic/spades/" + timeGame.getGameId(), timeGame);
				}

				if (time == 0) {
					logger.info("Time is up for player " + timeGame.getCurrTurn());
					timeGame.setServerPlaying(true);
					timeGame.setGameModifier(timeGame.getCurrTurn());
					timeGame.setPlayerNotification(timeGame.getGameNotification());
					timeGame = spadeService.playGame(timeGame);
				}
			}
		};
	}
}
