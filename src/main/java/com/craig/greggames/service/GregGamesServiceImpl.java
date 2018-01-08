package com.craig.greggames.service;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGameChildTypes;
@Service
public class GregGamesServiceImpl implements GregGamesService{
	
	
	public GregGameChildTypes[] getGregGames(){
		
		return GregGameChildTypes.values();
	}

}
