package com.craig.greggames.model.game.cards.spades.dao.repo;



import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.craig.greggames.model.game.cards.player.Player;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;
import com.craig.greggames.util.GregMapper;
import com.craig.greggames.util.UpdateGenerator;


public class SpadeGameRepositoryImpl implements SpadeGameRepositoryCustom{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private GregMapper gregMapper;
	
	@Autowired
	private UpdateGenerator updateGenerator;
	@Override
	public SpadeGameDAO updateGame(SpadeGameDAO spadeGameDAO,Set<String>excludedFields,Set<String>makeEmptyIfNull) {
		// TODO Auto-generated method stub
		
		Query query = new Query(Criteria.where("gameId").is(spadeGameDAO.getGameId()));
		
		Map<?,?> map = gregMapper.convertObjectToMap(spadeGameDAO);
		
		Update update = new Update();
		
		updateGenerator.makeUpdateFields(update, map, excludedFields,makeEmptyIfNull);
		
		mongoTemplate.updateFirst(query, update, SpadeGameDAO.class);
		
		SpadeGameDAO newSpadeGameDao = mongoTemplate.findOne(query, SpadeGameDAO.class);
		return newSpadeGameDao;
	
	}

	@Override
	public SpadeGame findAndModify(SpadeGameDAO spadeGameDAO) {
		Query query = new Query(Criteria.where("versionNb").is(spadeGameDAO.getVersionNb()));
		Update update = new Update();
		
		return null;
		
	}

	@Override
	public SpadeGameDAO updateLockingField(SpadeGameDAO spadeGameDAO) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("gameId").is(spadeGameDAO.getGameId()).and("lock").is(false)
				.and("trickCount").is(spadeGameDAO.getTrickCount()).and("turnCount").is(spadeGameDAO.getTurnCount())
				.and("handCount").is(spadeGameDAO.getHandCount()));
		
		Update update = new Update();
		update.set("lock",true);
		
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		findAndModifyOptions.returnNew(true);
		return mongoTemplate.findAndModify(query, update, findAndModifyOptions,SpadeGameDAO.class);
	}


}
