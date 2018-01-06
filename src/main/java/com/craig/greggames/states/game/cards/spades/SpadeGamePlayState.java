package com.craig.greggames.states.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TRICK_COUNT;
import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBidderHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeMetaDataHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(4)
public class SpadeGamePlayState extends AbstractSpadeGameState {

	@Autowired
	private SpadePlayerHandler playerService;
	@Autowired
	private CardHandler cardService;
	@Autowired
	private SpadeMetaDataHandler metaDataService;
	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadeBidderHandler bidderService;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;

	@Autowired
	private SpadeBotHandler botService;

	private final SpadeNotifications spadeNotification = SpadeNotifications.PLAY;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		switch (spadeGame.getPlayerNotification()) {

		case PLAY:
			if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

				// determine who won the trick
				playerService.determinePlayerWinner(spadeGame);
				// determine the amount of points the winner has
				playerService.determinePlayerPoints(spadeGame);
				// reAdjust cards. should return boolean
				cardService.reAdjustCards(spadeGame);
				PlayerTable temWinnerPlayer = spadeGame.getTempWinner();
				spadeGame.setTempWinner(null);
				metaDataService.addPreviousTrick(spadeGame);

				if (spadeGame.getTrickCount() == MAX_TRICK_COUNT) {

					// determine the total team score;
					teamService.determineTeamPoints(spadeGame);
					// determine if someone won the game;
					teamService.determineGameWinner(spadeGame);
					metaDataService.addPreviousHand(spadeGame);
					int start = spadeGame.getStartHand().getCode() + 1;
					if (start > MAX_TURN_PER_TRICK) {
						start = start - MAX_TURN_PER_TRICK;
					}
					spadeGame.setBidding(true);
					spadeGame.setPlaying(false);
					spadeGame.setGameNotification(SpadeNotifications.BID);
					spadeGame.setStartHand(PlayerTable.getPlayer(start));
					spadeGame.setStartTurn(spadeGame.getStartHand());
					spadeGame.setCurrTurn(spadeGame.getStartHand());

					cardService.distributeCards(spadeGame);
					teamService.cleanUpPoints(spadeGame);
					bidderService.cleanUpBid(spadeGame);

					spadeGame.setHandCount(spadeGame.getHandCount() + 1);
					spadeGame.setTrickCount(0);
					spadeGame.setTurnCount(1);
					spadeGame.setTrickOver(true);
					spadeGame.setHandOver(true);

				} else {

					spadeGame.setCurrTurn(temWinnerPlayer);
					spadeGame.setStartTurn(temWinnerPlayer);
					spadeGame.setTurnCount(1);
					spadeGame.setTrickCount(spadeGame.getTrickCount() + 1);
					spadeGame.setGameNotification(SpadeNotifications.PLAY);
					spadeGame.setTrickOver(true);

				}
				playerService.cleanUpWhoHasPlayed(spadeGame);
				cardService.removePlayingCard(spadeGame);

			} else {

				playerService.determinePlayerWinner(spadeGame);
				cardService.reAdjustCards(spadeGame);
				int curr = spadeGame.getCurrTurn().getCode() + 1;
				if (curr > MAX_TURN_PER_TRICK) {
					curr = curr - MAX_TURN_PER_TRICK;
				}
				spadeGame.setCurrTurn(PlayerTable.getPlayer(curr));
				spadeGame.setTurnCount(spadeGame.getTurnCount() + 1);
				spadeGame.setGameNotification(SpadeNotifications.PLAY);

			}
			playerService.determineTurn(spadeGame);
			return spadePersistenceDal.saveGame(spadeGame);
		case NEW_PLAYER:
			botService.determineBots(spadeGame);
			spadeGame.setNewPlayer(false);
			return spadePersistenceDal.saveGame(spadeGame);
		case RECEIVED_ERROR:
			playerService.cleanUpError(spadeGame);
			return spadePersistenceDal.saveGame(spadeGame);
		default:
			SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
			playerService.addError(spadeGame, SpadeErrors.CURRENTLY_PLAYING, oldSpadeGame);
			return spadePersistenceDal.saveGame(spadeGame);

		}

	}

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		if (spadeNotification == spadeNotifications) {
			return true;
		}
		return false;
	}

}
