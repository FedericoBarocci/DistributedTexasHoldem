package breads_and_aces.rmi.game.init.servable.registrar;

import java.rmi.RemoteException;

import breads_and_aces.node.server.NodeConnectionInfo;

public interface GameRegistrar {
	boolean registerPlayer(NodeConnectionInfo nodeConnectionInfo) throws RemoteException;
	boolean isStarted();
}
