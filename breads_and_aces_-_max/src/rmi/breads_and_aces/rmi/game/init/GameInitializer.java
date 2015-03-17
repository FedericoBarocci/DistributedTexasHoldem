package breads_and_aces.rmi.game.init;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.node.server.NodeConnectionInfo;

public interface GameInitializer {
	void initialize(NodeConnectionInfo nodeConnectionInfo) throws RemoteException, MalformedURLException, NotBoundException;
}
