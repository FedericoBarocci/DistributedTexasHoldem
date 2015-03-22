package breads_and_aces.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;

import breads_and_aces.node.model.NodeConnectionInfos;

public interface PlayersRegistrar extends Remote {
	boolean registerPlayer(NodeConnectionInfos nodeConnectionInfo, String playerId) throws RemoteException;
}
