package com.craig.greggames.states.game.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(1)
public class SpadeGameLeaveState extends AbstractSpadeGameState {



	@Autowired
	private SpadePersistenceDal spadePersistenceDal;

	
	@Autowired
	private SpadeGameHandler spadeGameHandler;

	private final SpadeNotifications spadeNotification = SpadeNotifications.LEAVE_GAME;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		spadeGameHandler.leaveGame(spadeGame);
		
		return spadePersistenceDal.saveGame(spadeGame);
	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		return spadeNotification == spadeNotifications;
	}

}
