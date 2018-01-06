package com.craig.greggames.states;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.AbstractGreggame;

@Service
public class GreggameStateFactory <T extends AbstractGreggame> {
	
	@Autowired
	private List<GreggameStateEngine<T>> engines;
	
	
	
	public T executeMachine(T greggame){
		
		
		for(GreggameStateEngine<T>engine:engines) {
			
			if(engine.gregGameChildTypes()==greggame.getGameType()) {
				
				return engine.machine(greggame);
		
			}
		}
		return null;
	}

}
