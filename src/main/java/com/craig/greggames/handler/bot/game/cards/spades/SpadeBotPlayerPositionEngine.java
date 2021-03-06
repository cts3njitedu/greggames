package com.craig.greggames.handler.bot.game.cards.spades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;

@Service
public class SpadeBotPlayerPositionEngine {

	@Autowired
	private List<SpadeBotPlayerPosition> spadeBotPlayerPositions;
	
	private static final Logger logger = Logger.getLogger(SpadeBotPlayerPositionEngine.class);
	public Card botPlayerEngine(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame, int position) {
		
		logger.info("Entering " + getClass());
		for(SpadeBotPlayerPosition spadeBotPlayerPosition: spadeBotPlayerPositions) {
			if(spadeBotPlayerPosition.validatePosition(position)) {
				return spadeBotPlayerPosition.getCard(spadeGameMetaData, spadeGame);
			}
		}
		logger.info("Exiting " + getClass());
		return null;
	}
	
	
}
