package bread_and_aces.game.model.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.exceptions.SinglePlayerException;
import bread_and_aces.game.model.controller.Communication.GameHolder;
import bread_and_aces.game.model.oracle.GameOracle;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.oracle.actions.ActionKeeperFactory;
import bread_and_aces.game.model.oracle.responses.OracleResponse;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.game.updater.GameUpdater;
import bread_and_aces.gui.view.ViewControllerDelegate;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.utils.DevPrinter;

@Singleton
public class DistributedController implements DistributedControllerForRemoteHandling/*, DistributedControllerLocal*/ {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GameState gameState;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Communicator communicator;
	private final DistributedControllerLocalDelegate distributedControllerLocalDelegate;
	
	@Inject
	public DistributedController(
			ViewControllerDelegate viewControllerDelegate,
			GameOracle gameOracle, 
			GameState gameState,
			GamePlayersKeeper gamePlayersKeeper, Communicator communicator
			, DistributedControllerLocalDelegate distributedControllerDelegate) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gameState = gameState;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.communicator = communicator;
		
		this.distributedControllerLocalDelegate = distributedControllerDelegate;
	}

	/**
	 * local
	 */
////	@Override
//	public void handleToken() {
////		DevPrinter.println("Ho ricevuto il token-bucket da remoto");
////		gamePlayersKeeper.getPlayer(gamePlayersKeeper.getMyName()).receiveToken();
//		distributedControllerLocalDelegate.handleToken();
//	}

	/**
	 * remote
	 */
	@Override
	public void receiveStartGame(String whoHasToken) {
//		gamePlayersKeeper.getPlayer(whoHasToken).receiveToken();
//		viewControllerDelegate.setViewToken(whoHasToken);
//		DevPrinter.println("Game can start!");
		distributedControllerLocalDelegate.receiveStartGame(whoHasToken);
		gamePlayersKeeper.setLeaderId(whoHasToken);
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnSend(ActionKeeper actionKeeper) {
		GameHolder gh = setActionAndUpdate(gamePlayersKeeper.getMyName(), actionKeeper).exec(communicator, gamePlayersKeeper, actionKeeper);
		// CRASH
		gh.getCrashedOptional().ifPresent(c->{
			Communication communication = removePlayer(c);
			nestedSetActionOnSend(communication, actionKeeper);
		});
		
		gh.getGameupdaterOptional().ifPresent(c->{
			gameOracle.update(c);
		});
//			gameOracle.update(c)	
	}
	
	private void nestedSetActionOnSend(Communication communication, ActionKeeper actionKeeper) {
		communication.exec(communicator, gamePlayersKeeper, actionKeeper);
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper) {
		setActionAndUpdate(fromPlayer, actionKeeper);
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater) {
		setActionAndUpdate(fromPlayer, actionKeeper);
		gameOracle.update(gameUpdater);
	}
	
	private Communication setActionAndUpdate(String fromPlayer, ActionKeeper actionKeeper) {
		
		DevPrinter.println();
		String successor;
		
		try {
			DevPrinter.println();
			successor = gameOracle.getSuccessor(fromPlayer).getName();
			DevPrinter.println("Oracle tells successor: " + successor);
			gamePlayersKeeper.setLeaderId(successor);
		} 
		catch (SinglePlayerException e) {
			viewControllerDelegate.endGame(fromPlayer);
			DevPrinter.println("Oracle tells NOT successor. END.");
			return Communication.END;
		}
		
//		gamePlayersKeeper.getPlayer(fromPlayer).setAction(actionKeeper);
//		viewControllerDelegate.setPlayerAction(fromPlayer, actionKeeper);
		distributedControllerLocalDelegate.setLocalAction(fromPlayer, actionKeeper);
		DevPrinter.println();
		
//		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
//		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
//		viewControllerDelegate.setViewToken(successor);
		// TODO guarda qui dentro
		distributedControllerLocalDelegate.handleLocalToken(fromPlayer, successor);
		DevPrinter.println();

//		gameState.nextGameState(actionKeeper);
//		DevPrinter.println("actionkeeper: "+actionKeeper.getAction() + " - " + actionKeeper.getValue());
//		DevPrinter.println(gameState.getGameState());
//		viewControllerDelegate.setViewState(actionKeeper);
		distributedControllerLocalDelegate.handleLocalState(actionKeeper, gameState);
		
		OracleResponse response = gameOracle.ask();
		
		DevPrinter.println("Oracle tell: " + response);
		
		return response.exec();
	}

	public boolean leader(boolean leaveGame) {
//		if (viewControllerDelegate.isSetRefresh()) {
//			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
//			viewControllerDelegate.enableButtons(gamePlayersKeeper.getMyPlayer().hasToken());
//			
//			return false;
//		}
//		
//		return gamePlayersKeeper.getMyPlayer().hasToken();
		//return distributedControllerLocalDelegate.leader();
		if (viewControllerDelegate.isSetRefresh()) {
			if (leaveGame) {
				//TODO Notify other players of current player exit (remove from playerkeeper)
				System.exit(0);
			}
			
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
			viewControllerDelegate.enableButtons(gamePlayersKeeper.getMyPlayer().hasToken());
			
			return false;
		}
		
		return gamePlayersKeeper.getMyPlayer().hasToken();
	}
	
	public String getLeader() {
		return gamePlayersKeeper.getLeaderId();
	}

	/**
	 * local
	 */
	@Override
	public Communication removePlayer(String playerId) {
		DevPrinter.println(/*new Throwable(),*/"HO RICEVUTO UNA NOTIFICA DI CRASH PER " + playerId);
		
		// TODO FORSE VANNO INVERTITI ?
		Communication communication = setActionAndUpdate( playerId, ActionKeeperFactory.get(Action.FOLD) );
//		gamePlayersKeeper.remove(playerId);
//		viewControllerDelegate.remove(playerId);
		distributedControllerLocalDelegate.removePlayerLocally(playerId);
		
		return communication;
	}
}
