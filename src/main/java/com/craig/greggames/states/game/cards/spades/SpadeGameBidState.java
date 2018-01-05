package com.craig.greggames.states.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBidderHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.validator.GreggameValidatorEngine;
import com.craig.greggames.validator.GreggameValidatorFactory;

@Service
public class SpadeGameBidState extends AbstractSpadeGameState {

	@Autowired
	private SpadeBidderHandler bidderService;
	@Autowired
	private SpadeBotHandler botService;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	@Autowired
	private SpadePlayerHandler playerService;

	@Autowired
	private GreggameValidatorFactory<SpadeGame> validatoryFactory;
	
	private final SpadeNotifications spadeNotification = SpadeNotifications.BID;

	@Override
	public void execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		if (spadeGame.getGameNotification() == SpadeNotifications.BID) {

			switch (spadeGame.getPlayerNotification()) {

			case BID:
				GreggameValidatorEngine<SpadeGame> validatorEngine = validatoryFactory.engine(gregGameChildTypes);
				boolean isValid = validatorEngine.validate(spadeGame);
				if (!isValid) {
					spadePersistenceDal.saveGame(spadeGame);
					break;
				}
				bidderService.determineBid(spadeGame);
				spadeGame.setSpadePlayed(false);
				if (spadeGame.getTurnCount() == MAX_TURN_PER_TRICK) {

					spadeGame.setCurrTurn(spadeGame.getStartTurn());

					spadeGame.setGameNotification(SpadeNotifications.PLAY);

					spadeGame.setTurnCount(1);
					spadeGame.setTrickCount(1);
				} else {

					int currTurnCode = spadeGame.getCurrTurn().getCode() + 1;

					if (currTurnCode > MAX_TURN_PER_TRICK) {
						currTurnCode = currTurnCode - MAX_TURN_PER_TRICK;
					}
					spadeGame.setCurrTurn(PlayerTable.getPlayer(currTurnCode));
					spadeGame.setTurnCount(spadeGame.getTurnCount() + 1);
					spadeGame.setGameNotification(SpadeNotifications.BID);
				}

				playerService.determineTurn(spadeGame);
				spadePersistenceDal.saveGame(spadeGame);
				break;
			case NEW_PLAYER:
				botService.determineBots(spadeGame);
				spadeGame.setNewPlayer(false);
				spadePersistenceDal.saveGame(spadeGame);
				break;
			case RECEIVED_ERROR:
				playerService.cleanUpError(spadeGame);
				spadePersistenceDal.saveGame(spadeGame);
				break;
			default:
				SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
				playerService.addError(spadeGame, SpadeErrors.CURRENTLY_BIDDING, oldSpadeGame);
				spadePersistenceDal.saveGame(spadeGame);
				break;

			}

		} 

	}

	

	@Override
	public boolean validateState(SpadeNotifications spadeNotifications) {
		// TODO Auto-generated method stub
		if(spadeNotification == spadeNotifications) {
			return true;
		}
		return false;
	}

}
