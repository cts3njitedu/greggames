package com.craig.greggames.controller.game.cards.spades.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.service.cards.spades.SpadeService;
//@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class SpadesSocketController {
	
	@Autowired
	private SpadeService spadeService;


	@MessageMapping("/greggames/{ggType}")
    @SendTo("/topic/{ggType}")
    public SpadeGame createGame(@DestinationVariable String ggType, @RequestBody SpadeGame spadeGame) throws Exception {
 
         return spadeService.createGame(spadeGame);
    }
	
	
	@MessageMapping("/greggames/{ggType}/{gameId}")
    @SendTo("/topic/{ggType}/{gameId}")
    public SpadeGame modifyGameState(@DestinationVariable String ggType, @DestinationVariable String gameId, SpadeGame spadeGame) throws Exception {
       
         return spadeService.modifyGameState(ggType, gameId, spadeGame);
    }
	
	
}
