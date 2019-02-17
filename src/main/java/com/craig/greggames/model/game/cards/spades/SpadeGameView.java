package com.craig.greggames.model.game.cards.spades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SpadeGameView {

	private SpadeGame newGame;
	
	private List<SpadeGame> spadeGames;

	public SpadeGame getNewGame() {
		return newGame;
	}

	public void setNewGame(SpadeGame newGame) {
		this.newGame = newGame;
	}

	public List<SpadeGame> getSpadeGames() {
		return spadeGames;
	}

	public void setSpadeGames(List<SpadeGame> spadeGames) {
		this.spadeGames = spadeGames;
	}
	
	
}
