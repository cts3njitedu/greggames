package com.craig.greggames.states.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBidderHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(2)
public class SpadeGameStartState extends AbstractSpadeGameState {

	@Autowired
	private CardHandler cardService;

	@Autowired
	private SpadeBidderHandler bidderService;

	@Autowired
	private SpadePlayerHandler playerService;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBotHandler botService;

	private final SpadeNotifications spadeNotification = SpadeNotifications.START;

	@Override
	public SpadeGame state(SpadeGame spadeGame) {
		// TODO Auto-generated method stub

		switch (spadeGame.getPlayerNotification()) {

		case START:
			Random rand = new Random();
			int start = rand.nextInt(MAX_TURN_PER_TRICK) + 1;

			spadeGame.setStartTurn(PlayerTable.getPlayer(start));
			spadeGame.setStartHand(PlayerTable.getPlayer(start));
			spadeGame.setCurrTurn(PlayerTable.getPlayer(start));
			spadeGame.setHandCount(1);
			cardService.distributeCards(spadeGame);
			spadeGame.setSpadePlayed(false);
			spadeGame.setGameNotification(SpadeNotifications.BID);
			spadeGame.setTurnCount(1);
			bidderService.cleanUpBid(spadeGame);
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
			playerService.addError(spadeGame, SpadeErrors.WRONG_MOVE, oldSpadeGame);
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
