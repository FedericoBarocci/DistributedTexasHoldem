package breads_and_aces.game.model.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.oracle.GameOracle;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.oracle.responses.OracleResponse;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.state.GameState;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.gui.controllers.exceptions.SinglePlayerException;
import breads_and_aces.gui.view.ViewControllerDelegate;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

@Singleton
public class DistributedController {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GameState gameState;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Communicator communicator;

	@Inject
	public DistributedController(ViewControllerDelegate viewControllerDelegate,
			GameOracle gameOracle, GameState gameState,
			GamePlayersKeeper gamePlayersKeeper, Communicator communicator) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gameState = gameState;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.communicator = communicator;
	}

	public void handleToken() {
		System.out.println("Ho ricevuto il token-bucket da remoto");

		gamePlayersKeeper.getPlayer(gamePlayersKeeper.getMyName()).receiveToken();
	}

	public void receiveStartGame(String whoHasToken) {
		gamePlayersKeeper.getPlayer(whoHasToken).receiveToken();
		viewControllerDelegate.setViewToken(whoHasToken);

		System.out.println("Game can start!");
	}

	public void setAction(Action action) {
		setActionAndUpdate(gamePlayersKeeper.getMyName(), action).exec(communicator, gamePlayersKeeper, action).ifPresent(c -> gameOracle.update(c));
	}
	
	public void setAction(String fromPlayer, Action action) {
		setActionAndUpdate(fromPlayer, action);
	}

	public void setAction(String fromPlayer, Action action, GameUpdater gameUpdater) {
		setActionAndUpdate(fromPlayer, action);
		gameOracle.update(gameUpdater);
	}
	
	private Communication setActionAndUpdate(String fromPlayer, Action action) {
		String successor;
		
		try {
			successor = gameOracle.getSuccessor(fromPlayer).getName();
			System.out.println("Oracle tell successor: " + successor);
		} 
		catch (SinglePlayerException e) {
			viewControllerDelegate.endGame(fromPlayer);
			System.out.println("Oracle tell NOT successor. END.");
			return Communication.END;
		}
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(action);
		viewControllerDelegate.setPlayerAction(fromPlayer, action);
		
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		viewControllerDelegate.setViewToken(successor);
		
		gameState.nextGameState(action);
		
		OracleResponse response = gameOracle.ask();
		
		System.out.println("Oracle tell: " + response);
		
		return response.exec();
	}

	public boolean leader() {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
			viewControllerDelegate.enableButtons(gamePlayersKeeper.getMyPlayer().hasToken());
			return false;
		}
		
		return gamePlayersKeeper.getMyPlayer().hasToken();
	}
}
