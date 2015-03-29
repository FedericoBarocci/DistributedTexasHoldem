package breads_and_aces.game;

import javax.inject.Inject;

import breads_and_aces.game.registry.PlayersShelf;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final PlayersShelf playersRegistry;
	private boolean isStarted = false;
	
	@Inject
	public Game(PlayersShelf playersRegistry) {
		this.playersRegistry = playersRegistry;
	}
	
	public PlayersShelf getPlayers() {
		return playersRegistry;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		this.isStarted = true;
	}
}
