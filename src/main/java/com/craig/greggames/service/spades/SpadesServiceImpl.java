package com.craig.greggames.service.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.dao.SpadeGameDAO;
import com.craig.greggames.model.spades.dao.SpadeGameRepository;
import com.craig.greggames.model.team.TeamTable;
import com.craig.greggames.service.spades.state.SpadeGameService;
import com.craig.greggames.service.spades.state.SpadeTeamService;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadesServiceImpl implements SpadeService {

	@Autowired
	private SpadeGameRepository repository;

	@Autowired
	private GregMapper mapper;

	@Autowired
	private SpadeGameService spadeGameService;

	@Autowired
	private SpadeTeamService spadeTeamService;

	@Override
	public List<SpadeGame> getGames() {
		// TODO Auto-generated method stub
		List<SpadeGame> spadeGames = new ArrayList<SpadeGame>();
		List<SpadeGameDAO> spadeGamesDAO = repository.findAll();
		for (SpadeGameDAO spadeGameDAO : spadeGamesDAO) {

			// SpadeGame spadeGame = new SpadeGame();
			// spadeGame.setBags(spadeGameDAO.getBags());
			// spadeGame.setBidNil(spadeGameDAO.getBidNil());
			// spadeGame.setGameId(spadeGameDAO.getGameId());
			// spadeGame.setNumberOfPlayers(spadeGameDAO.getNumberOfPlayers());
			// spadeGame.setBagPoints(spadeGameDAO.getBagPoints());
			// spadeGame.setPointsToWin(spadeGameDAO.getPointsToWin());

			SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);
			spadeGames.add(spadeGame);
		}
		return spadeGames;
	}

	@Override
	public SpadeGame createGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		spadeGameService.createGame(spadeGame);
		SpadeGameDAO spadeGameDAO = mapper.spadeGameToDAO(spadeGame);

		spadeGameDAO = repository.save(spadeGameDAO);
		return findGame(spadeGameDAO.getGameId());
	}

	@Override
	public SpadeGame findGame(String gameId) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = repository.findOne(gameId);
		SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);
		// spadeGame.setPlayerCardCount(null);

		return spadeGame;
	}

	@Override
	public SpadeGame findGameHeaderInfo(String gameId) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = repository.findOne(gameId);
		SpadeGame spadeGame = mapper.spadeDAOToGame(spadeGameDAO);
		return spadeGame;
	}

	@Override
	public SpadeGame startGame(String gameId) {
		// TODO Auto-generated method stub
		SpadeGame spadeGame = findGame(gameId);
		// spadeGame.setNumberOfTeams(spadeGame.getNumberOfTeams()==0?2:spadeGame.getNumberOfTeams());
		// spadeGame.setStarting(true);
		spadeGameService.startGame(spadeGame);
	
		return saveGame(spadeGame);

	}

	@Override
	public SpadeGame saveGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		SpadeGameDAO spadeGameDAO = mapper.spadeGameToDAO(spadeGame);

		spadeGameDAO = repository.save(spadeGameDAO);
		
		return findGame(spadeGameDAO.getGameId());
	}

}
