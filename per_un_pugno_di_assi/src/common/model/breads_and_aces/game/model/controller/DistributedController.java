package breads_and_aces.game.model.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.oracle.GameOracle;
import breads_and_aces.game.model.oracle.GameStates;
import breads_and_aces.game.model.oracle.OracleResponses;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.gui.controllers.exceptions.SinglePlayerException;
import breads_and_aces.gui.view.ViewControllerDelegate;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

@Singleton
public class DistributedController {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Communicator communicator;

	@Inject
	public DistributedController(ViewControllerDelegate viewControllerDelegate, GameOracle gameOracle, GamePlayersKeeper gamePlayersKeeper, Communicator communicator) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
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
		setActionAndUpdate(gamePlayersKeeper.getMyName(), action).exec(communicator, gamePlayersKeeper, action).ifPresent(c->gameOracle.update(c));
		
		//distributedController.update(gameUpdater);
		
		
		/*if(communication.equals(Communication.DEAL)) {
			communicationService.makeGameUpdater(gamePlayersKeeper.getPlayers());
			communicationService.exec(communication, action);
			gameOracle.update(communicationService.getGameUpdater());
		}
		else {
			communicationService.exec(communication, action);
		}*/
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
		
		gameOracle.nextGameState(action);
		
		OracleResponses response = gameOracle.ask();
		
		System.out.println("Oracle tell: " + response);
		
		switch (response) {
		case OK:
			return Communication.ACTION;

		case NEXT_STEP:
			viewControllerDelegate.addTableCards(gamePlayersKeeper.getActivePlayers());
			return Communication.ACTION;

		case WINNER:
			viewControllerDelegate.setRefresh();
			return winnerEvent();

		case END_GAME:
			viewControllerDelegate.endGame(fromPlayer);
			return Communication.END;
		}
		
		/* Should never be here */
		return Communication.ACTION;
	}

	private Communication winnerEvent() {
		List<Player> winners = gameOracle.getWinner();
		
		viewControllerDelegate.showDown(winners);
		
		for (Player p : winners) {
			p.setScore(p.getScore() + 50);
//			System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
		}
		
		gameOracle.setGameState(GameStates.NULL);
		
		return Communication.DEAL;
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
