package bread_and_aces.game;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.gui.controllers.actionlisteners.init.ListenersInitializer;
import bread_and_aces.gui.view.ViewControllerDelegate;
import bread_and_aces.gui.view.ViewCreator;

@LazySingleton
public class GameInitializerReal implements GameInitializer {

	private final Game game;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final ViewCreator viewCreator;
	private final ListenersInitializer listenersInitializer;
	private final ViewControllerDelegate viewControllerDelegate;
	private final BetManager betManager;

	@Inject
	public GameInitializerReal(Game game, 
			GamePlayersKeeper gamePlayersKeeper,
			ViewCreator viewCreator,
			ListenersInitializer listenersInitializer,
			ViewControllerDelegate viewControllerDelegate, BetManager betManager) {
		this.game = game;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.viewCreator = viewCreator;
		this.listenersInitializer = listenersInitializer;
		this.viewControllerDelegate = viewControllerDelegate;
		this.betManager = betManager;
	}

	@Override
	public void start(int initialGoal, int initialCoins) {
		game.setGoal(initialGoal);
		game.setCoins(initialCoins);
		betManager.init();
		
		gamePlayersKeeper.getPlayers().forEach(p -> p.setScore( initialCoins ));
		
		viewCreator.init(null);
		listenersInitializer.init(null);
		
		viewControllerDelegate.init(gamePlayersKeeper.getPlayers(),
				gamePlayersKeeper.getMyName(), game.getGoal(), game.getCoins() );
	}
}
