package com.craig.greggames.model.game.cards.spades.dao.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;

public interface SpadeGameRepository extends MongoRepository<SpadeGameDAO,String>, SpadeGameRepositoryCustom{
	
	

}
