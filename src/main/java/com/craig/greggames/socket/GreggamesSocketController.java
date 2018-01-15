package com.craig.greggames.socket;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.GreggamesPing;

@Controller
public class GreggamesSocketController {

	@MessageMapping("/greggames/{ggType}/ping")
	@SendTo("/topic/{ggType}/ping")
	public GreggamesPing createGame(@DestinationVariable String ggType, Map<String,Object>request) throws Exception {

		GreggamesPing greggamesPing = new GreggamesPing();
		greggamesPing.setMessage("Ping Successful");
		System.out.println(request.toString());
		greggamesPing.setGameChildTypes(GregGameChildTypes.valueOf(ggType.toUpperCase()));
		return greggamesPing;
	}
	
	@MessageMapping("/greggames/ping")
	@SendTo("/topic/ping")
	public GreggamesPing createGame2(Map<String,Object>request) throws Exception {

		GreggamesPing greggamesPing = new GreggamesPing();
		greggamesPing.setMessage("Ping Successful Game");
		System.out.println(request.toString()+" from Games");
		
		return greggamesPing;
	}
}

