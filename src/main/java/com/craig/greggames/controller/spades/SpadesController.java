package com.craig.greggames.controller.spades;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.service.spades.SpadeService;

@RestController
@RequestMapping("/cards/spades")
public class SpadesController {

	@Autowired
	private SpadeService spadeService;


	@RequestMapping(method=RequestMethod.GET, path= {"/games"})
	public @ResponseBody List<SpadeGame> getGames() {
		
		return spadeService.getGames();
	}
	
	
	@RequestMapping(method=RequestMethod.POST, path= {"/games"})
	public @ResponseBody List<SpadeGame> addGame(SpadeGame spadeGame) {
		
		return spadeService.getGames();
	}
//	
//	@RequestMapping(path= {"/hand"}, method=RequestMethod.POST)
//	public @ResponseBody Hand getHand(@RequestBody Game game) {
//	
//		return spadeService.getHand(game);
//	}
//	
	
	
	
	
}
