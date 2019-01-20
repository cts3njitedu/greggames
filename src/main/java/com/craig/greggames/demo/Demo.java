package com.craig.greggames.demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;

public class Demo {

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadePlayerHandler playerService;
	@Autowired
	private CardHandler cardService;
	
	@Autowired
	private SpadeBotHandler botService;
	
	@Autowired
	private SpadeGameHandler gameService;
	

	public static void main(String[] args) {

		PlaySpades playSpades = new PlaySpades();
//		
//
		SpadeGame spadeGame = new SpadeGame();

		spadeGame.setStarting(true);
		spadeGame.setBags(50);
		spadeGame.setBagPoints(100);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(150);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setBidNilPoints(100);
		spadeGame.setPointsToLose(-500);

		try {
			playSpades.playGame(spadeGame);
		} catch (GreggamesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		UUID uuid = UUID.randomUUID();
//        String randomUUIDString = uuid.toString();
//
//        System.out.println("Random UUID String = " + randomUUIDString);
//        System.out.println("UUID version       = " + uuid.version());
//        System.out.println("UUID variant       = " + uuid.variant());
		
//		GetBotCard getBotCard = new GetBotCard();
//		getBotCard.playBot(spadeGame);

	}
}
