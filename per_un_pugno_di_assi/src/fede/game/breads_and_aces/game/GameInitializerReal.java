package breads_and_aces.game;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.gui.controllers.actionlisteners.init.ListenersInitializer;
import breads_and_aces.gui.view.ViewControllerDelegate;

@Singleton
public class GameInitializerReal implements GameInitializer {

	private final Game game;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final ListenersInitializer listenersInitializer;
	private final ViewControllerDelegate viewControllerDelegate;

	private static final int initialCoins = 200;
	private static final int initialGoal = 1000;

	@Inject
	public GameInitializerReal(Game game, GamePlayersKeeper gamePlayersKeeper, 
			ListenersInitializer listenersInitializer,
			ViewControllerDelegate viewControllerDelegate) {
		this.game = game;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.listenersInitializer = listenersInitializer;
		this.viewControllerDelegate = viewControllerDelegate;
	}

	@Override
	public void start() {
		game.setGoal(initialGoal);
		game.setCoins(initialCoins);
		
		gamePlayersKeeper.getPlayers().forEach(p -> p.setScore(game.getCoins()));
		
		// TODO start the view here
		listenersInitializer.init();
		
		viewControllerDelegate.init(gamePlayersKeeper.getPlayers(),
				gamePlayersKeeper.getMyName(), game.getGoal(), game.getCoins());
	}
}
