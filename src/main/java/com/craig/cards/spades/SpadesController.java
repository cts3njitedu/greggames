package com.craig.cards.spades;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craig.cards.model.Card;
import com.craig.cards.model.Hand;
import com.craig.cards.spades.model.Game;
import com.craig.cards.spades.service.SpadeService;

@RestController
@RequestMapping("/spades")
public class SpadesController {

	@Autowired
	private SpadeService spadeService;


	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Card postHello(@RequestBody Card player) {
		
		return player;
	}
	
	@RequestMapping(path= {"/hand"}, method=RequestMethod.POST)
	public @ResponseBody Hand getHand(@RequestBody Game game) {
	
		return spadeService.getHand(game);
	}
	
	
	
	
	
}
