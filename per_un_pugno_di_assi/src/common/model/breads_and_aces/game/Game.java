package breads_and_aces.game;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.table.Table;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Table table;
	private boolean isStarted = false;
	
	@Inject
	public Game(GamePlayersKeeper gamePlayersKeeper, Table table) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.table = table;
	}
	
	public GamePlayersKeeper getPlayers() {
		return gamePlayersKeeper;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		isStarted = true;
	}

	public Table getTable() {
		return table;
	}
}
