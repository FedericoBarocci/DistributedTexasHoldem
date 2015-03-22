package breads_and_aces.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.node.model.NodeConnectionInfos;

public interface PlayersSynchronizar extends Remote {
	void synchronizeAllNodesAndPlayersFromInitiliazer(Map<String, NodeConnectionInfos> nodesConnectionInfosMap, Map<PlayerRegistrationId, Player> playersMap) throws RemoteException;
}
