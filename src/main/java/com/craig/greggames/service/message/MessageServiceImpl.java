package com.craig.greggames.service.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.SpadeGame;
import com.craig.greggames.service.spades.SpadeService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private SpadeService spadeService;
	
	@Override
	public List<SpadeGame> addGame(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		
		spadeService.addGame(spadeGame);
		return spadeService.getGames();
		
	}

	
}
