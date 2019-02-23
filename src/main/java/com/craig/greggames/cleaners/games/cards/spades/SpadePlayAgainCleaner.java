package com.craig.greggames.cleaners.games.cards.spades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;

@Service
public class SpadePlayAgainCleaner extends AbstractSpadeGameCleaner {

	@Autowired
	private SpadeTeamHandler spadeTeamHandler;

	@Override
	public void clean(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		if(spadeGame.getPlayerNotification()==SpadeNotifications.NEW_PLAYER) {
			SpadePlayer spadePlayer = spadeGame.getTeams().get(spadeTeamHandler.getTeamByPlayer(spadeGame.getGameModifier(), spadeGame.getNumberOfTeams()))
					.getPlayers().get(spadeGame.getGameModifier());
			spadePlayer.setPlayAgain(false);
		}
	

	}

}
