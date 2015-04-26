package breads_and_aces.game.model.oracle;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.MeProvider;
import breads_and_aces.game.model.table.Table;

@Singleton
public class GameOracleProvider implements Provider<GameOracle> {

	private final Game game;
//	private final GameViewProvider gameViewProvider;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final MeProvider meProvider;
	private final Table table;
	
	private GameOracle gameOracle;

	@Inject
	public GameOracleProvider(Game game, Table table,
//			GameViewProvider gameViewProvider,
			GamePlayersKeeper gamePlayersKeeper, MeProvider meProvider) {
		this.game = game;
		this.table = table;
//		this.gameViewProvider = gameViewProvider;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.meProvider = meProvider;
	}
	
	public GameOracleProvider create() {
		this.gameOracle = new GameOracle(game, table, /*gameViewProvider,*/
				gamePlayersKeeper, meProvider);
		return this;
	}

	@Override
	public GameOracle get() {
		return gameOracle;
	}

}
