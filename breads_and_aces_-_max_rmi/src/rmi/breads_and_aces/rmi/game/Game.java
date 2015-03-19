package breads_and_aces.rmi.game;

import javax.inject.Inject;

import breads_and_aces.rmi.game.model.Players;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private final Players players;
	private boolean isStarted = false;
	
	@Inject
	public Game(Players players) {
		this.players = players;
	}
	
	public Players getPlayers() {
		return players;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		this.isStarted = true;
	}
}
