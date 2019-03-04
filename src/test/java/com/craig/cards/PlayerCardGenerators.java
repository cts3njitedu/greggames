package com.craig.cards;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craig.greggames.handler.game.cards.spades.SpadeBotHandler;
import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
import com.craig.greggames.model.game.cards.Deck;
import com.craig.greggames.model.game.cards.player.PlayerTable;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.model.game.cards.spades.SpadePlayer;
import com.craig.greggames.model.game.cards.team.TeamTable;
import com.craig.greggames.util.GregMapper;

@Service
public class PlayerCardGenerators {

	
	@Autowired
	private GregMapper gregMapper;
	
	@Autowired
	private SpadeBotHandler botHandler;
	
	@Autowired
	private SpadeTeamHandler spadeTeamHandler;
	
	public void leadPlayerBidNil(SpadeGame spadeGame) {
		spadeGame.setServerPlaying(true);
		spadeGame.setStartTurn(PlayerTable.PLAYER1);
		spadeGame.setCurrTurn(PlayerTable.PLAYER1);
		spadeGame.setSpadePlayed(false);
		SpadePlayer spadePlayer1=null;
		TeamTable team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER1, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer1.setPlayingCard(null);
		spadePlayer1.setBidNil(false);
//		spadePlayer1.setRemainingCards(Arrays.asList(Deck.SIX_DIAMONDS.getCard(),Deck.JACK_HEARTS.getCard(),Deck.NINE_CLUBS.getCard(),Deck.TEN_SPADES.getCard()));
		spadePlayer1.setRemainingCards(Arrays.asList(Deck.SIX_SPADES.getCard(),Deck.ACE_SPADES.getCard()));
		
		SpadePlayer spadePlayer2 = null;
		TeamTable team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER2, spadeGame.getNumberOfTeams());
		spadePlayer2 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER2);
		spadePlayer2.setPlayingCard(null);
		
	
		SpadePlayer spadePlayer3=null;
		team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER3, spadeGame.getNumberOfTeams());
		spadePlayer3 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER3);
		spadePlayer3.setPlayingCard(null);
		
		SpadePlayer spadePlayer4 = null;
		team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER4, spadeGame.getNumberOfTeams());
		spadePlayer4 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER4);
		spadePlayer4.setPlayingCard(null);
		
	}
	
	public void leadPlayerDonotBidNilButTeammateDoes(SpadeGame spadeGame) {
		spadeGame.setServerPlaying(true);
		spadeGame.setStartTurn(PlayerTable.PLAYER1);
		spadeGame.setCurrTurn(PlayerTable.PLAYER1);
		spadeGame.setSpadePlayed(false);
		SpadePlayer spadePlayer1=null;
		TeamTable team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER1, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer1.setPlayingCard(null);
		spadePlayer1.setBidNil(false);
		spadePlayer1.setRemainingCards(Arrays.asList(Deck.SIX_DIAMONDS.getCard(),Deck.THREE_HEARTS.getCard(),Deck.NINE_CLUBS.getCard(),Deck.FOUR_SPADES.getCard()
				,Deck.ACE_CLUBS.getCard(),Deck.EIGHT_HEARTS.getCard()));
		
		
		SpadePlayer spadePlayer2 = null;
		TeamTable team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER2, spadeGame.getNumberOfTeams());
		spadePlayer2 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER2);
		spadePlayer2.setPlayingCard(null);
		
		
	
		SpadePlayer spadePlayer3=null;
		team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER3, spadeGame.getNumberOfTeams());
		spadePlayer3 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER3);
		spadePlayer3.setPlayingCard(null);
		spadePlayer3.setBidNil(true);
		SpadePlayer spadePlayer4 = null;
		team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER4, spadeGame.getNumberOfTeams());
		spadePlayer4 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER4);
		spadePlayer4.setPlayingCard(null);
		
	}
	
	
	public void secondPositionLeadPlayerBidNilWinning(SpadeGame spadeGame) {
		spadeGame.setServerPlaying(true);
		spadeGame.setStartTurn(PlayerTable.PLAYER1);
		spadeGame.setCurrTurn(PlayerTable.PLAYER2);
		spadeGame.setTempWinner(PlayerTable.PLAYER1);
		spadeGame.setSpadePlayed(false);
		SpadePlayer spadePlayer1=null;
		TeamTable team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER1, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer1.setPlayingCard(null);
		spadePlayer1.setBidNil(false);
		spadePlayer1.setRemainingCards(Arrays.asList(Deck.SIX_DIAMONDS.getCard(),Deck.THREE_HEARTS.getCard(),Deck.NINE_CLUBS.getCard(),Deck.FOUR_SPADES.getCard()
				,Deck.ACE_CLUBS.getCard(),Deck.EIGHT_HEARTS.getCard()));
		spadePlayer1.setPlayingCard(Deck.JACK_CLUBS.getCard());
		
		SpadePlayer spadePlayer2 = null;
		TeamTable team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER2, spadeGame.getNumberOfTeams());
		spadePlayer2 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER2);
		spadePlayer2.setPlayingCard(null);
//		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_DIAMONDS.getCard(),Deck.TWO_CLUBS.getCard(),Deck.FOUR_DIAMONDS.getCard(),Deck.FOUR_SPADES.getCard()
//				,Deck.KING_SPADES.getCard(),Deck.NINE_CLUBS.getCard()));
		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_DIAMONDS.getCard(),Deck.FOUR_DIAMONDS.getCard(),Deck.ACE_HEARTS.getCard(),Deck.EIGHT_HEARTS.getCard()));
//		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_SPADES.getCard(),Deck.FOUR_SPADES.getCard(),Deck.ACE_SPADES.getCard(),Deck.EIGHT_SPADES.getCard()));
		spadePlayer2.setBidNil(false);
		
	
		SpadePlayer spadePlayer3=null;
		team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER3, spadeGame.getNumberOfTeams());
		spadePlayer3 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER3);
		spadePlayer3.setPlayingCard(null);
		spadePlayer3.setBidNil(false);
		
		
		SpadePlayer spadePlayer4 = null;
		team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER4, spadeGame.getNumberOfTeams());
		spadePlayer4 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER4);
		spadePlayer4.setPlayingCard(null);
		spadePlayer4.setBidNil(false);
	}
	
	
	public void thirdPositionLeadPlayerBidNilWinning(SpadeGame spadeGame) {
		spadeGame.setServerPlaying(true);
		spadeGame.setStartTurn(PlayerTable.PLAYER1);
		spadeGame.setCurrTurn(PlayerTable.PLAYER3);
		spadeGame.setTempWinner(PlayerTable.PLAYER1);
		spadeGame.setSpadePlayed(false);
		SpadePlayer spadePlayer1=null;
		TeamTable team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER1, spadeGame.getNumberOfTeams());
		spadePlayer1 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER1);
		spadePlayer1.setPlayingCard(null);
		spadePlayer1.setBidNil(false);
		spadePlayer1.setRemainingCards(Arrays.asList(Deck.SIX_DIAMONDS.getCard(),Deck.THREE_HEARTS.getCard(),Deck.NINE_CLUBS.getCard(),Deck.FOUR_SPADES.getCard()
				,Deck.ACE_CLUBS.getCard(),Deck.EIGHT_HEARTS.getCard()));
		spadePlayer1.setPlayingCard(Deck.JACK_CLUBS.getCard());
		
		SpadePlayer spadePlayer2 = null;
		TeamTable team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER2, spadeGame.getNumberOfTeams());
		spadePlayer2 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER2);
		spadePlayer2.setPlayingCard(null);
//		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_DIAMONDS.getCard(),Deck.TWO_CLUBS.getCard(),Deck.FOUR_DIAMONDS.getCard(),Deck.FOUR_SPADES.getCard()
//				,Deck.KING_SPADES.getCard(),Deck.NINE_CLUBS.getCard()));
//		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_DIAMONDS.getCard(),Deck.FOUR_DIAMONDS.getCard(),Deck.ACE_HEARTS.getCard(),Deck.EIGHT_HEARTS.getCard()));
//		spadePlayer2.setRemainingCards(Arrays.asList(Deck.SEVEN_SPADES.getCard(),Deck.FOUR_SPADES.getCard(),Deck.ACE_SPADES.getCard(),Deck.EIGHT_SPADES.getCard()));
		spadePlayer2.setBidNil(false);
		spadePlayer2.setPlayingCard(Deck.TEN_CLUBS.getCard());
		
	
		SpadePlayer spadePlayer3=null;
		team1 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER3, spadeGame.getNumberOfTeams());
		spadePlayer3 = spadeGame.getTeams().get(team1).getPlayers().get(PlayerTable.PLAYER3);
		spadePlayer3.setPlayingCard(null);
		spadePlayer3.setRemainingCards(Arrays.asList(Deck.SEVEN_CLUBS.getCard(),Deck.TEN_DIAMONDS.getCard(),Deck.KING_CLUBS.getCard(),Deck.ACE_HEARTS.getCard(),Deck.FOUR_CLUBS.getCard()));
//		spadePlayer3.setRemainingCards(Arrays.asList(Deck.SEVEN_SPADES.getCard(),Deck.TEN_SPADES.getCard(),Deck.ACE_SPADES.getCard(),Deck.EIGHT_SPADES.getCard()));
		spadePlayer3.setBidNil(false);
		
		
		SpadePlayer spadePlayer4 = null;
		team2 = spadeTeamHandler.getTeamByPlayer(PlayerTable.PLAYER4, spadeGame.getNumberOfTeams());
		spadePlayer4 = spadeGame.getTeams().get(team2).getPlayers().get(PlayerTable.PLAYER4);
		spadePlayer4.setPlayingCard(null);
		spadePlayer4.setBidNil(true);
	}
}
