package breads_and_aces.registration;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final PlayersKeeper playersRegistry;
	private boolean isStarted = false;
	
	@Inject
	public Game(PlayersKeeper playersRegistry) {
		this.playersRegistry = playersRegistry;
	}
	
	public PlayersKeeper getPlayers() {
		return playersRegistry;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		this.isStarted = true;
	}
}
