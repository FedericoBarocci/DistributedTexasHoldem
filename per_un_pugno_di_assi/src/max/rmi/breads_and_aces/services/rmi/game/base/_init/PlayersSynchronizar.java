package breads_and_aces.services.rmi.game.base._init;

import it.unibo.cs.sd.poker.game.core.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.registration.model.NodeConnectionInfos;

public interface PlayersSynchronizar extends Remote {
//	void synchronizeAllNodesAndPlayersFromInitiliazer(List<NodeConnectionInfos> nodesConnectionInfosMap, 
//			Map<PlayerRegistrationId, Player> playersMap
//			, List<Card> tablesCard
//			) throws RemoteException;
	void synchronizeAllNodesAndPlayersFromInitiliazer(
			List<NodeConnectionInfos> nodesConnectionInfosMap,
			List<Player> playersMap, List<Card> tablesCard)
			throws RemoteException;
}
