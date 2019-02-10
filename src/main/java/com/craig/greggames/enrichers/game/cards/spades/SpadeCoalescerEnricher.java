package com.craig.greggames.enrichers.game.cards.spades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;

@Service
public class SpadeCoalescerEnricher extends AbstractSpadeGameEnricher {

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	private final static Set<SpadeNotifications> notificationSet;
	static {

		notificationSet = new HashSet<SpadeNotifications>();

		notificationSet.add(SpadeNotifications.BID);
		notificationSet.add(SpadeNotifications.PLAY);
		notificationSet.add(SpadeNotifications.START);
	
		
	

	}
	@Override
	public void enricher(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		

		SpadeGame oldSpadeGame = spadePersistenceDal.findGame(spadeGame.getGameId());
		//spadeGame.setGameNotification(oldSpadeGame.getGameNotification());
		spadeGame.setPointsToWin(oldSpadeGame.getPointsToWin());
		spadeGame.setPointsToLose(oldSpadeGame.getPointsToLose());
		spadeGame.setBidNilPoints(oldSpadeGame.getBidNilPoints());
		spadeGame.setBags(oldSpadeGame.getBags());
		spadeGame.setBagPoints(oldSpadeGame.getBagPoints());
		spadeGame.setNumberOfTeams(oldSpadeGame.getNumberOfTeams());
		
		
		
		
	}
	@Override
	public boolean validateState(SpadeNotifications spadeNotification) {
		// TODO Auto-generated method stub
		return notificationSet.contains(spadeNotification);
	}

}
