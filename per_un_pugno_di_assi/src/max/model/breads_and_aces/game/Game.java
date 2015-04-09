package breads_and_aces.game;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final RegistrarPlayersKeeper playersRegistry;
	private boolean isStarted = false;
	
	@Inject
	public Game(RegistrarPlayersKeeper playersRegistry) {
		this.playersRegistry = playersRegistry;
	}
	
	public RegistrarPlayersKeeper getPlayers() {
		return playersRegistry;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		this.isStarted = true;
	}
}
