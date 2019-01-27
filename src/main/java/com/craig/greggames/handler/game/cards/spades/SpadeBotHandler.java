package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.bot.game.cards.spades.SpadeBotPlayerPositionEngine;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadeBotHandler {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;
	
	@Autowired
	private SpadeBotPlayerPositionEngine spadeBotPlayerPositionEngine;
	
	

	public int getBotBid(SpadeGame spadeGame) {
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;
		SpadeTeam team = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(spadeGame.getCurrTurn());
		Set<Card> cards = player.getRemainingCards();
		for (Card card : cards) {

			switch (card.getSuit()) {

			case HEARTS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					hearts++;
				}
				break;

			case SPADES:

				if (card.getValue().getValue() >= CardValue.NINE.getValue()) {
					spades++;
				}

				break;
			case DIAMONDS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					diamonds++;
				}
				break;
			case CLUBS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					clubs++;
				}
				break;
			default:
				break;
			}

		}
		return POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs);

	}

	public void determineBotCard(SpadeGame spadeGame) {
		
		SpadePlayer player = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers().get(spadeGame.getCurrTurn());
		if(spadeGame.isServingPlaying()) {
			player.setPlayingCard(getBotCard(spadeGame));
		}
		
	}
	private Card getBotCard(SpadeGame newSpadeGame) {

		SpadeTeam leadingSpadeTeam = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getStartTurn(), newSpadeGame.getNumberOfTeams()));
		SpadeTeam currPlayerTeam = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()));
		SpadePlayer leadPlayer = leadingSpadeTeam.getPlayers().get(newSpadeGame.getStartTurn());

		SpadePlayer currPlayer = currPlayerTeam.getPlayers().get(newSpadeGame.getCurrTurn());

		SpadeGameMetaData spadeGameMetaData = cardHandler.getSpadeGameMetaData(newSpadeGame);

		int playerTrickPosition = spadeTeamHandler.getTrickPlayerPosition(leadPlayer.getName(), currPlayer.getName());

		return spadeBotPlayerPositionEngine.botPlayerEngine(spadeGameMetaData, newSpadeGame, playerTrickPosition);

	}

	public void determineBots(SpadeGame newSpadeGame) {

		System.out.println("Determining Bots");
		int numberOfActivePlayers = 0;
		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams())).getPlayers().get(player);
			if (spadePlayer.getUserId() != null) {

				spadePlayer.setBot(false);
				numberOfActivePlayers++;
			} else {

				spadePlayer.setBot(true);
			}
		}
		newSpadeGame.setActivePlayers(numberOfActivePlayers);

	}
}
