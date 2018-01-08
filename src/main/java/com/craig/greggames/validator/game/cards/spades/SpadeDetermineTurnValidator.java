package com.craig.greggames.validator.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeErrors;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
@Order(2)
public class SpadeDetermineTurnValidator extends AbstractSpadeValidator {

	@Autowired
	private SpadeTeamHandler teamService;
	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.PLAY);
		notificationSet.add(SpadeNotifications.BID);

	}
	@Override
	public boolean validate(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
	
		SpadeGame prevSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
		SpadePlayer player = spadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getGameModifier());

		SpadePlayer prevPlayer = prevSpadeGame.getTeams()
				.get(teamService.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
				.getPlayers().get(spadeGame.getGameModifier());
		player.setError(false);
		player.getErrorMessages().clear();
		if (spadeGame.getGameModifier() != spadeGame.getCurrTurn()) {
			player.setError(true);

			player.getErrorMessages().put(SpadeErrors.NOT_YOUR_TURN, SpadeErrors.NOT_YOUR_TURN.getError());

			if (prevPlayer.getPlayingCard() != null) {

				player.getRemainingCards().add(player.getPlayingCard());
				player.setPlayingCard(prevPlayer.getPlayingCard());

			} else {

				if(player.getPlayingCard()!=null) {
					player.getRemainingCards().add(player.getPlayingCard());
				}
				
				player.setPlayingCard(null);
			}

			return false;

		}
	

		return true;
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		if(notificationSet.contains(spadeNotification)) {
			return true;
		}
		return false;
	}

}
