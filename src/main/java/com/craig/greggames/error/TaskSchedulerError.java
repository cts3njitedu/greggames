package com.craig.greggames.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ErrorHandler;

import com.craig.greggames.broadcast.game.cards.spades.SpadeGameBroadCaster;


public class TaskSchedulerError implements ErrorHandler{

	@Autowired
	private SpadeGameBroadCaster spadeGameBroadCaster;
	@Override
	public void handleError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
