package com.craig.greggames.validator.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
@Service
@Order(3)
public class SpadeBidValidator extends AbstractSpadeValidator {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	private static final Logger logger = Logger.getLogger(SpadeBidValidator.class);

	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.BID);

	}
	@Override
	public boolean validate(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		logger.info("Entering " + getClass());
		if(!notificationSet.contains(spadeGame.getPlayerNotification())) {
			return true;
		}
		if (spadeGame.getGameModifier() == null) {

			return false;
		}

		SpadePlayer player = spadeGame.getTeams()
				.get(spadeTeamHandler.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getGameModifier());

		player.setError(false);
		player.getErrorMessages().clear();
		if (spadeGame.getGameModifier() != spadeGame.getCurrTurn()) {
			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());

			if (player.getPlayingCard() != null) {
				player.getRemainingCards().add(player.getPlayingCard());
			}
			player.setPlayingCard(null);
			player.setPlayerBid(0);
			return false;

		}

		if (!player.isTurn()) {

			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());

			if (player.getPlayingCard() != null) {
				player.getRemainingCards().add(player.getPlayingCard());
			}
			player.setPlayingCard(null);
			player.setPlayerBid(0);
			return false;
		} else {

			if (player.getPlayingCard() != null) {

				if(player.getPlayingCard().getName()!=null || player.getPlayingCard().getSuit()!=null
						|| player.getPlayingCard().getValue()!=null) {
					
					player.setError(true);
					if (player.getPlayingCard() != null) {
						player.getRemainingCards().add(player.getPlayingCard());
					}

					player.setPlayingCard(null);
					player.getErrorMessages().put(SpadeErrors.CURRENTLY_BIDDING, SpadeErrors.CURRENTLY_BIDDING.getError());
					player.setPlayerBid(0);
					return false;
				}
				
			}
		}

		logger.info("Exiting " + getClass());
		return true;
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		
		return notificationSet.contains(spadeNotification);
	
	}

}
