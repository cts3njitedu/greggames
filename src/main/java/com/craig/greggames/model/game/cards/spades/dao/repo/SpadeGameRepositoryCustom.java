package com.craig.greggames.model.game.cards.spades.dao.repo;

import java.util.Set;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;

public interface SpadeGameRepositoryCustom {
	
	public SpadeGameDAO updateGame(SpadeGameDAO spadeGameDAO,Set<String>excludedFields,Set<String>makeEmptyIfNull);
	
	public SpadeGame findAndModify(SpadeGameDAO spadeGameDAO);
	
	public SpadeGameDAO updateLockingField(SpadeGameDAO spadeGameDAO);
	
	

}
