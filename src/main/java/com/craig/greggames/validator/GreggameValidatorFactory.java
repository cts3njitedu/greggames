package com.craig.greggames.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.AbstractGreggame;
import com.craig.greggames.model.GregGameChildTypes;

@Service
public class GreggameValidatorFactory <T extends AbstractGreggame>{

	
	@Autowired
	private List<GreggameValidatorEngine<T>> validatorEngines;
	
	
	public GreggameValidatorEngine<T> engine(GregGameChildTypes gregGameChildType) {
		
		
		for(GreggameValidatorEngine<T> validatorEngine: validatorEngines) {
			
			if(validatorEngine.getGreggameType()==gregGameChildType) {
				
				return validatorEngine;
			}
		}
		return null;
	}
	
	
	
}
