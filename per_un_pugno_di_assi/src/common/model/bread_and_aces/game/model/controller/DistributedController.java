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
import bread_and_aces.game.model.oracle.responses.OracleResponseFactory;
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
	private final OracleResponseFactory oracleResponseFactory;
	
//	private String lastPlayerId = "";
	
	@Inject
	public DistributedController(
			ViewControllerDelegate viewControllerDelegate,
			GameOracle gameOracle, 
			GameState gameState,
			GamePlayersKeeper gamePlayersKeeper, Communicator communicator,
			DistributedControllerLocalDelegate distributedControllerDelegate,
			OracleResponseFactory oracleResponseFactory) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gameState = gameState;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.communicator = communicator;
		this.distributedControllerLocalDelegate = distributedControllerDelegate;
		this.oracleResponseFactory = oracleResponseFactory;
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
			gh.getCrashedPeers().forEach(c->{
				distributedControllerLocalDelegate.removePlayerLocally(c);
			}); 
			
			setActionOnSend(actionKeeper);
		}
	}
	
//	private void setActionOnSendCrashed(String crashedId) {
//		ActionKeeper actionKeeper = ActionKeeperFactory.build(Action.FOLD);
//		GameHolder gh = setActionAndUpdate(crashedId, actionKeeper).exec(communicator, gamePlayersKeeper, actionKeeper);
//		
//		gh.getCrashedOptional().ifPresent(c->{
//			System.out.println("CRASHED OPTIONAL PRESENT");
//			
//			//Communication communication = this.removePlayer(c);
//			distributedControllerLocalDelegate.removePlayerLocally(c);
//			//Communication communication = setActionAndUpdate(c, ActionKeeperFactory.build(Action.FOLD));
//			
//			//TODO this.nestedSetActionOnSend(communication, actionKeeper);
//		});
//		
//		gh.getCrashedOptional().ifPresent(c->{
//			setActionOnSendCrashed(c);
//		});
//	}
	
//	private void nestedSetActionOnSend(Communication communication, ActionKeeper actionKeeper) {
//		communication.exec(communicator, gamePlayersKeeper, actionKeeper);
//	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper) {
		this.setActionAndUpdate(fromPlayer, actionKeeper).finaly();
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, ActionKeeper actionKeeper, GameUpdater gameUpdater) {
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
			viewControllerDelegate.endGame(fromPlayer);
			DevPrinter.println("Oracle tells NOT successor. END.");
			//return Communication.END;
			return oracleResponseFactory.createOracleResponseEnd(fromPlayer);
		}
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(actionKeeper);
		viewControllerDelegate.setPlayerAction(fromPlayer, actionKeeper);
		
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		viewControllerDelegate.setViewToken(successor);

		gameState.nextGameState(actionKeeper);
		DevPrinter.println("actionkeeper: "+actionKeeper.getAction() + " - " + actionKeeper.getValue());
		DevPrinter.println(""+gameState.getGameState());
		
//		if(! lastPlayerId.equals(fromPlayer)) {
			viewControllerDelegate.setViewState(actionKeeper);
//			lastPlayerId = fromPlayer;
//		}
		
		OracleResponse response = gameOracle.ask();
		
		DevPrinter.println("Oracle tell: " + response);
		
		return response;
	}

	public boolean leader(boolean leaveGame) {
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
	
	public String getLeader() {
		return gamePlayersKeeper.getLeaderId();
	}
	
	/**
	 * local
	 * @return 
	 */
	@Override
	public void removePlayer(String playerId) {
		DevPrinter.println(/*new Throwable(),*/"HO RICEVUTO UNA NOTIFICA DI CRASH PER " + playerId);
		
		/*Communication communication = */setActionAndUpdate( playerId, ActionKeeperFactory.build(Action.FOLD) ).finaly();
//		gamePlayersKeeper.remove(playerId);
//		viewControllerDelegate.remove(playerId);
		distributedControllerLocalDelegate.removePlayerLocally(playerId);
		
		//return communication;
	}
}
