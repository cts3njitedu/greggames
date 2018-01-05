package com.craig.greggames.states.game.cards.spades;

import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.CardHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeBidderHandler;
import com.craig.greggames.handler.game.cards.spades.SpadePlayerHandler;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.model.game.cards.spades.dao.repo.SpadeGameRepository;
import com.craig.greggames.validator.GreggameValidatorFactory;

@Service
public class SpadeGameStartState extends AbstractSpadeGameState {

	
	
	@Autowired
	private CardHandler cardService;
	
	@Autowired
	private SpadeBidderHandler bidderService;
	
	@Autowired
	private SpadePlayerHandler playerService;
	
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	private final SpadeNotifications spadeNotification = SpadeNotifications.PLAY;

	@Override
	public void execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		if(spadeGame.getGameNotification()==SpadeNotifications.START) {
			
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
			
			spadePersistenceDal.saveGame(spadeGame);
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
