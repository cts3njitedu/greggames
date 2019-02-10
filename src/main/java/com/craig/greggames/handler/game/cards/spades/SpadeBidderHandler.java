package com.craig.greggames.handler.game.cards.spades;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
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
	private SpadeTeamHandler spadeTeamHandler;

	private Logger logger = Logger.getLogger(SpadeBidderHandler.class);

	public void determineBid(SpadeGame newSpadeGame) {

		logger.info("Entering " + getClass());
		SpadeTeam team = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(newSpadeGame.getCurrTurn());

		int totalBid = 0;
		totalBid = player.getPlayerBid();
		team.setTotalBid(team.getTotalBid() + totalBid);
		if(totalBid==0) {
			logger.info(newSpadeGame.getGameModifier() + " is bidding nil");
			player.setBidNil(true);
		}
		logger.info(newSpadeGame.getGameModifier()+ " bid is "+ totalBid);
		logger.info("Exiting " + getClass());

	}

	public void cleanUpBid(SpadeGame newSpadeGame) {

		logger.info("Cleaning up Bid");
		for (Entry<TeamTable, SpadeTeam> team : newSpadeGame.getTeams().entrySet()) {

			team.getValue().setTotalBid(0);
			for (Entry<PlayerTable, SpadePlayer> player : team.getValue().getPlayers().entrySet()) {

				player.getValue().setPlayerBid(0);
				player.getValue().setBidNil(false);
			}
		}
	}
}
