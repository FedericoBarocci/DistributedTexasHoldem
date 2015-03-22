package breads_and_aces.game;

import javax.inject.Inject;

import breads_and_aces.game.registry.PlayersRegistry;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final PlayersRegistry playersRegistry;
	private boolean isStarted = false;
	
	@Inject
	public Game(PlayersRegistry playersRegistry) {
		this.playersRegistry = playersRegistry;
	}
	
	public PlayersRegistry getPlayers() {
		return playersRegistry;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		this.isStarted = true;
	}
}
