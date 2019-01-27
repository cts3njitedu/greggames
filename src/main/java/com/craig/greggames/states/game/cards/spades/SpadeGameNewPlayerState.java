package com.craig.greggames.states.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
@Order(1)
public class SpadeGameNewPlayerState extends AbstractSpadeGameState {



	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;

	private final SpadeNotifications spadeNotification = SpadeNotifications.NEW_PLAYER;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		System.out.println("New Player");
		spadeBotHandler.determineBots(spadeGame);
		spadeGame.setNewPlayer(false);
		return spadePersistenceDal.saveGame(spadeGame);
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
