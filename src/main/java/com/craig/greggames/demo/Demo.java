package com.craig.greggames.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Update;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.util.GregMapper;
import com.craig.greggames.util.UpdateGenerator;

public class Demo {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	@Autowired
	private SpadePlayerHandler spadePlayerHandler;
	@Autowired
	private CardHandler cardHandler;
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	@Autowired
	private SpadeGameHandler spadeGameHandler;
	

	public static void main(String[] args) {

//		PlaySpades playSpades = new PlaySpades();
////		
////
//		SpadeGame spadeGame = new SpadeGame();
//
//		spadeGame.setStarting(true);
//		spadeGame.setBags(50);
//		spadeGame.setBagPoints(100);
//		spadeGame.setGameOver(false);
//		spadeGame.setPointsToWin(150);
//		spadeGame.setNumberOfTeams(2);
//		spadeGame.setBidNilPoints(100);
//		spadeGame.setPointsToLose(-500);
//
//		try {
//			playSpades.playGame(spadeGame);
//		} catch (GreggamesException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		UUID uuid = UUID.randomUUID();
//        String randomUUIDString = uuid.toString();
//
//        System.out.println("Random UUID String = " + randomUUIDString);
//        System.out.println("UUID version       = " + uuid.version());
//        System.out.println("UUID variant       = " + uuid.variant());
		
//		GetBotCard getBotCard = new GetBotCard();
//		getBotCard.playBot(spadeGame);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(CardGamesApplication.class);
		SpadeGameHandler spadeGameHandler = ctx.getBean(SpadeGameHandler.class);
		SpadeGame spadeGame = new SpadeGame();
		//spadeGame.setStarting(true);
	
		spadeGame.setBags(50);
		spadeGame.setBagPoints(100);
		spadeGame.setGameOver(false);
		spadeGame.setPointsToWin(150);
		spadeGame.setNumberOfTeams(2);
		spadeGame.setBidNilPoints(100);
		spadeGame.setPointsToLose(-500);
		spadeGameHandler.create(spadeGame);
		UpdateGenerator updateGenerator = new UpdateGenerator();
		GregMapper gregMapper = new GregMapper();
		Update update = new Update();
		Map<?,?> map = null;
		try {
			map = gregMapper.convertObjectToMap(spadeGame);
//			System.out.println(gregMapper.convertObjectToString(gregMapper.convertObjectToMap(spadeGame)));
//			updateGenerator.makeUpdateFields(update, map, "");
//			updateGenerator.makeUpdateFields(new Update(), gregMapper.spadeGameToDAO(spadeGame),new StringBuilder(),SpadeNotifications.BID);	
		}catch(Throwable throwable) {
			System.out.println("error happened");
		}
		
		//map.forEach((k, v) -> System.out.println((k + ":" + v)));
		
		
	}
}
