package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardSuit;
import com.craig.greggames.model.game.cards.player.PlayerPosition;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.model.game.cards.team.TeamTable;

@Service
public class SpadePlayerHandler {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private SpadeBotHandler spadeBotHandler;
	
	private static final Logger logger = Logger.getLogger(SpadePlayerHandler.class);
	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		logger.info("Determining player winner");
		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getCurrTurn(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getCurrTurn());
		currPlayer.setHasPlayed(true);
		if (newSpadeGame.getTempWinner() == null) {
			if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				currPlayer.setWon(true);
			}

			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams()
					.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer tempWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card tempWinnerCard = tempWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (tempWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > tempWinnerCard.getValue().getValue()) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					tempWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						tempWinner.setWon(true);
					}
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						currPlayer.setWon(true);
					}
					tempWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					if (newSpadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

						tempWinner.setWon(true);
					}

					currPlayer.setWon(false);
				}
			}
		}
		logger.info("Winner is " + newSpadeGame.getTempWinner());

	}

	public void determinePlayerPoints(SpadeGame newSpadeGame) {

		logger.info("Determing player points");
		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getTempWinner(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		spadePlayer.setWon(true);
		spadePlayer.setTurn(true);
		spadePlayer.setPlayerCurrentScore(spadePlayer.getPlayerCurrentScore() + POINTS_WON_PER_TRICK_BEFORE_OVERBID);

		logger.info("Player: " + spadePlayer.getName() + ", Current Score: "+ spadePlayer.getPlayerCurrentScore());
		newSpadeGame.setStartTurn(spadePlayer.getName());

	}

	public SpadePlayer retrievePlayer(PlayerTable player, SpadeGame newSpadeGame) {

		return newSpadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(player, newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(player);

	}

	public void determineTurn(SpadeGame newSpadeGame) {

		PlayerTable currPlayer = newSpadeGame.getCurrTurn();

		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				if (entryPlayer.getKey() == currPlayer) {

					entryPlayer.getValue().setTurn(true);
				} else {
					entryPlayer.getValue().setTurn(false);
				}
			}
		}
	}

	public void cleanUpErrors(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				entryPlayer.getValue().setError(false);
				Map<SpadeErrors, String> messages = new EnumMap<SpadeErrors, String>(SpadeErrors.class);
				entryPlayer.getValue().setErrorMessages(messages);
			}
		}
	}

	public void cleanUpError(SpadeGame newSpadeGame) {

		SpadePlayer player = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getGameModifier(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getGameModifier());
		player.setError(false);
		player.getErrorMessages().clear();
	}

	public void addError(SpadeGame newSpadeGame, SpadeErrors spadeError, SpadeGame oldSpadeGame) {

		SpadePlayer player = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getGameModifier(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getGameModifier());

		SpadePlayer oldPlayer = newSpadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(newSpadeGame.getGameModifier(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getGameModifier());
		player.setError(true);
		
		player.getErrorMessages().put(spadeError, spadeError.getError());
		logger.info("Error for player " + player.getName() + " is "+ player.getErrorMessages());
		if (oldPlayer.isHasPlayed()) {

			player.getRemainingCards().add(player.getPlayingCard());
			player.setPlayingCard(oldPlayer.getPlayingCard());
		} else {
			if (player.getPlayingCard() != null) {
				player.getRemainingCards().add(player.getPlayingCard());
			}
			player.setPlayingCard(null);

		}

	}
	
	public void cleanUpWhoHasPlayed(SpadeGame newSpadeGame) {
		
		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				entryPlayer.getValue().setHasPlayed(false);
			}
		}
	}
	
	public void addNewPlayer(SpadeGame newSpadeGame) {
		logger.info("Adding new player...");
		spadeBotHandler.determineBots(newSpadeGame);
		newSpadeGame.setNewPlayer(false);
		
	}
	
	public void determineActivePlayers(SpadeGame newSpadeGame) {
		
		int activePlayers=0;
		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				if(!entryPlayer.getValue().isBot()) {
					activePlayers++;
				}
			}
		}
		
		newSpadeGame.setActivePlayers(activePlayers);
		logger.info("Number of active players: " + newSpadeGame.getActivePlayers());
	}

	public void setPlayerPosition(SpadeGame newSpadeGame) {
		
		logger.info("Setting player positions...");
		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {
				int position = entryPlayer.getKey().getCode();
				int count = 1;
				PlayerTable currentPlayer = entryPlayer.getKey();
				Map<PlayerPosition, PlayerTable> playerPositionMap = entryPlayer.getValue().getPlayerPositions();
				while(count<=MAX_TURN_PER_TRICK) {
					playerPositionMap.put(PlayerPosition.getPlayerPosition(count), currentPlayer);
					count++;
					position++;
					if(position>MAX_TURN_PER_TRICK) {
						position = 1;
					}
					currentPlayer = PlayerTable.getPlayer(position);
				}
				entryPlayer.getValue().setPlayerPositions(playerPositionMap);
			}
		}
		
	}
}
