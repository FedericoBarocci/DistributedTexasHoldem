package breads_and_aces.rmi.game.init.servable.registrar.registrars;

import javax.inject.Inject;

import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;

public class GameRegistrarInit implements GameRegistrar {

	private boolean gameStarted;
	private final Players players;
	
	@Inject
	public GameRegistrarInit(Players players) {
		this.players = players;
	}

	@Override
	public boolean registerPlayerNode(Player nodeConnectionInfo) {
		players.addPlayer(nodeConnectionInfo);
		return true;
	}
	
	@Override
	public boolean isStarted() {
		return gameStarted;
	}
}
