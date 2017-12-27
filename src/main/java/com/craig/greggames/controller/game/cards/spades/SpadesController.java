package com.craig.greggames.controller.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.service.cards.spades.SpadeService;

@RestController
@RequestMapping("/cards/spades")
public class SpadesController {

	@Autowired
	private SpadeService spadeService;

	@RequestMapping(method = RequestMethod.GET, path = { "/games" })
	public @ResponseBody List<SpadeGame> getGames() {

		return spadeService.getGames();
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/games" })
	public @ResponseBody List<SpadeGame> addGame(SpadeGame spadeGame) {

		return spadeService.getGames();
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/games/{gameId}" })
	public @ResponseBody SpadeGame getGame(@PathVariable String gameId) {

		return spadeService.findGame(gameId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = { "/games/{gameId}/start" })
	public @ResponseBody SpadeGame startGame(@PathVariable String gameId) {

		return spadeService.startGame(gameId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = { "/games/error" })
	public @ResponseBody SpadeGame testException() throws GreggamesException {

		throw new GreggamesException("This is a test");
		//return null;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, path = { "/games/{gameId}" })
	public @ResponseBody SpadeGame modifyGameState(@PathVariable String gameId, @RequestBody SpadeGame spadeGame) throws GreggamesException {

		return spadeService.modifyGameState(null, gameId, spadeGame);
		//return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = { "/games/{gameId}/players" })
	public @ResponseBody SpadeGame addPlayer(@PathVariable String gameId, @RequestBody SpadeGame spadeGame) throws GreggamesException {

		return spadeService.saveGame(spadeGame);
		//return null;
	}
	//
	// @RequestMapping(path= {"/hand"}, method=RequestMethod.POST)
	// public @ResponseBody Hand getHand(@RequestBody Game game) {
	//
	// return spadeService.getHand(game);
	// }
	//
	
	

}
