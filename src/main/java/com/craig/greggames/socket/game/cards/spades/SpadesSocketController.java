package com.craig.greggames.socket.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.craig.greggames.exception.GreggamesException;
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
 
         return spadeService.playGame(spadeGame);
    }
	
	
	@MessageMapping("/greggames/{ggType}/{gameId}")
    @SendTo("/topic/{ggType}/{gameId}")
    public SpadeGame modifyGameState(@DestinationVariable String ggType, @DestinationVariable String gameId, SpadeGame spadeGame) throws Exception, GreggamesException {
       
         return spadeService.playGame(spadeGame);
    }
	
	
}
