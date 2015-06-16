package bread_and_aces.game.updater;

import javax.inject.Inject;
import javax.inject.Provider;

import bread_and_aces.game.core.Deck;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;

public class GameUpdaterProvider implements Provider<GameUpdater> {

	private final GamePlayersKeeper gamePlayersKeeper;

	@Inject
	public GameUpdaterProvider(GamePlayersKeeper gamePlayersKeeper) {
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
//	public static GameUpdater build() {
////		return new GameUpdater(gamePlayersKeeper.getPlayers(), new Deck());
//		return new GameUpdater( Main.Injector.getInstance(GamePlayersKeeper.class).getPlayers(),  new Deck());
//	}

	@Override
	public GameUpdater get() {
		return new GameUpdater(gamePlayersKeeper.getPlayers(), new Deck());
	}
}
