package com.craig.greggames.validator;

import com.craig.greggames.model.AbstractGreggame;
import com.craig.greggames.model.GregGameChildTypes;

public interface GreggameValidatorEngine <T extends AbstractGreggame> {

	
	public boolean validate(T greggame);
	
	public GregGameChildTypes getGreggameType();
}
