package com.craig.greggames.util;

import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class ExcludeFieldsUtility {

	
	
	public void excludeBotAndUserIdAndActivePlayers(SpadeGame spadeGame, Set<String> excludedFields) {
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
	}
}
