package com.craig.greggames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.craig.greggames.model.SpadeGame;
import com.craig.greggames.service.message.MessageService;
//@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	
//	@MessageMapping("/greggames/{ggType}/{gameId}")
//    @SendTo("/topic/{ggType}/{gameId}")
//    public Greeting greeting(@DestinationVariable String ggType, @DestinationVariable String gameId, HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        System.out.println(ggType+":"+gameId);
//        return new Greeting("Hello, " + ggType+ "!");
//    }

	@MessageMapping("/greggames/{ggType}")
    @SendTo("/topic/{ggType}")
    public List<SpadeGame> addGame(@DestinationVariable String ggType, SpadeGame spadeGame) throws Exception {
        //Thread.sleep(1000); // simulated delay
		//System.out.println(spadeGame.toString());
         return messageService.addGame(spadeGame);
    }
}
