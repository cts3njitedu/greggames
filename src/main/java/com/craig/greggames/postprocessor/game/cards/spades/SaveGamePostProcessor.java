package com.craig.greggames.postprocessor.game.cards.spades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
@Order(3)
public class SaveGamePostProcessor extends AbstractPostProcessor {

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	private Set<SpadeNotifications> spadeNotifications = new HashSet<>(Arrays.asList(SpadeNotifications.BID,SpadeNotifications.PLAY));
	
	private Set<SpadeNotifications> playerChangeNotifications = 
			new HashSet<>(Arrays.asList(SpadeNotifications.LEAVE_GAME,SpadeNotifications.NEW_PLAYER));
	private Logger logger = Logger.getLogger(SaveGamePostProcessor.class);
	@Override
	SpadeGame postProcess(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		return saveGame(spadeGame);
	}
	
	@Value ("${spade.maxTime:60}")
	private long maxTime;
	
	private long maxNotificationTime = 1;
	
	private  SpadeGame saveGame(SpadeGame spadeGame) {
		
		logger.info("Entering " + getClass());
//		SpadeGame oldSpadeGame = spadeGame;
		//if game id is null this means this is a new game
//		if(spadeGame.getGameId()!=null) {
//			oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
//			mergeNewWithOld(oldSpadeGame, spadeGame);
//			spadeBotHandler.determineBots(spadeGame);
//			spadeGame.setLock(false);
//			if(spadeNotifications.contains(spadeGame.getPlayerNotification())) {
//				spadeGame.setMaxTime(maxTime);
//			}
//		}
		
		spadeGame.setLock(false);
		if(SpadeNotifications.TRICK_OVER == spadeGame.getPlayerNotification() || SpadeNotifications.HAND_OVER==spadeGame.getPlayerNotification()) {
			return spadePersistenceDal.saveGame(spadeGame);
		}
		
		
		
		
		if(spadeGame.isGameOver()) {
			spadeGame.setMaxTime(maxNotificationTime);
			return spadePersistenceDal.saveGame(spadeGame);
		}
		
		if(spadeNotifications.contains(spadeGame.getPlayerNotification())) {
			spadeGame.setMaxTime(maxTime);
		}
		
		if(SpadeNotifications.CREATE==spadeGame.getPlayerNotification() || SpadeNotifications.START==spadeGame.getPlayerNotification()) {
			logger.info("Creating or starting game: "+spadeGame.getPlayerNotification());
			return spadePersistenceDal.saveGame(spadeGame);
			
		}
		else if(!(playerChangeNotifications.contains(spadeGame.getPlayerNotification()))) {
			logger.info("Game action is: " + spadeGame.getPlayerNotification());
			Set<String> excludedFields = new HashSet<>();
			excludedFields.add("activePlayers");
			
			
			for(Entry<TeamTable, SpadeTeam> team: spadeGame.getTeams().entrySet()) {
				
				for(Entry<PlayerTable, SpadePlayer> player: team.getValue().getPlayers().entrySet()) {
					StringBuilder playerBuilder = new StringBuilder().append("teams.").append(team.getKey())
							.append(".players.").append(player.getKey());
					String exludeBot = new StringBuilder().append(playerBuilder).append(".bot").toString();
					String excludeUserId = new StringBuilder().append(playerBuilder).append(".userId").toString();
					excludedFields.add(exludeBot);
					excludedFields.add(excludeUserId);
				}
			}
			return spadePersistenceDal.updateGame(spadeGame, excludedFields,new HashSet<>());
		}
		else {
			Set<String> excludedFields = new HashSet<>();
			excludedFields.add("activePlayers");
			
			logger.info("Player makeup is changing: " +spadeGame.getPlayerNotification());
			for(Entry<TeamTable, SpadeTeam> team: spadeGame.getTeams().entrySet()) {
				
				for(Entry<PlayerTable, SpadePlayer> player: team.getValue().getPlayers().entrySet()) {
					if(player.getKey()!=spadeGame.getGameModifier()) {
						StringBuilder playerBuilder = new StringBuilder().append("teams.").append(team.getKey())
								.append(".players.").append(player.getKey());
						String exludeBot = new StringBuilder().append(playerBuilder).append(".bot").toString();
						String excludeUserId = new StringBuilder().append(playerBuilder).append(".userId").toString();
						excludedFields.add(exludeBot);
						excludedFields.add(excludeUserId);	
					}
					
				}
			}
			
			logger.info("Exiting " + getClass());
			return spadePersistenceDal.updateGame(spadeGame, excludedFields,new HashSet<>());
			
			
			
			
		}
		
		
		
		
		
		
	
		
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
