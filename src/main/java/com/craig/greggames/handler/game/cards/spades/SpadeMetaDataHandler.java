package com.craig.greggames.handler.game.cards.spades;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadePreviousHand;
import com.craig.greggames.model.game.cards.spades.SpadePreviousTrick;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadeMetaDataHandler {

	@Autowired
	private GregMapper gregMapper;
	
	private Logger logger = Logger.getLogger(SpadeMetaDataHandler.class);

	public void addPreviousHand(SpadeGame spadeGame) {

		logger.info("Adding previous hand");
		String jsonSpadeGame = gregMapper.convertObjectToString(spadeGame);
		SpadeGame copySpadeGame = (SpadeGame) gregMapper.convertStringToObject(jsonSpadeGame, SpadeGame.class);

		SpadePreviousHand spadePreviousHand = new SpadePreviousHand();
		spadePreviousHand.getTeams().putAll(copySpadeGame.getTeams());
		spadePreviousHand.setNumberOfTeams(copySpadeGame.getNumberOfTeams());
		spadePreviousHand.setHandCount(spadeGame.getHandCount());
		spadeGame.setPreviousHand(spadePreviousHand);

	}

	public void addPreviousTrick(SpadeGame spadeGame) {

		logger.info("Adding previous trick");
		String jsonSpadeGame = gregMapper.convertObjectToString(spadeGame);
		SpadeGame copySpadeGame = (SpadeGame) gregMapper.convertStringToObject(jsonSpadeGame, SpadeGame.class);

		SpadePreviousTrick spadePreviousTrick = new SpadePreviousTrick();

		for (Entry<TeamTable, SpadeTeam> entry : copySpadeGame.getTeams().entrySet()) {

			Map<PlayerTable, SpadePlayer> players = new EnumMap<PlayerTable, SpadePlayer>(
					entry.getValue().getPlayers());

			spadePreviousTrick.getPlayers().putAll(players);

		}

		spadeGame.setPreviousTrick(spadePreviousTrick);
	}

}
