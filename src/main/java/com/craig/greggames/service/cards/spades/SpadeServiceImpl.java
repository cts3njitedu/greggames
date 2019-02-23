package com.craig.greggames.service.cards.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.executor.game.cards.spades.SpadeGameExecutor;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameView;
import com.craig.greggames.model.game.cards.spades.SpadePlayerView;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.response.game.cards.spades.SpadeResponseBuilder;

@Service
public class SpadeServiceImpl implements SpadeService {

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeGameExecutor spadeGameExecutor;
	
	

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
		return spadeGameExecutor.execute(spadeGame);

	}

	@Override
	public SpadeGameView getGameView() {
		// TODO Auto-generated method stub
		SpadeGameView spadeGameView = new SpadeGameView();
		List<SpadeGame> spadeGames = spadePersistenceDal.getGames();
		List<SpadeGame> newSpadeGames = new ArrayList<>();
//		for(SpadeGame spadeGame: spadeGames) {
//			SpadeGame newSpadeGame = new SpadeGame();
//			newSpadeGame.setGameId(spadeGame.getGameId());
//			newSpadeGames.add(newSpadeGame);
//		}
		spadeGameView.setSpadeGames(spadeGames);
		
		return spadeGameView;
	}

	


}
