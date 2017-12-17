package com.craig.greggames.database;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;
import com.craig.greggames.model.game.cards.spades.dao.repo.SpadeGameRepository;

public class MockDataBaseConnection {
	
	@Autowired
	private SpadeGameRepository spadeGameRepo;
	
	
	
	public void saveSpadeGame() {
		
		System.out.println("I like chicken");
		SpadeGameDAO spadeGame = new SpadeGameDAO();

		spadeGame.setActivePlayers(0);
		spadeGame.setBagPoints(100);
		spadeGame.setBags(50);
		spadeGame.setBidNilPoints(100);
		spadeGame.setStarting(true);
		spadeGame.setPointsToWin(150);
		spadeGame.setPointsToLose(-100);
		spadeGame.setNumberOfTeams(2);
		
		when(spadeGameRepo.save(any(SpadeGameDAO.class))).thenReturn(spadeGame);
	}
	
	public void findSpadeGame() {
		
		SpadeGameDAO spadeGame = new SpadeGameDAO();

		spadeGame.setActivePlayers(0);
		spadeGame.setBagPoints(100);
		spadeGame.setBags(50);
		spadeGame.setBidNilPoints(100);
		spadeGame.setStarting(true);
		spadeGame.setPointsToWin(150);
		spadeGame.setPointsToLose(-100);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setGameId("1");
		
		when(spadeGameRepo.findOne(any(String.class))).thenReturn(spadeGame);
	}

}
