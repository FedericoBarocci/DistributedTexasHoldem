package breads_and_aces.services.rmi.game.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
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
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.services.rmi.utils.CrashHandler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerClientable 
	extends AbstractGameService 
	implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	private final CrashHandler crashHandler;
	
	@AssistedInject
	public GameServiceAsSessionInitializerClientable(@Assisted String nodeId, Game game, NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry playersRegistry, CrashHandler crashHandler) throws RemoteException {
		super(nodeId, game, nodesConnectionInfosRegistry, playersRegistry);
		this.crashHandler = crashHandler;
	}

	@Override
	public void synchronizeAllNodesAndPlayersFromInitiliazer(Map<String, NodeConnectionInfos> nodesConnectionInfosMap, Map<PlayerRegistrationId, Player> playersMap) throws RemoteException {
		nodesConnectionInfosRegistry.setNodesConnectionInfos(nodesConnectionInfosMap);
		playersRegistry.setPlayers(playersMap);
		
		nodesConnectionInfosMap.entrySet().stream().forEach(entry->{
			String id = entry.getKey();
			NodeConnectionInfos nodeConnectionInfos = entry.getValue();
			try {
				ServiceUtils.lookup(nodeConnectionInfos.getAddress(), nodeConnectionInfos.getPort());
			} catch(MalformedURLException e) {
				
			} catch (NotBoundException | RemoteException e) {
				crashHandler.setHappenedCrash(true);
				crashHandler.addCrashed(id);
			}
		});
		if (crashHandler.isHappenedCrash())
			crashHandler.handle();
		
		// TODO too bad here, but it works
		game.setStarted();
	}
}
