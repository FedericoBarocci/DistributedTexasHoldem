package breads_and_aces.services.rmi.game.base._init;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import breads_and_aces.game.core.Card;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.model.NodeConnectionInfos;

public interface PlayersSynchronizar extends Remote {
	void synchronizeAllNodesAndPlayersFromInitiliazer(List<NodeConnectionInfos> nodesConnectionInfosMap, 
			Map<PlayerRegistrationId, Player> playersMap
			, List<Card> tablesCard,
			int initialCoins,
			int initialGoal
			) throws RemoteException;
}
