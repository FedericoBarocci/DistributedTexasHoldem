package breads_and_aces.game.init.servable.registrar.registrars;

import javax.inject.Inject;

import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;

public class GameRegistrarInit implements GameRegistrar {

	private boolean gameStarted;
	private final PlayersRegistry playersRegistry;
	private final NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	
	@Inject
	public GameRegistrarInit(NodesConnectionInfosRegistry nodesConnectionInfosRegistry, PlayersRegistry playersRegistry) {
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
		this.playersRegistry = playersRegistry;
	}

	@Override
	public boolean registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
//		players.addPlayer(player);
		
		long now = System.currentTimeMillis();
		
		if (playersRegistry.contains(playerId) || nodesConnectionInfosRegistry.contains(playerId))
			return false;
		
		nodeConnectionInfos.setRegisterTime(now);
		nodesConnectionInfosRegistry.addNodeInfo(nodeConnectionInfos);
		
		PlayerRegistrationId playerRegistrationId = new PlayerRegistrationId(playerId, now);
		Player player = new Player(playerId);
		
		playersRegistry.addPlayer(playerRegistrationId,player);
		
		return true;
	}
	
	@Override
	public boolean isStarted() {
		return gameStarted;
	}
}
