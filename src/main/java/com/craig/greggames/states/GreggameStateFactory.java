package com.craig.greggames.states;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.craig.greggames.model.AbstractGreggame;
import com.craig.greggames.model.GregGameChildTypes;

public class GreggameStateFactory <T extends AbstractGreggame> {
	
	@Autowired
	private List<GreggameStateEngine<T>> gameStates;
	
	
	
	public GreggameStateEngine<T> getState(GregGameChildTypes gregGameChildTypes){
		
		return null;
		
	}

}
