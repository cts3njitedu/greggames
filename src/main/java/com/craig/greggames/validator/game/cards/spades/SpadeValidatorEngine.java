package com.craig.greggames.validator.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.GregGameChildTypes;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.validator.GreggameValidatorEngine;

@Service
public class SpadeValidatorEngine implements GreggameValidatorEngine<SpadeGame> {

	@Autowired
	private List<AbstractSpadeValidator> abstractSpadeValidators;
	
	private final GregGameChildTypes greggame = GregGameChildTypes.SPADES;
	
	@Override
	public boolean validate(SpadeGame spadeGame) {
		boolean isValid = true;
		for(AbstractSpadeValidator abstractSpadeValidator : abstractSpadeValidators) {
			
			if(abstractSpadeValidator.validateState(spadeGame.getGameNotification())) {
				
				isValid = isValid && abstractSpadeValidator.validate(spadeGame);
			}
			
			
		}
		return isValid;
	}

	@Override
	public GregGameChildTypes getGreggameType() {
		// TODO Auto-generated method stub
		return greggame;
	}

	
	
	
	
	
}
