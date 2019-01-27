package com.craig.greggames.demo;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

public class GetBotCard {
	
	@Autowired
	private SpadeGameHandler creator;
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	@Autowired
	private CardHandler cardHandler;
	@Autowired
	private SpadeValidatorEngine validatorFactory;
	@Autowired
	private SpadeBotHandler botHandler;
	
	

	public GetBotCard() {

		this.creator = new SpadeGameHandler();
		this.cardHandler = new CardHandler();
		this.validatorFactory=new SpadeValidatorEngine();
	}
	
	
	public void playBot(SpadeGame spadeGame) {
		creator.create(spadeGame);
		creator.start(spadeGame);
		creator.play(spadeGame);
	}
	

}
