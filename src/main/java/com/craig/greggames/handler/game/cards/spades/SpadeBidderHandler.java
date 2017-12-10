package com.craig.greggames.handler.game.cards.spades;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeBidderHandler {

	@Autowired
	private SpadeTeamHandler teamService;

	@Autowired
	private SpadeBotHandler botService;

	public void determineBid(SpadeGame newSpadeGame) {

		SpadeTeam team = newSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(newSpadeGame.getCurrTurn());

		int totalBid = 0;
		if (player.isBot()) {

			botService.getBotBid(player);

		} else {

			totalBid = player.getPlayerBid();
		}
		team.setTotalBid(team.getTotalBid() + totalBid);

	}

	public void cleanUpBid(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, SpadeTeam> team : newSpadeGame.getTeams().entrySet()) {

			team.getValue().setTotalBid(0);
			for (Entry<PlayerTable, SpadePlayer> player : team.getValue().getPlayers().entrySet()) {

				player.getValue().setPlayerBid(0);
			}
		}
	}
}
