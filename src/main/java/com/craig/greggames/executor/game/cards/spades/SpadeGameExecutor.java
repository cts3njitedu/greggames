//package com.craig.greggames.executor.game.cards.spades;
//
//import static com.craig.greggames.constants.game.cards.spades.SpadeGameConstants.MAX_TURN_PER_TRICK;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.craig.greggames.handler.game.cards.spades.SpadeTeamHandler;
//import com.craig.greggames.model.game.cards.spades.SpadeErrors;
//import com.craig.greggames.model.game.cards.spades.SpadeGame;
//import com.craig.greggames.model.game.cards.spades.SpadeNotifications;
//import com.craig.greggames.service.cards.spades.SpadeService;
//
//public class SpadeGameExecutor extends CardGameExecutor<SpadeGame> {
//
//	@Autowired
//	private SpadeTeamHandler teamService;
//	@Autowired
//	private SpadeService spadeService;
//	@Override
//	public void create(SpadeGame spadeGame) {
//		// TODO Auto-generated method stub
//		spadeGame.setSpadePlayed(false);
//
//		spadeGame.setNumberOfPlayers(MAX_TURN_PER_TRICK);
//		spadeGame.setGameNotification(SpadeNotifications.START);
//		teamService.makeTeams(spadeGame);
//
//	}
//
//	@Override
//	public void execute(SpadeGame greggame) {
//		// TODO Auto-generated method stub
//		SpadeGame oldSpadeGame = spadeService.findGame(greggame.getGameId());
//		
//		
//		switch (greggame.getGameNotification()) {
//		
//		case BID:
//			greggame.setPreviousHand(null);
//			greggame.setPreviousTrick(null);
//			greggame.setTrickOver(false);
//			greggame.setHandOver(false);
//			switch (greggame.getPlayerNotification()) {
//
//			case RECEIVED_ERROR:
//				playerService.cleanUpError(greggame);
//				break;
//			case NEW_PLAYER:
//				System.out.println("New Player");
//				botService.determineBots(greggame);
//				greggame.setNewPlayer(false);
//				break;
//			case BID:
//				System.out.println("Bidding");
//				boolean isValidBid = validationService.validateBid(greggame);
//				if (!isValidBid) {
//					return saveGame(spadeGame);
//				}
//				spadeGameService.play(spadeGame);
//				break;
//			default:
//				playerService.addError(spadeGame,SpadeErrors.CURRENTLY_BIDDING,oldSpadeGame);
//				break;
//
//			}
//			break;
//
//		case PLAY:
//			spadeGame.setPreviousHand(null);
//			spadeGame.setPreviousTrick(null);
//			spadeGame.setTrickOver(false);
//			spadeGame.setHandOver(false);
//			switch (spadeGame.getPlayerNotification()) {
//
//			case RECEIVED_ERROR:
//				System.out.println("Recieved Error");
//				playerService.cleanUpError(spadeGame);
//				break;
//			case NEW_PLAYER:
//
//				botService.determineBots(spadeGame);
//				spadeGame.setNewPlayer(false);
//				break;
//			case PLAY:
//				SpadeGame prevSpadeGame = findGame(spadeGame.getGameId());
//				boolean isValidTurn = validationService.validateTurn(spadeGame, prevSpadeGame);
//				if (!isValidTurn) {
//					return saveGame(spadeGame);
//				}
//				boolean isValidCard = validationService.validatePlayerCard(spadeGame);
//				System.out.println(isValidCard);
//				if (!isValidCard) {
//					return saveGame(spadeGame);
//				}
//				spadeGameService.play(spadeGame);
//				break;
//			default:
//				
//				playerService.addError(spadeGame,SpadeErrors.CURRENTLY_PLAYING,oldSpadeGame);
//				break;
//
//			}
//			break;
//
//		case START:
//			spadeGame.setPreviousHand(null);
//			spadeGame.setPreviousTrick(null);
//			spadeGame.setTrickOver(false);
//			spadeGame.setHandOver(false);
//			spadeGameService.startGame(spadeGame);
//			break;
//		default:
//			spadeGame.setPreviousHand(null);
//			spadeGame.setPreviousTrick(null);
//			playerService.addError(spadeGame,SpadeErrors.WRONG_MOVE,oldSpadeGame);
//			break;
//		}
//
//	}
//
//
//}
