package breads_and_aces.rmi.game.init.servable.registrar.registrars;

import java.rmi.RemoteException;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;

public class GameRegistrarStarted implements GameRegistrar {

	@Override
	public boolean registerPlayer(NodeConnectionInfo nodeConnectionInfo) throws RemoteException {
		// no more players accepted
		return false;
	}
	
	@Override
	public boolean isStarted() {
		return true;
	}
}
