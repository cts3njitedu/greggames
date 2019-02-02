package com.craig.greggames.broadcast.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.craig.greggames.model.game.cards.CardGame;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.util.GregMapper;



@Component
@Scope("prototype")
public class TaskCreator {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private GregMapper gregMapper;
	
	@Autowired
	private SpadeService spadeService;
	
	public Runnable createTask(SpadeGame spadeGame) {
		return new Runnable() {
			private long time = spadeGame.getMaxTime(); 
			private SpadeGame timeGame = (SpadeGame) gregMapper.convertStringToObject(gregMapper.convertObjectToString(spadeGame), SpadeGame.class);
			@Override
			public void run() {
				// TODO Auto-generated method stub
				time--;
				timeGame = spadePersistenceDal.findGame(timeGame.getGameId());
				System.out.println(timeGame.getGameId() + ":"+time);
				timeGame.setMaxTime(time);
				spadePersistenceDal.saveGame(timeGame);
				simpMessagingTemplate.convertAndSend("/topic/spades/"+timeGame.getGameId(),timeGame);
				
				if(time==0) {
					timeGame.setServingPlaying(true);
					timeGame = spadeService.playGame(timeGame);
				}
			}
		};
	}
}
