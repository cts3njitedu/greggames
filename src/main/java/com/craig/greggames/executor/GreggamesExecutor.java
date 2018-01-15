package com.craig.greggames.executor;

import com.craig.greggames.model.AbstractGreggame;

public interface GreggamesExecutor <T extends AbstractGreggame>{

	
	public T execute(T greggame);


}
