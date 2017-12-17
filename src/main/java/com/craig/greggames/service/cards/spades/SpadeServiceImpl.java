package com.craig.greggames.service.cards.spades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;
import com.craig.greggames.model.game.cards.spades.dao.repo.SpadeGameRepository;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadeServiceImpl implements SpadeService {

	@Autowired
	private SpadeGameRepository repository;

	@Autowired
	private GregMapper mapper;

	@Autowired
	private SpadeGameHandler spadeGameService;

	@Autowired
	private SpadeTeamHandler spadeTeamService;

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

	@Override
	public SpadeGame modifyGameState(String gameType, String gameId, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		spadeGameService.play(spadeGame);

		return saveGame(spadeGame);
	}

}
