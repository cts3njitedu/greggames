package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeGameHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
@Order(3)
public class GameOverPostProcessor extends AbstractPostProcessor {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SpadeGameHandler spadeGameHandler;
	private static final Logger logger = Logger.getLogger(GameOverPostProcessor.class);

	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return saveGame(spadeGame);
	}

	@Value("${spade.maxTime:60}")
	private long maxTime;
	@Value("${spade.maxNotificationGame:15}")
	private long maxNotificationTime;

	private SpadeGame saveGame(SpadeGame spadeGame) {

		logger.info("Entering: " + getClass());
		if (spadeGame.isGameOver()) {

			spadeGame.setTeams(null);
			spadeGame.setSpadeBroken(null);
			spadeGame.setActivePlayers(0);
			spadeGame.setServerPlaying(false);
			spadeGame.setGameNotification(SpadeNotifications.GAME_OVER);
			spadeGame.setPlaying(false);
			spadeGame.setGameOver(false);
			spadeGame.setPlayerNotification(SpadeNotifications.CREATE);
			spadeGameHandler.create(spadeGame);
			spadeGameHandler.start(spadeGame);
//			spadeGame.setPlayAgain(true);
			for(Entry<TeamTable, SpadeTeam> teams : spadeGame.getTeams().entrySet()) {
				for(Entry<PlayerTable, SpadePlayer> players: teams.getValue().getPlayers().entrySet()) {
					players.getValue().setPlayAgain(true);
				}
			}
		}
		logger.info("Exiting: " + getClass());
		return spadeGame;
	}

}
