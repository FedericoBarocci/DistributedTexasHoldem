package bread_and_aces.game.model.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.Game;
import bread_and_aces.game.exceptions.SinglePlayerException;
import bread_and_aces.game.model.controller.Communication.GameHolder;
import bread_and_aces.game.model.oracle.GameOracle;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.Message;
import bread_and_aces.game.model.oracle.actions.MessageFactory;
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
		game.start();
	}
	
	/**
	 * local
	 */
//	@Override
	public void setActionOnSend(Message message) {
		OracleResponse oracleResponse = setActionAndUpdate(gamePlayersKeeper.getMyName(), message);
		Communication communication = oracleResponse.exec();
		GameHolder gh = communication.exec(communicator, gamePlayersKeeper, message);
		
		if(!gh.hasCrashed()) {
			oracleResponse.complete();
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
			
			setActionOnSend(message);
		}
	}
	
	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, Message message) {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
		}
		
		if (message.hasCrashed()) {
			if(gamePlayersKeeper.contains(message.getCrashed())) {
				OracleResponse oracleResponse = setActionAndUpdate(message.getCrashed(), message);
				crashHandler.removeLocallyFromEverywhere(message.getCrashed());
				oracleResponse.complete();
			}
			else {
				gameOracle.ask().complete();
			}
		}
		else {
			setActionAndUpdate(fromPlayer, message).complete();
		}
	}

	/**
	 * remote
	 */
	@Override
	public void setActionOnReceive(String fromPlayer, Message message, GameUpdater gameUpdater) {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
		}
		
		if (message.hasCrashed()) {
			if(gamePlayersKeeper.contains(message.getCrashed())) {
				OracleResponse oracleResponse = setActionAndUpdate(message.getCrashed(), message);
				crashHandler.removeLocallyFromEverywhere(message.getCrashed());
				oracleResponse.complete();
			}
			else {
				gameOracle.ask().complete();
			}
		}
		else {
			setActionAndUpdate(fromPlayer, message).complete();
		}
		
		gameOracle.update(gameUpdater);
	}
	
	public OracleResponse setActionAndUpdate(String fromPlayer, Message message) {
		String successor;
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(message);
		viewControllerDelegate.setPlayerAction(fromPlayer, message);
		
		try {
			successor = gameOracle.getSuccessor(fromPlayer).getName();
			DevPrinter.println("Oracle tells successor: " + successor);
			gamePlayersKeeper.setLeaderId(successor);
		} 
		catch (SinglePlayerException e) {
			gamePlayersKeeper.getPlayer(fromPlayer).receiveToken();
			//viewControllerDelegate.endGame(fromPlayer);
			viewControllerDelegate.enableButtons(false);
			gamePlayersKeeper.setLeaderId(fromPlayer);
			
			DevPrinter.println("Oracle tells NOT successor. END.");
			//return Communication.END;
			return oracleResponseFactory.createOracleResponseWinner(gameOracle.getWinners());
		}
		
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		viewControllerDelegate.setViewToken(successor);

		gameState.nextGameState(message);
		DevPrinter.println("actionkeeper: "+message.getAction() + " - " + message.getValue());
		DevPrinter.println(""+gameState.getGameState());
		
		viewControllerDelegate.setViewState(message);
		
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
		else {
			DevPrinter.println("game stop");
		}
		
		return false;
	}
	
	public String getLeader() {
		return gamePlayersKeeper.getLeaderId();
	}
	
	/**
	 * local
	 * NOT USED
	 * 
	 * @return 
	 */
	@Override
	public void removePlayer(String playerId) {
		DevPrinter.println("HO RICEVUTO UNA NOTIFICA DI CRASH PER " + playerId);
		
//		setActionAndUpdate( playerId, MessageFactory.build(Action.FOLD) ).finaly();
		distributedControllerLocalDelegate.removePlayerLocally(playerId);
	}
	
	public String setNextLeader() {
		String leader = getLeader();
		String successor;
		
		gamePlayersKeeper.getPlayer(leader).setAction(Action.FOLD);
		viewControllerDelegate.setPlayerAction(leader, MessageFactory.buildForCrash(leader));
		
		try {
			successor = gameOracle.getSuccessor(leader).getName();
			DevPrinter.println("Oracle tells successor: " + successor);
			gamePlayersKeeper.setLeaderId(successor);
		} 
		catch (SinglePlayerException e) {
			successor = gamePlayersKeeper.getNext(leader).getName();
			gamePlayersKeeper.setLeaderId(successor);
		}
		
		DevPrinter.println("Successor: " + successor);
		
		gamePlayersKeeper.getPlayer(leader).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(leader);
		viewControllerDelegate.setViewToken(successor);
		
		return successor;
	}
	
	public void removeAndUpdate(String crashedLeader) {
		Message crashMessage = MessageFactory.buildForCrash(crashedLeader);
		OracleResponse oracleResponse = setActionAndUpdate(crashedLeader, crashMessage);
		
		crashHandler.removeLocallyFromEverywhere(crashedLeader);
		
		boolean newCrashedPeersDetected;
		
		do {
			newCrashedPeersDetected = false;
			
			Communication communication = oracleResponse.exec();
			GameHolder gh = communication.exec(communicator, gamePlayersKeeper, crashMessage);
			
			DevPrinter.println();
			
			if(!gh.hasCrashed()) {
				oracleResponse.complete();
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
				
				DevPrinter.println();
				////removeAndUpdate(crashedLeader);
				//oracleResponse.finaly();
				oracleResponse = gameOracle.ask();
				newCrashedPeersDetected = true;
			}
		} while (newCrashedPeersDetected);
	}
}
