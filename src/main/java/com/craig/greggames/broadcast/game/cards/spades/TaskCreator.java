package com.craig.greggames.broadcast.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.craig.greggames.cleaners.games.cards.spades.SpadeGameCleanerEngine;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.util.GregMapper;



@Component
//@Scope("prototype")
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
	
	private static Logger logger = Logger.getLogger(TaskCreator.class);
	
	public Runnable createTask(SpadeGame spadeGame) {
		return new Runnable() {
			private long time = spadeGame.getMaxTime(); 
			private SpadeGame timeGame = (SpadeGame) gregMapper.convertStringToObject(gregMapper.convertObjectToString(spadeGame), SpadeGame.class);
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				time--;
				timeGame = spadePersistenceDal.findGame(timeGame.getGameId());
				//System.out.println(timeGame.getGameId() + ":"+time);
				timeGame.setMaxTime(time);
				spadeGameCleanerEngine.cleanse(timeGame);
				spadePersistenceDal.saveGame(timeGame);
				if(time%10==0) {
					logger.info("Time for game " + timeGame.getGameId() + " is "+ time);
				}
				if(time%30==0 ||time<=10) {
					simpMessagingTemplate.convertAndSend("/topic/spades/"+timeGame.getGameId(),timeGame);
				}
				
				
				if(time==0) {
					logger.info("Time is up for player " + timeGame.getCurrTurn());
					timeGame.setServingPlaying(true);
					timeGame.setGameModifier(timeGame.getCurrTurn());
					timeGame.setPlayerNotification(timeGame.getGameNotification());
					timeGame = spadeService.playGame(timeGame);
				}
			}
		};
	}
}
