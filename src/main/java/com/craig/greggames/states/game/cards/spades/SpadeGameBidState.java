package com.craig.greggames.states.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(3)
public class SpadeGameBidState extends AbstractSpadeGameState {

	
	@Autowired
	private SpadeBotHandler spadeBotHandler;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	
	@Autowired
	private SpadeGameHandler spadeGameHandler;
	private final SpadeNotifications spadeNotification = SpadeNotifications.BID;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		if(spadeGame.getPlayerNotification()==spadeGame.getGameNotification()) {
			spadeGameHandler.bid(spadeGame);
		}
		else {
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			spadePlayerHandler.addError(spadeGame, SpadeErrors.CURRENTLY_BIDDING, oldSpadeGame);	
		}
		
		return spadeGame;

	}

	

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
