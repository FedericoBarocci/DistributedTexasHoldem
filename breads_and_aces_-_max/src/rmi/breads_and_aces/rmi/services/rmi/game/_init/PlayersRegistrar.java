package breads_and_aces.rmi.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;

import breads_and_aces.node.server.NodeConnectionInfo;

public interface PlayersRegistrar extends Remote {
	boolean registerPlayer(NodeConnectionInfo nodeConnectionInfo) throws RemoteException;
}
