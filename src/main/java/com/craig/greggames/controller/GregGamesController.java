package com.craig.greggames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craig.greggames.model.GregGames;
import com.craig.greggames.service.GregGamesService;

@RestController
@RequestMapping("/greggames")
//@CrossOrigin(origins= {"http://localhost:4200","https://greggames.herokuapp.com"})
public class GregGamesController {
	
	@Autowired
	private GregGamesService service;


	@RequestMapping(method=RequestMethod.GET, value="/cards")
	public @ResponseBody GregGames[] getGregGames() {
		
		return service.getGregGames();
	}

}