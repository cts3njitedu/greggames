package com.craig.greggames.service.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.mongo.spades.SpadeGameDAO;
import com.craig.greggames.model.mongo.spades.SpadeGameRespository;
import com.craig.greggames.model.spades.SpadeGame;

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
			spadeGame.setBags(spadeGameDAO.getBags());
			spadeGame.setBidNil(spadeGameDAO.getBidNil());
			spadeGame.setGameId(spadeGameDAO.getGameId());
			spadeGame.setNumberOfPlayers(spadeGameDAO.getNumberOfPlayers());
			spadeGame.setOverBook(spadeGameDAO.getOverBook());
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
		spadeGameDAO.setBags(spadeGame.getBags());
		spadeGameDAO.setBidNil(spadeGame.getBidNil());
		spadeGameDAO.setOverBook(spadeGame.getOverBook());
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
		spadeGame.setOverBook(spadeGameDAO.getOverBook());
		spadeGame.setPointsToWin(spadeGameDAO.getPointsToWin());
		
		return spadeGame;
	}

	

	
	

}
