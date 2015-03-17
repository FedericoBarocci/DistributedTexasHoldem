package breads_and_aces.rmi.game.init.servable.registrar.registrars;

import java.rmi.RemoteException;

import javax.inject.Inject;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Players;
import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;

public class GameRegistrarInit implements GameRegistrar {

	private boolean gameStarted;
	private final Players players;
	
	@Inject
	public GameRegistrarInit(Players players) {
		this.players = players;
	}

	@Override
	public boolean registerPlayer(NodeConnectionInfo nodeConnectionInfo) throws RemoteException {
		players.addPlayer(nodeConnectionInfo);
//		System.out.println("added "+nodeConnectionInfo.getId()+" as new player");
		return true;
	}
	
	@Override
	public boolean isStarted() {
		return gameStarted;
	}
}
