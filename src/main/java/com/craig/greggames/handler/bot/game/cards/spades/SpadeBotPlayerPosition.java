package com.craig.greggames.handler.bot.game.cards.spades;

import com.craig.greggames.model.game.cards.Card;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadeGameMetaData;

public interface SpadeBotPlayerPosition {

	
	public Card getCard(SpadeGameMetaData spadeGameMetaData, SpadeGame spadeGame);
	
    public boolean validatePosition(int position);
}
