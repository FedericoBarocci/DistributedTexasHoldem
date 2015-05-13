package breads_and_aces.game.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.state.GameState;

@Singleton
public class PotManager {

	private final GamePlayersKeeper gamePlayersKeeper;
	private final GameState gameState;
	private final Game game;
	
	private int currentPot = 0;
	
	@Inject
	public PotManager(GamePlayersKeeper gamePlayersKeeper, GameState gameState, Game game) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.gameState = gameState;
		this.game = game;
	}
	
	public int getCurrentPot() {
		return this.currentPot;
	}
	
	public void setCurrentPot(int pot) {
		this.currentPot = pot;
	}
	
	public int getMax() {
		return game.getCoins();
	}
}
