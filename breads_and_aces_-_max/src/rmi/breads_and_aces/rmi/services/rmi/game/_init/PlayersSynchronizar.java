package breads_and_aces.rmi.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import breads_and_aces.node.server.NodeConnectionInfo;

public interface PlayersSynchronizar extends Remote {
	void synchronizeAllPlayersFromInitiliazer(Map<String, NodeConnectionInfo> nodeConnectionInfos) throws RemoteException;
}
