package com.craig.greggames.broadcast.game.cards.spades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.model.game.cards.spades.SpadeGame;

@Service
public class SpadeAsyncTaskExecutor {

	@Autowired
	private List<SpadeAsyncStateProcess> spadeAsyncStateProcesses;
	
	
	
	public void execute(SpadeGame spadeGame) {
		SpadeGame newGame = spadeGame;
		for(SpadeAsyncStateProcess spadeAsyncStateProcess: spadeAsyncStateProcesses) {
			if(newGame!=null) {
				if(spadeAsyncStateProcess.isValidAsyncTask(newGame.getAsyncState())) {
					newGame = spadeAsyncStateProcess.processAsyncTask(newGame);
				}
			}
			
		}
	}
}
