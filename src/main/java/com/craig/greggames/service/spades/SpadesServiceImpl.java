package com.craig.greggames.service.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.SpadeGame;
import com.craig.greggames.mongo.model.SpadeGameDAO;
import com.craig.greggames.mongo.model.SpadeGameRespository;

@Service
public class SpadesServiceImpl implements SpadeService {

	@Autowired
	private SpadeGameRespository repository;
	@Override
	public List<SpadeGame> getGames() {
		// TODO Auto-generated method stub
		List<SpadeGame> spadeGames = new ArrayList<SpadeGame>();
		List<SpadeGameDAO> spadeGamesDAO = repository.findAll();
		for(SpadeGameDAO spadeGameDAO : spadeGamesDAO) {
			
			SpadeGame spadeGame = new SpadeGame();
			spadeGame.setGameId(spadeGameDAO.getGameId());
			spadeGame.setPointsToWin(spadeGameDAO.getPointsToWin());
			spadeGames.add(spadeGame);
		}
		return spadeGames;
	}
	@Override
	public SpadeGame addGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = new SpadeGameDAO();
		spadeGameDAO.setPointsToWin(spadeGame.getPointsToWin());
		repository.save(spadeGameDAO);
		return spadeGame;
	}

	

	
	

}
