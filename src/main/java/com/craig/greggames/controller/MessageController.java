package com.craig.greggames.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.craig.greggames.model.Greeting;
import com.craig.greggames.model.HelloMessage;
//@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class MessageController {
	
	@MessageMapping("/greggames/{ggType}/{gameId}")
    @SendTo("/topic/{ggtype}/{gameId}")
    public Greeting greeting(@DestinationVariable String ggType,@DestinationVariable String gameId, HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(ggType+ ":"+ gameId);
        return new Greeting("Hello, " + ggType+" "+gameId + "!");
    }

}
