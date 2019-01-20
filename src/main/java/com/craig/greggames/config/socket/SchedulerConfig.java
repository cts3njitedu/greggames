package com.craig.greggames.config.socket;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.craig.greggames.model.game.cards.spades.SpadeGame;



//@EnableScheduling
//@Configuration
public class SchedulerConfig {

	
	@Autowired
	private SimpMessagingTemplate template;
	@SendTo("/topic/ping")
	@Scheduled(fixedRate=3000)
	public void sendAdhockMessages() {
		SpadeGame spadeGame = new SpadeGame();
		spadeGame.setActivePlayers(4);
		System.out.println("Exiting");
		template.convertAndSend("/topic/ping","Sugaaar Honey ice tesa" + LocalDate.now().toEpochDay());
	}
}
