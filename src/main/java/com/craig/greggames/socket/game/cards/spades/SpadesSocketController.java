package com.craig.greggames.socket.game.cards.spades;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameAbridged;
import com.craig.greggames.model.game.cards.spades.SpadeGameView;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.postprocessor.game.cards.spades.TrickPostProcessor;
import com.craig.greggames.response.game.cards.spades.SpadeResponseBuilder;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.util.GregMapper;
//@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class SpadesSocketController {
	
	@Autowired
	private SpadeService spadeService;
	
	@Autowired
	private SpadeResponseBuilder spadeResponseBuilder;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	private static final Logger logger = Logger.getLogger(SpadesSocketController.class);
	
	@Autowired
	private GregMapper gregMapper;
	
	@Autowired
	private WebSocketMessageBrokerStats webSocketMessageBrokerStats;
	@MessageMapping("/greggames/{ggType}")
    @SendTo("/topic/{ggType}")
    public SpadeGameView createGame(@DestinationVariable String ggType, @RequestBody SpadeGame spadeGame) throws Exception {
 
		
         return spadeResponseBuilder.getSpadeGameView(spadeService.playGame(spadeGame));
    }
	
	
	@MessageMapping("/greggames/{ggType}/{gameId}")
    @SendTo("/topic/{ggType}/{gameId}")
    public void modifyGameState(@DestinationVariable String ggType, @DestinationVariable String gameId, SpadeGame spadeGame) throws Exception, GreggamesException {
		logger.info("Stats: " + gregMapper.convertObjectToString(webSocketMessageBrokerStats));
//		SpadeGame spadeGame = spadePersistenceDal.findGame(spadeGameAbridged.getGameId());
		spadeGame.setServerPlaying(false);
		spadeService.playGame(spadeGame);
//		SpadeGame newSpadeGame = new SpadeGame();
//		newSpadeGame.setGameId(spadeGame.getGameId());
//		return newSpadeGame;
			
		
    }
	
	
}
