package com.craig.greggames.model.game.cards.spades.dao.repo;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;

public interface SpadeGameRepositoryCustom {
	
	public String updateGame(SpadeGameDAO spadeGameDAO);
	
	public SpadeGame findAndModify(SpadeGameDAO spadeGameDAO);
	
	public SpadeGameDAO updateLockingField(SpadeGameDAO spadeGameDAO);
	
	

}
