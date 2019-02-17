package com.craig.greggames.cleaners.games.cards.spades;

import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeMessageCleaner extends AbstractSpadeGameCleaner {


	@Override
	public void clean(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		for(Entry<TeamTable, SpadeTeam> team : spadeGame.getTeams().entrySet()) {
			for(Entry<PlayerTable, SpadePlayer> player: team.getValue().getPlayers().entrySet()) {
				player.getValue().getErrorMessages().clear();
				player.getValue().getServerMessages().clear();
			}
		}

	}

}
