package com.craig.greggames.executor.game.cards.spades;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.enrichers.game.cards.spades.SpadeBroadCasterEnricher;
import com.craig.greggames.enrichers.game.cards.spades.SpadeGameEnricherEngine;
import com.craig.greggames.executor.game.cards.CardGameExecutor;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
import com.craig.greggames.model.game.cards.spades.dal.SpadePersistenceDal;
import com.craig.greggames.states.game.cards.spades.SpadeGameStateEngine;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

@Service
public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {


	

	
	@Autowired
	private SpadeGameStateEngine stateEngine;
	
	@Autowired
	private SpadeGameEnricherEngine enricherEngine;
	
	@Autowired
	private SpadeValidatorEngine validatorEngine;

	@Autowired
	private SpadePersistenceDal spadePersistenceDal;
	
	@Autowired
	private SpadeBroadCasterEnricher spadeBroadCasterEnricher;
	
	

	@Override
	public SpadeGame execute(SpadeGame spadeGame) {
		// TODO Auto-generated method stub
		if(SpadeNotifications.BID==spadeGame.getPlayerNotification() || SpadeNotifications.PLAY==spadeGame.getPlayerNotification()) {
			spadePersistenceDal.updateLockingField(spadeGame);
		}
		
		if(spadeGame.isLock()) {
			return spadeGame;
		}
		
		enricherEngine.enricherEngine(spadeGame);
		
		boolean isValid = validatorEngine.validate(spadeGame);
		
		//stop before going
		if(isValid) {
			return stateEngine.machine(spadeGame);
		}
		else {
			return spadePersistenceDal.saveGame(spadeGame);
		}
		

		
		//

	}


}
