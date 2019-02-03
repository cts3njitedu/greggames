package com.craig.greggames.model.game.cards.spades.dal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;
import com.craig.greggames.model.game.cards.spades.dao.repo.SpadeGameRepository;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadePersistenceDalImpl implements SpadePersistenceDal{

	@Autowired
	private SpadeGameRepository repository;

	@Autowired
	private GregMapper mapper;
	@Override
	public List<SpadeGame> getGames() {
		// TODO Auto-generated method stub
		List<SpadeGame> spadeGames = new ArrayList<SpadeGame>();
		List<SpadeGameDAO> spadeGamesDAO = repository.findAll();
		for (SpadeGameDAO spadeGameDAO : spadeGamesDAO) {

			SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);
			spadeGames.add(spadeGame);
		}
		return spadeGames;
	}

	@Override
	public SpadeGame findGame(String gameId) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = repository.findOne(gameId);
		SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);

		return spadeGame;
	}

	@Override
	public SpadeGame findGameHeaderInfo(String gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpadeGame startGame(String gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpadeGame saveGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = mapper.spadeGameToDAO(spadeGame);

		spadeGameDAO = repository.save(spadeGameDAO);

		
		SpadeGame newSpadeGame =  findGame(spadeGameDAO.getGameId());
		
		spadeGame.setGameId(newSpadeGame.getGameId());
		
		return spadeGame;
	}

	@Override
	public SpadeGame updateLockingField(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = mapper.spadeGameToDAO(spadeGame);
		
		spadeGameDAO = repository.updateLockingField(spadeGameDAO);
		if(spadeGameDAO!=null) {
			
			return mapper.spadeDAOToGame(spadeGameDAO);
		}
		
		return spadeGame;
	}

}
