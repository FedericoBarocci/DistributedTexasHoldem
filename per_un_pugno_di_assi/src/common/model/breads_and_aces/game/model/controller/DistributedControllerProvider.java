package breads_and_aces.game.model.controller;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.oracle.GameOracle;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.gui.view.ViewControllerDelegate;

@Singleton
public class DistributedControllerProvider implements Provider<DistributedController> {

	private final ViewControllerDelegate viewControllerDelegate;
	private final GameOracle gameOracle;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Game game;
	
	private DistributedController distributedController;
	
	@Inject
	public DistributedControllerProvider(ViewControllerDelegate viewControllerDelegate, GameOracle gameOracle, GamePlayersKeeper gamePlayersKeeper, Game game) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.gameOracle = gameOracle;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.game = game;
	}
	
	public DistributedControllerProvider create() {
		this.distributedController = new DistributedController(viewControllerDelegate, gameOracle, gamePlayersKeeper, game);
		return this;
	}
	
	@Override
	public DistributedController get() {
		return distributedController;
	}

}
