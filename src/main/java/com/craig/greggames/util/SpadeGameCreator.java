package com.craig.greggames.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.craig.greggames.controller.spades.model.SpadeGame;
import com.craig.greggames.controller.spades.model.SpadePlayer;
import com.craig.greggames.controller.spades.model.Team;
import com.craig.greggames.model.Card;
import com.craig.greggames.model.CardSuit;
import com.craig.greggames.model.PlayerTable;
import com.craig.greggames.model.TeamTable;

public class SpadeGameCreator {

	private Random rand = new Random();

	public SpadeGame execute(SpadeGame spadeGame) {
		SpadeGame newSpadeGame = spadeGame;

		if (newSpadeGame.isStarting()) {
			makeTeams(newSpadeGame);
			distributeCards(newSpadeGame);
			updateHandAndTrick(newSpadeGame);
			newSpadeGame.setStarting(false);
		} else {

			if (newSpadeGame.getCurrTrick() == newSpadeGame.getEndTrick()) {

				determinePlayerWinner(newSpadeGame);

			} else {

				reAdjustCards(newSpadeGame);
				updateHandAndTrick(newSpadeGame);

			}

			if (newSpadeGame.getTrickCount() == 13) {

				determineTeamPoints(newSpadeGame);

				determineGameWinner(newSpadeGame);

				distributeCards(newSpadeGame);
			}

			updateHandAndTrick(newSpadeGame);
		}

		return newSpadeGame;

	}

	private void makeTeams(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			TeamTable team = TeamTable.getTeam(player.getCode() % 2);

			SpadePlayer spadePlayer = new SpadePlayer();
			spadePlayer.setTeam(team);
			spadePlayer.setName(player);
			spadePlayer.setBidding(true);

			newSpadeGame.getTeams().get(team).getPlayers().put(player, spadePlayer);
		}

	}

	private void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		int i = 0;
		for (PlayerTable player : PlayerTable.values()) {

			newSpadeGame.getTeams().get(TeamTable.getTeam(player.getCode() % 2)).getPlayers().get(player)
					.getRemainingCards().addAll(cards.subList(i, i + 13));

			i = i + 13;

		}

	}

	public SpadePlayer determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getStartTrick().getCode();

		SpadePlayer gameWinner = newSpadeGame.getTeams().get(TeamTable.getTeam(code % 2)).getPlayers()
				.get(PlayerTable.getPlayer(code));

		for (int i = 1; i <= 3; i++) {

			code++;
			if (code == 5) {
				code = 1;
			}
			SpadePlayer currPlayer = newSpadeGame.getTeams().get(TeamTable.getTeam(code % 2)).getPlayers()
					.get(PlayerTable.getPlayer(code));
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {

				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {

					gameWinner = currPlayer;
					gameWinner.setWon(true);
				}

			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					gameWinner = currPlayer;
				}
			}

		}

		determinePlayerPoints(newSpadeGame, gameWinner);
		return gameWinner;
	}

	private void determineTeamPoints(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, Team> entry : newSpadeGame.getTeams().entrySet()) {
			int teamScore = 0;
			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				teamScore = teamScore + entryPlayer.getValue().getPlayerCurrentScore();
			}

			int overBookOne = teamScore - entry.getValue().getTotalBid();
			if (overBookOne > 0) {
				int totalBags = overBookOne + entry.getValue().getBags();
				if (totalBags >= newSpadeGame.getBags()) {

					teamScore = teamScore - newSpadeGame.getOverBook();
					newSpadeGame.getTeams().get(entry.getKey()).setOverBook(true);
					newSpadeGame.getTeams().get(entry.getKey()).setBags(totalBags - newSpadeGame.getBags());
					newSpadeGame.getTeams().get(entry.getKey())
							.setTotalScore(newSpadeGame.getTeams().get(entry.getKey()).getTotalScore() + teamScore);
				}
			}
		}

	}

	private void determinePlayerPoints(SpadeGame newSpadeGame, SpadePlayer gameWinner) {

		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams().get(TeamTable.getTeam(player.getCode() % 2)).getPlayers()
					.get(PlayerTable.getPlayer(player.getCode()));

			if (spadePlayer.getName() == gameWinner.getName()) {

				spadePlayer.setWon(true);
				spadePlayer.setTurn(true);
				newSpadeGame.setCurrTrick(spadePlayer.getName());
				reAdjustCards(newSpadeGame);
			} else {
				spadePlayer.setWon(false);
				spadePlayer.setTurn(false);

			}
		}

	}

	private void determineGameWinner(SpadeGame newSpadeGame) {

		int maxScore = 0;
		boolean isEqual = false;
		TeamTable maxTeam = null;
		for (Entry<TeamTable, Team> entry : newSpadeGame.getTeams().entrySet()) {

			if (entry.getValue().getTotalScore() == maxScore) {

				if (maxScore >= newSpadeGame.getPointsToWin()) {
					isEqual = true;
				}
			} else {
				if (entry.getValue().getTotalScore() > maxScore) {
					maxScore = entry.getValue().getTotalScore();
					maxTeam = entry.getKey();
				}
			}

		}
		if (newSpadeGame.getTeams().get(maxTeam).getTotalScore() >= newSpadeGame.getPointsToWin()) {

			if (!isEqual) {

				newSpadeGame.getTeams().get(maxTeam).setWon(true);
				newSpadeGame.setGameOver(true);
			}

		}

	}

	private void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTrick();

		Card oldPlayingCard = newSpadeGame.getTeams().get(TeamTable.getTeam(player.getCode() % 2)).getPlayers()
				.get(player).getPlayingCard();

		newSpadeGame.getTeams().get(TeamTable.getTeam(player.getCode() % 2)).getPlayers().get(player)
				.getRemainingCards().remove(oldPlayingCard);

		newSpadeGame.getTeams().get(TeamTable.getTeam(player.getCode() % 2)).getPlayers().get(player)
				.setPlayingCard(null);

	}

	private void updateHandAndTrick(SpadeGame newSpadeGame) {

		if (newSpadeGame.getTrickCount() == 13 || newSpadeGame.getTrickCount() == 0) {

			newSpadeGame.setTrickCount(1);

			newSpadeGame.setHandCount(newSpadeGame.getHandCount() + 1);
			getStartHand(newSpadeGame);

			getStartTrick(newSpadeGame, true);

		} else {
			newSpadeGame.setTrickCount(newSpadeGame.getTrickCount() + 1);

			getStartTrick(newSpadeGame, false);
		}

	}

	private void getStartTrick(SpadeGame newSpadeGame, boolean isNewHand) {

		if (isNewHand) {
			int newStartCount = newSpadeGame.getStartHand().getCode();

			int newEndCount = newStartCount + 3;
			if (newEndCount > 4) {

				newEndCount = newEndCount - 4;
			}
			newSpadeGame.setStartTrick(newSpadeGame.getStartHand());
			newSpadeGame.setCurrTrick(newSpadeGame.getStartHand());
			newSpadeGame.setEndTrick(PlayerTable.getPlayer(newEndCount));
			// newSpadeGame.set

		} else {

			int newStartCount = newSpadeGame.getStartTrick().getCode() + 1;
			if (newStartCount > 4) {
				newStartCount = newStartCount - 4;
			}
			int newEndCount = newStartCount + 3;
			if (newEndCount > 4) {
				newEndCount = newEndCount - 4;
			}

			newSpadeGame.setStartTrick(PlayerTable.getPlayer(newStartCount));
			newSpadeGame.setCurrTrick(PlayerTable.getPlayer(newStartCount));
			newSpadeGame.setEndTrick(PlayerTable.getPlayer(newEndCount));

		}

	}

	private void getStartHand(SpadeGame newSpadeGame) {

		if (newSpadeGame.isStarting()) {

			int n = rand.nextInt(4) + 1;

			newSpadeGame.setStartHand(PlayerTable.getPlayer(n));
		} else {
			int playerCount = newSpadeGame.getStartHand().getCode();
			int newStartCount = playerCount + 1;
			if (newStartCount > 4) {
				newStartCount = newStartCount - 4;
			}

			newSpadeGame.setStartHand(PlayerTable.getPlayer(newStartCount));
		}

	}

}
