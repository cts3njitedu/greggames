package com.craig.cards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craig.cards.model.GregGames;
import com.craig.cards.service.GregGamesService;

@RestController
@RequestMapping("/greggames")
@CrossOrigin("http://localhost:4200/")
public class GregGamesController {
	
	@Autowired
	private GregGamesService service;


	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody GregGames[] getGregGames() {
		
		return service.getGregGames();
	}

}
