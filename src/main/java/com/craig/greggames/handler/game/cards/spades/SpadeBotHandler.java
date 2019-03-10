package com.craig.greggames.handler.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.POINTS_WON_PER_TRICK_BEFORE_OVERBID;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.bot.game.cards.spades.SpadeBotPlayerPositionEngine;
import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.CardValue;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.SpadeTeam;
import com.craig.greggames.util.GregMapper;

@Service
public class SpadeBotHandler {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Autowired
	private CardHandler cardHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeBotHandler.class);
	
	@Autowired
	private GregMapper gregMapper;
	@Autowired
	private SpadeBotPlayerPositionEngine spadeBotPlayerPositionEngine;
	
	

	public int getBotBid(SpadeGame spadeGame) {
		logger.info("Bot is bidding");
		int spades = 0;
		int clubs = 0;
		int diamonds = 0;
		int hearts = 0;
		SpadeTeam team = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams()));

		SpadePlayer player = team.getPlayers().get(spadeGame.getCurrTurn());
		Set<Card> cards = player.getRemainingCards().stream().collect(Collectors.toSet());
		for (Card card : cards) {

			switch (card.getSuit()) {

			case HEARTS:
				if (card.getValue().getValue() >= CardValue.QUEEN.getValue()) {
					hearts++;
				}
				break;

			case SPADES:

				if (card.getValue().getValue() >= CardValue.JACK.getValue()) {
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
		
		logger.info("spades: " + spades + ", hearts: "+ hearts + ", clubs: "+ clubs + ", diamonds: " + diamonds );
		player.setPlayerBid(POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs));
		return POINTS_WON_PER_TRICK_BEFORE_OVERBID * (hearts + spades + diamonds + clubs);

	}

	public void determineBotCard(SpadeGame spadeGame) {
		
		logger.info("Determining bot card");
		SpadePlayer player = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getCurrTurn(), spadeGame.getNumberOfTeams())).getPlayers().get(spadeGame.getCurrTurn());
		if(spadeGame.isServerPlaying()) {
			logger.info("Server is playing");
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
		logger.info("Spade game metadata: " + gregMapper.convertObjectToString(spadeGameMetaData));
		int playerTrickPosition = spadeTeamHandler.getTrickPlayerPosition(leadPlayer.getName(), currPlayer.getName());

		Card card = spadeBotPlayerPositionEngine.botPlayerEngine(spadeGameMetaData, newSpadeGame, playerTrickPosition);

		logger.info("Playing card: " + gregMapper.convertObjectToString(card) + ", for player "+ currPlayer.getName());
		return card;
	}

	public void determineBots(SpadeGame newSpadeGame) {

		logger.info("Determining Bots");
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
