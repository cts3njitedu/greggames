package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SaveGamePostProcessor extends AbstractPostProcessor {

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	private Set<SpadeNotifications> spadeNotifications = new HashSet<>(Arrays.asList(SpadeNotifications.BID,SpadeNotifications.PLAY));
	
	private Logger logger = Logger.getLogger(SaveGamePostProcessor.class);
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return saveGame(spadeGame);
	}
	
	@Value ("${spade.maxTime}")
	private long maxTime;
	
	private synchronized SpadeGame saveGame(SpadeGame spadeGame) {
		
		logger.info("Entering " + getClass());
		SpadeGame oldSpadeGame = spadeGame;
		//if game id is null this means this is a new game
		if(spadeGame.getGameId()!=null) {
			oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			mergeNewWithOld(oldSpadeGame, spadeGame);
			spadeBotHandler.determineBots(spadeGame);
			spadeGame.setLock(false);
			if(spadeNotifications.contains(spadeGame.getPlayerNotification())) {
				spadeGame.setMaxTime(maxTime);
			}
		}
		
		logger.info("Exiting " + getClass());
		
		return spadePersistenceDal.saveGame(spadeGame);
		
	}

	//This is if a new player enters game or player leaves game
	private void mergeNewWithOld(SpadeGame oldSpadeGame, SpadeGame newSpadeGame) {
		logger.info("Merging old spade game with new spade game for game id " + newSpadeGame.getGameId() );
		if(!newSpadeGame.getPlayerNotification().equals(SpadeNotifications.START)) {
			newSpadeGame.setActivePlayers(oldSpadeGame.getActivePlayers());
			
			Map<TeamTable, SpadeTeam> oldTeams = oldSpadeGame.getTeams();
			
			for(Entry<TeamTable, SpadeTeam> teamEntry: newSpadeGame.getTeams().entrySet()) {
				Map<PlayerTable, SpadePlayer> players = teamEntry.getValue().getPlayers();
				for(Entry<PlayerTable, SpadePlayer>playerEntry:players.entrySet()) {
					if(playerEntry.getKey()!=newSpadeGame.getGameModifier()) {
						SpadePlayer oldPlayer = oldTeams.get(teamEntry.getKey()).getPlayers().get(playerEntry.getKey());
						playerEntry.getValue().setBot(oldPlayer.isBot());
						playerEntry.getValue().setUserId(oldPlayer.getUserId());
					}
				}
			}
		}
			
		
	}
}
