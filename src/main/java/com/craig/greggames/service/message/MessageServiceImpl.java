package com.craig.greggames.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.service.spades.SpadeService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SpadeService spadeService;
	
	@Override
	public SpadeGame addGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		System.out.println(spadeGame.toString());
		return spadeService.addGame(spadeGame);
		
		
		
	}

	@Override
	public SpadeGame getGame(String gameId) {
		// TODO Auto-generated method stub
		return spadeService.findGame(gameId);
	}

	
}
