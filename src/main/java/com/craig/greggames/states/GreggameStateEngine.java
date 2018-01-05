package com.craig.greggames.states;

import com.craig.greggames.model.AbstractGreggame;
import com.craig.greggames.model.GregGameChildTypes;

public interface GreggameStateEngine<T extends AbstractGreggame> {

	public void execute(T greggame);

	public GregGameChildTypes gregGameChildTypes();

}
