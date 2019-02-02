package com.craig.greggames.postprocessor.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;
import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class NewTimerTaskPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		spadeGameBroadCaster.removeGameFromScheduler(spadeGame);
		spadeGameBroadCaster.addGameToScheduler(spadeGame);
		if(spadeGame.isServingPlaying()) {
			simpMessagingTemplate.convertAndSend("/topic/spades/"+spadeGame.getGameId(),spadeGame);
		}
		return spadeGame;
	}
	
}
