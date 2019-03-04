package com.craig.greggames.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;

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
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(CardGamesApplication.class);
//		SpadeGameHandler spadeGameHandler = ctx.getBean(SpadeGameHandler.class);
//		SpadeGame spadeGame = new SpadeGame();
//		//spadeGame.setStarting(true);
//	
//		spadeGame.setBags(50);
//		spadeGame.setBagPoints(100);
//		spadeGame.setGameOver(false);
//		spadeGame.setPointsToWin(150);
//		spadeGame.setNumberOfTeams(2);
//		spadeGame.setBidNilPoints(100);
//		spadeGame.setPointsToLose(-500);
//		spadeGameHandler.create(spadeGame);
//		UpdateGenerator updateGenerator = new UpdateGenerator();
//		GregMapper gregMapper = new GregMapper();
//		Update update = new Update();
//		Map<?,?> map = null;
//		try {
//			map = gregMapper.convertObjectToMap(spadeGame);
////			System.out.println(gregMapper.convertObjectToString(gregMapper.convertObjectToMap(spadeGame)));
////			updateGenerator.makeUpdateFields(update, map, "");
////			updateGenerator.makeUpdateFields(new Update(), gregMapper.spadeGameToDAO(spadeGame),new StringBuilder(),SpadeNotifications.BID);	
//		}catch(Throwable throwable) {
//			System.out.println("error happened");
//		}
		
		//map.forEach((k, v) -> System.out.println((k + ":" + v)));
		
	
		List<Card> cards = Arrays.asList(new Card("DAF",CardValue.EIGHT,CardSuit.CLUBS),new Card("DAF",CardValue.EIGHT,CardSuit.DIAMONDS),new Card("DAF",CardValue.NINE,CardSuit.DIAMONDS));
		
		Map<CardSuit, Set<Card>> map = cards.stream().collect(Collectors.groupingBy(Card::getSuit,Collectors.toSet()));
		
		
		System.out.println(map.toString());
		
//		List<Card> cards1 = Arrays.asList(new Card("DAF",CardValue.JACK,CardSuit.DIAMONDS),new Card("DAF",CardValue.EIGHT,CardSuit.DIAMONDS),new Card("DAF",CardValue.NINE,CardSuit.DIAMONDS));
		
		
		List<Card> cards1 = null;
		Card maxCard = cards1.stream().min(new Comparator<Card>() {

			@Override
			public int compare(Card o1, Card o2) {
				// TODO Auto-generated method stub
				return o1.getValue().getValue()-o2.getValue().getValue();
			}
		}).orElse(null);
		System.out.println(maxCard);
	}
}
