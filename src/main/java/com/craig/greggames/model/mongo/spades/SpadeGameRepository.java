package com.craig.greggames.model.mongo.spades;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpadeGameRepository extends MongoRepository<SpadeGameDAO,String>{
	
	

}
