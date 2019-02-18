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
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameView;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.service.cards.spades.SpadeService;
import com.craig.greggames.util.DealUtility;

@RestController
@RequestMapping("/cards/spades")
public class SpadesController {

	@Autowired
	private SpadeService spadeService;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	@Autowired
	private DealUtility dealUtility;

	@RequestMapping(method = RequestMethod.GET, path = { "/games" })
	public @ResponseBody List<SpadeGame> getGames() {

		return spadeService.getGames();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = { "/view" })
	public @ResponseBody SpadeGameView getGameView() {

		return spadeService.getGameView();
	}

	@RequestMapping(method = RequestMethod.POST, path = { "/games" })
	public @ResponseBody SpadeGame addGame(SpadeGame spadeGame) {

		return spadePersistenceDal.saveGame(spadeGame);
	}

	@RequestMapping(method = RequestMethod.GET, path = { "/games/{gameId}" })
	public @ResponseBody SpadeGame getGame(@PathVariable String gameId) {

		return spadeService.findGame(gameId);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = { "/games/{gameId}/start" })
	public @ResponseBody SpadeGame startGame(SpadeGame spadeGame) {

		spadeGame.setServerPlaying(false);
		return spadeService.playGame(spadeGame);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = { "/games/error" })
	public @ResponseBody SpadeGame testException() throws GreggamesException {

		throw new GreggamesException("This is a test");
		//return null;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, path = { "/games/{gameId}" })
	public @ResponseBody SpadeGame modifyGameState(@PathVariable String gameId, @RequestBody SpadeGame spadeGame) throws GreggamesException {

		spadeGame.setServerPlaying(false);
		return spadeService.playGame(spadeGame);
		//return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = { "/games/{gameId}/players" })
	public @ResponseBody SpadeGame addPlayer(@PathVariable String gameId, @RequestBody SpadeGame spadeGame) throws GreggamesException {
		spadeGame.setServerPlaying(false);
		return spadeService.playGame(spadeGame);
		//return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = { "/games/{gameId}/players/{playerId}/validator" })
	public @ResponseBody SpadeGame playerValidator(@PathVariable String gameId, @RequestBody SpadeGame spadeGame) throws GreggamesException {
		spadeGame.setServerPlaying(false);
		return spadeService.playGame(spadeGame);
		//return null;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = { "/games/shuffle" })
	public @ResponseBody List<Card> getCards(){
		
		return dealUtility.getSpadeHand();
		//return null;
	}
	
	
	
	

}
