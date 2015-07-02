package bread_and_aces.game.model.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.Game;
import bread_and_aces.game.exceptions.SinglePlayerException;
import bread_and_aces.game.model.controller.Communication.GameHolder;
import bread_and_aces.game.model.oracle.GameOracle;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.oracle.actions.ActionKeeperFactory;
import bread_and_aces.game.model.oracle.responses.OracleResponse;
import bread_and_aces.game.model.oracle.responses.OracleResponseFactory;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.game.updater.GameUpdater;
import bread_and_aces.gui.view.ViewControllerDelegate;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

@Singleton
public class DistributedController implements DistributedControllerForRemoteHandling/*, DistributedControllerLocal*/ {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GameState gameState;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Communicator communicator;
	private final DistributedControllerLocalDelegate distributedControllerLocalDelegate;
	private final OracleResponseFactory oracleResponseFactory;
	private final Game game;
	private final CrashHandler crashHandler;
	
//	private String lastPlayerId = "";
	
	@Inject
	public DistributedController(
			ViewControllerDelegate viewControllerDelegate,
			GameOracle gameOracle, 
			GameState gameState,
			GamePlayersKeeper gamePlayersKeeper, Communicator communicator,
			DistributedControllerLocalDelegate distributedControllerDelegate,
			OracleResponseFactory oracleResponseFactory, Game game, CrashHandler crashHandler) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gameState = gameState;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.communicator = communicator;
		this.distributedControllerLocalDelegate = distributedControllerDelegate;
		this.oracleResponseFactory = oracleResponseFactory;
		this.game = game;
		this.crashHandler = crashHandler;
	}

	/**
	 * remote
	 */
	@Override
	public void receiveStartGame(String whoHasToken) {
		gamePlayersKeeper.getPlayer(whoHasToken).receiveToken();
		viewControllerDelegate.setViewToken(whoHasToken);
		DevPrinter.println("Game can start!");
		gamePlayersKeeper.setLeaderId(whoHasToken);
	}

	/**
	 * local
	 */
//	@Override
	public void setActionOnSend(ActionKeeper actionKeeper) {
		OracleResponse oracleResponse = setActionAndUpdate(gamePlayersKeeper.getMyName(), actionKeeper);
		Communication communication = oracleResponse.exec();
		GameHolder gh = communication.exec(communicator, gamePlayersKeeper, actionKeeper);
		
		if(!gh.hasCrashed()) {
			oracleResponse.finaly();
		}
		
		gh.getGameupdaterOptional().ifPresent(g->{
			gameOracle.update(g);
		});
		
		// CRASH
		if (gh.hasCrashed()) {
			crashHandler.recursiveBroadcastRemoveCrashed(gamePlayersKeeper.getMyName(), gh.getCrashedPeers());
			
			gh.getCrashedPeers().forEach(c->{
				distributedControllerLocalDelegate.removePlayerLocally(c);
			}); 
			
			setActionOnSend(actionKeeper);
		}
	}
	
	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper) {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
		}
		
		this.setActionAndUpdate(fromPlayer, actionKeeper).finaly();
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater) {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
		}
		
		this.setActionAndUpdate(fromPlayer, actionKeeper).finaly();
		gameOracle.update(gameUpdater);
	}
	
	public OracleResponse setActionAndUpdate(String fromPlayer, ActionKeeper actionKeeper) {
		String successor;
		
		try {
			successor = gameOracle.getSuccessor(fromPlayer).getName();
			DevPrinter.println("Oracle tells successor: " + successor);
			gamePlayersKeeper.setLeaderId(successor);
		} 
		catch (SinglePlayerException e) {
			gamePlayersKeeper.getPlayer(fromPlayer).receiveToken();
			viewControllerDelegate.endGame(fromPlayer);
			viewControllerDelegate.enableButtons(false);
			
			DevPrinter.println("Oracle tells NOT successor. END.");
			//return Communication.END;
			return oracleResponseFactory.createOracleResponseWinner(gameOracle.getWinners());
		}
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(actionKeeper);
		viewControllerDelegate.setPlayerAction(fromPlayer, actionKeeper);
		
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		viewControllerDelegate.setViewToken(successor);

		gameState.nextGameState(actionKeeper);
		DevPrinter.println("actionkeeper: "+actionKeeper.getAction() + " - " + actionKeeper.getValue());
		DevPrinter.println(""+gameState.getGameState());
		
		viewControllerDelegate.setViewState(actionKeeper);
		
		OracleResponse response = gameOracle.ask();
		
		DevPrinter.println("Oracle tell: " + response);
		
		return response;
	}

	public boolean leader(boolean leaveGame) {
		if (game.isStarted()) {
			if (viewControllerDelegate.isSetRefresh()) {
				if (leaveGame) {
					//TODO Notify other players of current player exit (remove from playerkeeper, graceful exit)
					System.exit(0);
				}
	
				viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
				viewControllerDelegate.enableButtons(gamePlayersKeeper.getMyPlayer().hasToken());
				
				return false;
			}
			
			return gamePlayersKeeper.getMyPlayer().hasToken();
		}
		
		return false;
	}
	
	public String getLeader() {
		return gamePlayersKeeper.getLeaderId();
	}
	
	/**
	 * local
	 * @return 
	 */
	@Override
	public void removePlayer(String playerId) {
		DevPrinter.println("HO RICEVUTO UNA NOTIFICA DI CRASH PER " + playerId);
		
		setActionAndUpdate( playerId, ActionKeeperFactory.build(Action.FOLD) ).finaly();
		distributedControllerLocalDelegate.removePlayerLocally(playerId);
	}
}
