package com.craig.greggames.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import com.craig.greggames.model.TeamTable;
import com.craig.greggames.model.card.Card;
import com.craig.greggames.model.card.CardSuit;
import com.craig.greggames.model.player.PlayerTable;
import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.SpadePlayer;
import com.craig.greggames.model.spades.SpadeTeam;

public class SpadeGameCreator {

	private Random rand = new Random();

	public void execute(SpadeGame newSpadeGame) {

		playGame(newSpadeGame);

	}

	private void playGame(SpadeGame newSpadeGame) {

		if (newSpadeGame.isStarting()) {

			makeTeams(newSpadeGame);
			int start = rand.nextInt(4) + 1;
			newSpadeGame.setBidding(true);
			newSpadeGame.setStartTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
			newSpadeGame.setCurrTurn(PlayerTable.getPlayer(start));
			newSpadeGame.setHandCount(1);
			distributeCards(newSpadeGame);
			newSpadeGame.setStarting(false);
			newSpadeGame.setTurnCount(1);
			cleanUpBid(newSpadeGame);

		}

		else if (newSpadeGame.isBidding()) {

			// check the turn count. if 4 set bidding to false;

			determineBid(newSpadeGame);
			if (newSpadeGame.getTurnCount() == 4) {

				newSpadeGame.setCurrTurn(newSpadeGame.getStartTurn());
				newSpadeGame.setBidding(false);

				newSpadeGame.setTurnCount(1);
				newSpadeGame.setTrickCount(1);
			} else {

				int currTurnCode = newSpadeGame.getCurrTurn().getCode() + 1;

				if (currTurnCode > 4) {
					currTurnCode = currTurnCode - 4;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);
			}

		} else {

			if (newSpadeGame.getTurnCount() == 4) {

				// determine who won the trick
				determinePlayerWinner(newSpadeGame);
				// determine the amount of points the winner has
				determinePlayerPoints(newSpadeGame);
				// reAdjust cards. should return boolean
				reAdjustCards(newSpadeGame);
				PlayerTable temWinnerPlayer = newSpadeGame.getTempWinner();
				newSpadeGame.setTempWinner(null);

				if (newSpadeGame.getTrickCount() == 13) {

					// determine the total team score;
					determineTeamPoints(newSpadeGame);
					// determine if someone won the game;
					determineGameWinner(newSpadeGame);
					int start = newSpadeGame.getStartHand().getCode() + 1;
					if (start > 4) {
						start = start - 4;
					}
					newSpadeGame.setBidding(true);
					newSpadeGame.setStartHand(PlayerTable.getPlayer(start));
					newSpadeGame.setStartTurn(newSpadeGame.getStartHand());
					newSpadeGame.setCurrTurn(newSpadeGame.getStartHand());

					distributeCards(newSpadeGame);
					cleanUpPoints(newSpadeGame);
					cleanUpBid(newSpadeGame);

					newSpadeGame.setHandCount(newSpadeGame.getHandCount() + 1);
					newSpadeGame.setTrickCount(0);
					newSpadeGame.setTurnCount(1);

				} else {

					newSpadeGame.setCurrTurn(temWinnerPlayer);
					newSpadeGame.setStartTurn(temWinnerPlayer);
					newSpadeGame.setTurnCount(1);
					newSpadeGame.setTrickCount(newSpadeGame.getTrickCount() + 1);

				}
				removePlayingCard(newSpadeGame);

				if (newSpadeGame.isGameOver()) {
					// determineWinnersAndLosers(newSpadeGame);
				}
			} else {

				reAdjustCards(newSpadeGame);
				determinePlayerWinner(newSpadeGame);
				int curr = newSpadeGame.getCurrTurn().getCode() + 1;
				if (curr > 4) {
					curr = curr - 4;
				}
				newSpadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
				newSpadeGame.setTurnCount(newSpadeGame.getTurnCount() + 1);

			}

		}

	}

	private void makeTeams(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			TeamTable team = TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams());

			SpadePlayer spadePlayer = new SpadePlayer();
			spadePlayer.setTeam(team);
			spadePlayer.setName(player);
			spadePlayer.setBidding(true);

			newSpadeGame.getTeams().get(team).getPlayers().put(player, spadePlayer);
			newSpadeGame.getTeams().get(team).setName(team);
		}

	}

	private void distributeCards(SpadeGame newSpadeGame) {

		List<Card> cards = DealUtility.getSpadeHand();
		int i = 0;
		int dealCode = newSpadeGame.getCurrTurn().getCode() + 1;
		if (dealCode > 4) {
			dealCode = dealCode - 4;
		}

		for (int turn = 1; turn <= 4; turn++) {

			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(dealCode, newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(PlayerTable.getPlayer(dealCode)).getRemainingCards()
					.addAll(cards.subList(i, i + 13));
			dealCode++;
			if (dealCode > 4) {
				dealCode = dealCode - 4;
			}
			i = i + 13;
		}

	}

	private void determineBid(SpadeGame newSpadeGame) {

		SpadeTeam team = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(newSpadeGame.getCurrTurn().getCode(), newSpadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(newSpadeGame.getCurrTurn());
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;

		int totalBid = 0;
		if (player.isBot()) {

			List<Card> cards = player.getRemainingCards();
			for (Card card : cards) {

				switch (card.getSuit()) {

				case HEARTS:
					if (card.getValue().getValue() >= 13) {
						hearts++;
					}
					break;

				case SPADES:

					if (card.getValue().getValue() >= 9) {
						spades++;
					}

					break;
				case DIAMONDS:
					if (card.getValue().getValue() >= 13) {
						diamonds++;
					}
					break;
				case CLUBS:
					if (card.getValue().getValue() >= 13) {
						clubs++;
					}
					break;
				default:
					break;
				}

			}
			totalBid = 10 * (hearts + spades + diamonds + clubs);

		}
		team.setTotalBid(team.getTotalBid() + totalBid);
		team.getPlayers().get(newSpadeGame.getCurrTurn()).setPlayerBid(totalBid);

	}

	public void determinePlayerWinner(SpadeGame newSpadeGame) {

		int code = newSpadeGame.getCurrTurn().getCode();

		SpadePlayer currPlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(code, newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(PlayerTable.getPlayer(code));

		if (newSpadeGame.getTempWinner() == null) {

			currPlayer.setWon(true);
			newSpadeGame.setTempWinner(currPlayer.getName());

		} else {

			SpadeTeam spadeTeam = newSpadeGame.getTeams().get(
					TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()));

			SpadePlayer gameWinner = spadeTeam.getPlayers().get(newSpadeGame.getTempWinner());
			Card gameWinnerCard = gameWinner.getPlayingCard();
			Card currPlayerCard = currPlayer.getPlayingCard();
			if (gameWinnerCard.getSuit() == currPlayerCard.getSuit()) {
				if (currPlayerCard.getValue().getValue() > gameWinnerCard.getValue().getValue()) {

					currPlayer.setWon(true);
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {
					currPlayer.setWon(false);
				}
			} else {

				if (currPlayerCard.getSuit() == CardSuit.SPADES) {
					currPlayer.setWon(true);
					gameWinner.setWon(false);
					newSpadeGame.setTempWinner(currPlayer.getName());

				} else {

					currPlayer.setWon(false);
				}
			}
		}

	}

	private void determinePlayerPoints(SpadeGame newSpadeGame) {

		SpadePlayer spadePlayer = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(newSpadeGame.getTempWinner().getCode(), newSpadeGame.getNumberOfTeams()))
				.getPlayers().get(newSpadeGame.getTempWinner());

		spadePlayer.setWon(true);
		spadePlayer.setTurn(true);
		spadePlayer.setPlayerCurrentScore(spadePlayer.getPlayerCurrentScore() + 10);

		newSpadeGame.setStartTurn(spadePlayer.getName());

	}

	private void determineTeamPoints(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {
			int teamScore = 0;
			int totalOverBags = 0;

			for (Entry<PlayerTable, SpadePlayer> entryPlayer : entry.getValue().getPlayers().entrySet()) {

				int books = entryPlayer.getValue().getPlayerCurrentScore() - entryPlayer.getValue().getPlayerBid();

				if (books > 0) {
					if (entryPlayer.getValue().getPlayerBid() == 0) {

						teamScore = teamScore - newSpadeGame.getBidNilPoints() + books / 10;
						totalOverBags = totalOverBags + books;

						entryPlayer.getValue().setPlayerFinalScore(newSpadeGame.getBidNilPoints() + books / 10);

					} else {
						totalOverBags = totalOverBags + books;
						entryPlayer.getValue()
								.setPlayerFinalScore(entryPlayer.getValue().getPlayerBid() + (books) / 10);
						teamScore = teamScore + entryPlayer.getValue().getPlayerBid() + (books) / 10;
					}
				} else if (books < 0) {

					teamScore = teamScore - entryPlayer.getValue().getPlayerBid();
					entryPlayer.getValue().setPlayerFinalScore(entryPlayer.getValue().getPlayerBid());
				} else {

					teamScore = teamScore + entryPlayer.getValue().getPlayerBid();
					entryPlayer.getValue().setPlayerFinalScore(entryPlayer.getValue().getPlayerBid());
				}

			}

			int teamTotalBags = totalOverBags + entry.getValue().getBags();
			if (teamTotalBags >= newSpadeGame.getBags()) {
				teamScore = teamScore - newSpadeGame.getBagPoints();
				newSpadeGame.getTeams().get(entry.getKey()).setOverBook(true);
				newSpadeGame.getTeams().get(entry.getKey()).setBags(teamTotalBags - newSpadeGame.getBags());

			} else {
				newSpadeGame.getTeams().get(entry.getKey()).setBags(teamTotalBags);

			}

			newSpadeGame.getTeams().get(entry.getKey())
					.setTotalScore(newSpadeGame.getTeams().get(entry.getKey()).getTotalScore() + teamScore);

		}

	}

	private void determineGameWinner(SpadeGame newSpadeGame) {

		// List<SpadeTeam> spadeTeams = new ArrayList<SpadeTeam>();


		SpadeTeam maxScoreTeam = newSpadeGame.getTeams().values().stream().max(Comparator.comparing(SpadeTeam::getTotalScore)).get();

		SpadeTeam minScoreTeam = newSpadeGame.getTeams().values().stream().min(Comparator.comparing(SpadeTeam::getTotalScore)).get();

		if (maxScoreTeam.getTotalScore() >= newSpadeGame.getPointsToWin()) {

			for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

				if (entry.getValue().getTotalScore() == maxScoreTeam.getTotalScore()) {

					entry.getValue().setWon(true);
				} else {
					entry.getValue().setWon(false);
				}
			}
			newSpadeGame.setGameOver(true);

		}

		else if (minScoreTeam.getTotalScore() <= newSpadeGame.getPointsToLose()) {

			for (Entry<TeamTable, SpadeTeam> entry : newSpadeGame.getTeams().entrySet()) {

				if (entry.getValue().getTotalScore() == maxScoreTeam.getTotalScore()) {

					entry.getValue().setWon(true);
				} else {
					entry.getValue().setWon(false);
				}
			}
			newSpadeGame.setGameOver(true);

		}

	}

	private void reAdjustCards(SpadeGame newSpadeGame) {

		PlayerTable player = newSpadeGame.getCurrTurn();

		Card oldPlayingCard = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()

				.get(player).getPlayingCard();

		List<Card> cardsLeft = newSpadeGame.getTeams()
				.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()
				.get(player).getRemainingCards();

		cardsLeft.remove(oldPlayingCard);

	}

	private void removePlayingCard(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			newSpadeGame.getTeams().get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams()))
					.getPlayers().get(player).setPlayingCard(null);

		}
	}

	private void cleanUpPoints(SpadeGame newSpadeGame) {

		for (PlayerTable player : PlayerTable.values()) {

			SpadePlayer spadePlayer = newSpadeGame.getTeams()
					.get(TeamTable.getTeamByPlayer(player.getCode(), newSpadeGame.getNumberOfTeams())).getPlayers()
					.get(player);

			spadePlayer.setPlayerCurrentScore(0);
			spadePlayer.setPlayerBid(0);

		}

	}

	private void cleanUpBid(SpadeGame newSpadeGame) {

		for (Entry<TeamTable, SpadeTeam> team : newSpadeGame.getTeams().entrySet()) {

			team.getValue().setTotalBid(0);
		}
	}

	private void determineWinnersAndLosers(SpadeGame newSpadeGame) {

		for (TeamTable team : TeamTable.values()) {

			SpadeTeam spadeTeam = newSpadeGame.getTeams().get(team);
			if (spadeTeam != null) {
				if (true != spadeTeam.isLost()) {
					spadeTeam.setLost(false);
				}

				if (true != spadeTeam.isWon()) {

					spadeTeam.setWon(false);
				}

			}

		}

	}

}
