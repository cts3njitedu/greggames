package com.craig.greggames.validator.game.cards.spades;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.validator.GreggameValidatorEngine;

@Service
public class SpadeValidatorEngine implements GreggameValidatorEngine<SpadeGame> {

	@Autowired
	private List<SpadeValidator> abstractSpadeValidators;
	
	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	
	private static final Logger logger = Logger.getLogger(SpadeValidatorEngine.class);
	
	@Override
	public boolean validate(SpadeGame spadeGame) {
		boolean isValid = true;
		logger.info("Entering " + getClass());
		for(SpadeValidator abstractSpadeValidator : abstractSpadeValidators) {
			
			if(abstractSpadeValidator.validateState(spadeGame.getGameNotification())) {
				
				isValid = isValid && abstractSpadeValidator.validate(spadeGame);
			}
			
			
		}
		logger.info("Exiting "+ getClass());
		return isValid;
	}

	@Override
	public GregGameChildTypes getGreggameType() {
		// TODO Auto-generated method stub
		return greggame;
	}

	
	
	
	
	
}
