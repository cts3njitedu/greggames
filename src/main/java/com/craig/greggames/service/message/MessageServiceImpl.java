package com.craig.greggames.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.dao.SpadeGameDAO;
import com.craig.greggames.service.spades.SpadeService;
import com.craig.greggames.service.spades.state.SpadeGameService;
import com.craig.greggames.util.GregMapper;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SpadeService spadeService;
	
	@Autowired
	private SpadeGameService spadeGameService;
	
	@Autowired
	private GregMapper mapper;
	
	@Override
	public SpadeGame createGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		System.out.println(spadeGame.toString());
		return spadeService.createGame(spadeGame);
		
		
		
	}

	@Override
	public SpadeGame getGame(String gameId) {
		// TODO Auto-generated method stub
		return spadeService.findGame(gameId);
	}

	@Override
	public SpadeGame modifyGameState(String gameType, String gameId, SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		spadeGameService.play(spadeGame);
		
		return spadeService.saveGame(spadeGame);
	
	}

	
}
