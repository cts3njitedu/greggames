package com.craig.cards.service;

import org.springframework.stereotype.Service;

import com.craig.cards.model.GregGames;
@Service
public class GregGamesService {
	
	
	public GregGames[] getGregGames(){
		
		return GregGames.values();
	}

}
