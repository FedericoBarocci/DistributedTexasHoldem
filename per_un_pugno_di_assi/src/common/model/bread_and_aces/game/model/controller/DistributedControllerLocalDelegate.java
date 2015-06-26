package bread_and_aces.game.model.controller;

import java.util.List;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
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
	
	@Override
	public void removePlayerLocally(String playerId) {
		gamePlayersKeeper.remove(playerId);
		viewControllerDelegate.remove(playerId);
	}
	
	@Override
	public void removePlayerLocally(List<String> playerIds) {
		// TODO Auto-generated method stub
		
	}
}
