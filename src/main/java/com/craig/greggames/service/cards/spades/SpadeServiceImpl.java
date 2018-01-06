package com.craig.greggames.service.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.states.GreggameStateFactory;

@Service
public class SpadeServiceImpl implements SpadeService {

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private GreggameStateFactory<SpadeGame> greggameStateFactory;

	@Override
	public List<SpadeGame> getGames() {
		// TODO Auto-generated method stub
		return spadePersistenceDal.getGames();
	}

	@Override
	public SpadeGame findGame(String gameId) {
		// TODO Auto-generated method stub
		return spadePersistenceDal.findGame(gameId);
	}

	@Override
	public SpadeGame findGameHeaderInfo(String gameId) {
		// TODO Auto-generated method stub
		return spadePersistenceDal.findGameHeaderInfo(gameId);
	}

	
	@Override
	public SpadeGame playGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return greggameStateFactory.executeMachine(spadeGame);

	}


}
