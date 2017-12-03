package com.craig.greggames.service.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.mongo.spades.SpadeGameDAO;
import com.craig.greggames.model.mongo.spades.SpadeGameRepository;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadesServiceImpl implements SpadeService {

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

//			SpadeGame spadeGame = new SpadeGame();
//			spadeGame.setBags(spadeGameDAO.getBags());
//			spadeGame.setBidNil(spadeGameDAO.getBidNil());
//			spadeGame.setGameId(spadeGameDAO.getGameId());
//			spadeGame.setNumberOfPlayers(spadeGameDAO.getNumberOfPlayers());
//			spadeGame.setBagPoints(spadeGameDAO.getBagPoints());
//			spadeGame.setPointsToWin(spadeGameDAO.getPointsToWin());
			
			SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);
			spadeGames.add(spadeGame);
		}
		return spadeGames;
	}

	@Override
	public SpadeGame addGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = new SpadeGameDAO();
		spadeGameDAO.setPointsToWin(spadeGame.getPointsToWin());
		spadeGameDAO.setBags(spadeGame.getBags());
		spadeGameDAO.setBidNil(spadeGame.getBidNil());
		spadeGameDAO.setBagPoints(spadeGame.getBagPoints());
		spadeGameDAO.setNumberOfPlayers(spadeGame.getNumberOfPlayers());
		spadeGameDAO = repository.save(spadeGameDAO);
		return findGame(spadeGameDAO.getGameId());
	}

	@Override
	public SpadeGame findGame(String gameId) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = repository.findOne(gameId);
		SpadeGame spadeGame = new SpadeGame();
		spadeGame.setBags(spadeGameDAO.getBags());
		spadeGame.setBidNil(spadeGameDAO.getBidNil());
		spadeGame.setGameId(spadeGameDAO.getGameId());
		spadeGame.setNumberOfPlayers(spadeGameDAO.getNumberOfPlayers());
		spadeGame.setBagPoints(spadeGameDAO.getBagPoints());
		spadeGame.setPointsToWin(spadeGameDAO.getPointsToWin());

		return spadeGame;
	}

}
