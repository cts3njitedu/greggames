package com.craig.greggames.executor.game.cards.spades;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.cleaners.games.cards.spades.SpadeGameCleanerEngine;
import com.craig.greggames.enrichers.game.cards.spades.SpadeGameEnricherEngine;
import com.craig.greggames.executor.game.cards.CardGameExecutor;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.postprocessor.game.cards.spades.PostProcessorExecutor;
import com.craig.greggames.preprocessor.game.cards.spades.PreProcessorExecutor;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;
import com.craig.greggames.util.GregMapper;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {


	private static final Logger logger = Logger.getLogger(SpadeGameExecutor.class);

	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadeGameEnricherEngine enricherEngine;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private PreProcessorExecutor preProcessorExecutor;
	
	@Autowired
	private PostProcessorExecutor postProcessorExecutor;
	
	@Autowired
	private SpadeGameCleanerEngine spadeGameCleanerEngine;
	
	@Autowired
	private GregMapper gregMapper;

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Spade Game State Start: "+ spadeGame.getGameId()+ "Player Action: "+ spadeGame.getPlayerNotification());
	
		logger.info("Spade Game: " + gregMapper.convertObjectToString((spadeGame)));
		
		
		spadeGameCleanerEngine.cleanse(spadeGame);
		
		SpadeGame newSpadeGame = preProcessorExecutor.preProcess(spadeGame);
		
		if(newSpadeGame == null) {
			return spadePersistenceDal.findGame(spadeGame.getGameId());
		}
		
	
		enricherEngine.enricherEngine(newSpadeGame);
		
		boolean isValid = validatorEngine.validate(newSpadeGame);
		
		if(!isValid) {
			logger.info("Invalid request for : " + newSpadeGame.getGameModifier());
			newSpadeGame.setPlayerNotification(SpadeNotifications.CLIENT_ERROR);
		}
		
		logger.info("Player notification after validation: " + newSpadeGame.getPlayerNotification());
		stateEngine.machine(newSpadeGame);
		
	
		
		return postProcessorExecutor.postProcess(newSpadeGame);
		

	}


}
