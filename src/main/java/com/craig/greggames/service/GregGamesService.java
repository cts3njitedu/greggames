package com.craig.greggames.service;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGames;
@Service
public class GregGamesService {
	
	
	public GregGames[] getGregGames(){
		
		return GregGames.values();
	}

}
