//package com.craig.greggames.postprocessor.game.cards.spades;
//
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Service;
//
//import com.craig.greggames.handler.game.cards.CardHandler;
//import com.craig.greggames.model.game.cards.Card;
//import com.craig.greggames.model.game.cards.player.PlayerTable;
//import com.craig.greggames.model.game.cards.spades.SpadeGame;
//import com.craig.greggames.model.game.cards.spades.SpadePlayer;
//import com.craig.greggames.model.game.cards.spades.SpadeTeam;
//import com.craig.greggames.model.game.cards.team.TeamTable;
//
//@Service
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class PlayerCardsPostProcessor extends AbstractPostProcessor {
//
//	@Autowired
//	private CardHandler cardHandler;
//
//	private static final Logger logger = Logger.getLogger(PlayerCardsPostProcessor.class);
//
//	@Override
//	SpadeGame postProcess(SpadeGame spadeGame) {
//		// TODO Auto-generated method stub
//		return saveGame(spadeGame);
//	}
//
//	@Value("${spade.maxTime:60}")
//	private long maxTime;
//	@Value("${spade.maxNotificationGame:15}")
//	private long maxNotificationTime;
//
//	private SpadeGame saveGame(SpadeGame spadeGame) {
//
//		logger.info("Entering: " + getClass());
//
//		// spadeGame.setPlayAgain(true);
////		for (Entry<TeamTable, SpadeTeam> teams : spadeGame.getTeams().entrySet()) {
////			for (Entry<PlayerTable, SpadePlayer> players : teams.getValue().getPlayers().entrySet()) {
////				List<Card>remainingCards = players.getValue().getRemainingCards().stream().collect(Collectors.toList());
////				players.getValue().setSortedCards(cardHandler.sortCardsBySuitList(remainingCards));
////			}
////		}
//
//		logger.info("Exiting: " + getClass());
//		return spadeGame;
//	}
//
//}
