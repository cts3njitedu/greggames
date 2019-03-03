package com.craig.greggames.notification.games.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class NotificationService {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	


}
