package bread_and_aces.game.model.controller;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.gui.view.ViewControllerDelegate;

//@Singleton
@LazySingleton
public class DistributedControllerLocalDelegate implements DistributedControllerLocal {
	
	private final GamePlayersKeeper gamePlayersKeeper;
	private final ViewControllerDelegate viewControllerDelegate;

	@Inject
	public DistributedControllerLocalDelegate(GamePlayersKeeper gamePlayersKeeper, ViewControllerDelegate viewControllerDelegate) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.viewControllerDelegate = viewControllerDelegate;
	}
	
//	@Override
	void handleToken() {
		System.out.println("Ho ricevuto il token-bucket da remoto");
		gamePlayersKeeper.getPlayer(gamePlayersKeeper.getMyName()).receiveToken();
	}
	
//	@Override
	void receiveStartGame(String whoHasToken) {
		gamePlayersKeeper.getPlayer(whoHasToken).receiveToken();
		viewControllerDelegate.setViewToken(whoHasToken);
		System.out.println("Game can start!");
	}
	
	void setLocalAction(String fromPlayer, ActionKeeper actionKeeper) {
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(actionKeeper);
		viewControllerDelegate.setPlayerAction(fromPlayer, actionKeeper);
	}
	void handleLocalToken(String fromPlayer, String successor) {
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		viewControllerDelegate.setViewToken(successor);
	}
	
	boolean leader() {
		if (viewControllerDelegate.isSetRefresh()) {
			viewControllerDelegate.refresh(gamePlayersKeeper.getPlayers(), gamePlayersKeeper.getMyName());
			viewControllerDelegate.enableButtons(gamePlayersKeeper.getMyPlayer().hasToken());
			
			return false;
		}
		
		return gamePlayersKeeper.getMyPlayer().hasToken();
	}
	
	@Override
	public void removePlayerLocally(String playerId) {
		gamePlayersKeeper.remove(playerId);
		viewControllerDelegate.remove(playerId);
	}

	void handleLocalState(ActionKeeper actionKeeper, GameState gameState) {
		gameState.nextGameState(actionKeeper);
		System.out.println("actionkeeper: "+actionKeeper.getAction() + " - " + actionKeeper.getValue());
		System.out.println(gameState.getGameState());
		viewControllerDelegate.setViewState(actionKeeper);
	}
}
