package breads_and_aces.services.rmi.game.impl;

import java.rmi.RemoteException;
import java.util.Map;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.AbstractGameService;
import breads_and_aces.services.rmi.game.GameServiceClientable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerClientable 
	extends AbstractGameService 
	implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	
	@AssistedInject
	public GameServiceAsSessionInitializerClientable(@Assisted String nodeId, Game game, NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry playersRegistry) throws RemoteException {
		super(nodeId, game, nodesConnectionInfosRegistry, playersRegistry);
	}

	@Override
	public void synchronizeAllNodesAndPlayersFromInitiliazer(Map<String, NodeConnectionInfos> nodesConnectionInfosMap, Map<PlayerRegistrationId, Player> playersMap) throws RemoteException {
		nodesConnectionInfosRegistry.setNodesConnectionInfos(nodesConnectionInfosMap);
		
		playersRegistry.setPlayers(playersMap);
		
		// TODO too bad here, but it works
		game.setStarted();
	}
}
