package com.craig.greggames.model.game.cards.spades.dao.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;

public interface SpadeGameRepository extends MongoRepository<SpadeGameDAO,String>, SpadeGameRepositoryCustom{
	
	

}
