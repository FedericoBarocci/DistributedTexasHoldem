package breads_and_aces.services.rmi.game.impl;

import java.rmi.RemoteException;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.AbstractGameService;
import breads_and_aces.services.rmi.game.GameServiceServable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerServable 
	extends AbstractGameService 
	implements GameServiceServable {
	
	private final GameRegistrarProvider gameRegistrarProvider;

	@AssistedInject
	public GameServiceAsSessionInitializerServable(@Assisted String nodeId, Game game, NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry players, GameRegistrarProvider gameRegistrarProvider) throws RemoteException {
		super(nodeId, game, nodesConnectionInfosRegistry, players);
		this.gameRegistrarProvider = gameRegistrarProvider;
	}

	@Override
	public boolean registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) throws RemoteException {
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		
		System.out.println(gameRegistrar);
		
		return gameRegistrar.registerPlayer(nodeConnectionInfos, playerId);
		
	}
	
	private static final long serialVersionUID = 4075894245372521497L;
	
	/*private void createPlayer() {
		nodeConnectionInfo.setRegisterTime(now);
		nodesConnectionInfosRegistry.addNodeInfo(nodeConnectionInfo);
		
		PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
		Player player = new Player(playerId);
		
//		player.setRegisterPosition(now);
		playersRegistry.addPlayer(playerRegistrationId,player);
	}*/
}
