package breads_and_aces.game.model.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.model.oracle.GameOracle;
import breads_and_aces.game.model.oracle.GameStates;
import breads_and_aces.game.model.oracle.OracleResponses;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.gui.controllers.exceptions.SinglePlayerException;
import breads_and_aces.gui.view.ViewControllerDelegate;

@Singleton
public class DistributedController {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GamePlayersKeeper gamePlayersKeeper;

	@Inject
	public DistributedController(ViewControllerDelegate viewControllerDelegate, GameOracle gameOracle, GamePlayersKeeper gamePlayersKeeper) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gamePlayersKeeper = gamePlayersKeeper;
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

	// TODO when Communicator will be completed, this will have be public and setLocalAction below will have to be private
	/*private void setLocalActionAndPropagate(Action myAction, Communicator communicator) {
		Communication c = setLocalAction(myAction);
		c.sendCommunication(gamePlayersKeeper.getMyName(), myAction, gamePlayersKeeper.getPlayers(), communicator);
	}*/
	public Communication setLocalAction(Action myAction) {
		return setAction(gamePlayersKeeper.getMyName(), myAction);
	}
	public void setRemoteAction(String fromPlayer, Action action) {
		setAction(fromPlayer, action);
	}

	public void updateRemoteAction(String fromPlayer, Action action, GameUpdater gameUpdater) {
		setAction(fromPlayer, action);
		gameOracle.update(gameUpdater);
	}
	
	private Communication setAction(String fromPlayer, Action action) {
		String successor;
		
		try {
			successor = gameOracle.getSuccessor(fromPlayer).getName();
			System.out.println("Oracle tell successor: " + successor);
		} 
		catch (SinglePlayerException e) {
			System.out.println("Oracle tell NOT successor. END.");
			return Communication.END;
		}
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(action);
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		
		viewControllerDelegate.setViewToken(successor);
		gameOracle.nextGameState(action);
		
		OracleResponses response = gameOracle.ask();
		
		System.out.println("Oracle tell: " + response);
		
		switch (response) {
		case OK:
			return Communication.ACTION;

		case NEXT_STEP:
			viewControllerDelegate.addTableCards();
			return Communication.ACTION;

		case WINNER:
			viewControllerDelegate.setRefresh();
			return winnerEvent();

		case END_GAME:
			return Communication.END;
		}
		
		/* Should never be here */
		return Communication.ACTION;
	}

	private Communication winnerEvent() {
		List<Player> winners = gameOracle.getWinner();
		
		viewControllerDelegate.showDown(winners);
		
		for (Player p : winners) 
			System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
		
		gameOracle.setGameState(GameStates.NULL);
		
		return Communication.DEAL;
	}

	public boolean leader() {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
			return false;
		}
		
		return gamePlayersKeeper.getMyPlayer().hasToken();
	}

	public void update(GameUpdater gameUpdater) {
		gameOracle.update(gameUpdater);
	}
}
