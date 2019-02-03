package com.craig.greggames.model.game.cards.spades.dao.repo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.expression.spel.ast.BooleanLiteral;

import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.dao.SpadeGameDAO;


public class SpadeGameRepositoryImpl implements SpadeGameRepositoryCustom{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public String updateGame(SpadeGameDAO spadeGameDAO) {
		// TODO Auto-generated method stub
		//Query query = new Query(Criteria.where("gameId").is(o))
		return null;
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
