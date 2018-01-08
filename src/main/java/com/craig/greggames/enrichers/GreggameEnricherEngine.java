package com.craig.greggames.enrichers;

import com.craig.greggames.model.AbstractGreggame;
import com.craig.greggames.model.GregGameChildTypes;

public interface GreggameEnricherEngine <T extends AbstractGreggame> {

	
	public void enricherEngine(T greggame);
	
	public GregGameChildTypes getGregType();
}
