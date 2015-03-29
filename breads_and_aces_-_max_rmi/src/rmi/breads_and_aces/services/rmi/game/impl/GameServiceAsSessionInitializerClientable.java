package breads_and_aces.services.rmi.game.impl;

import java.rmi.RemoteException;
import java.util.Map;

import breads_and_aces.game.Game;
import breads_and_aces.game.init.registrar.utils.ShelfsUtils;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.AbstractGameService;
import breads_and_aces.services.rmi.game.GameServiceClientable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerClientable 
	extends AbstractGameService 
	implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	private final ShelfsUtils shelfsUtils;
	
	@AssistedInject
	public GameServiceAsSessionInitializerClientable(@Assisted String nodeId, 
			Game game, 
			PlayersShelf playersShelf
			, ShelfsUtils shelfsUtils
			) throws RemoteException {
		super(nodeId, game, playersShelf);
		this.shelfsUtils = shelfsUtils;
	}

	@Override
	public void synchronizeAllNodesAndPlayersFromInitiliazer(Map<String, NodeConnectionInfos> nodesConnectionInfosMap, Map<PlayerRegistrationId, Player> playersMap) throws RemoteException {
//		List<String> crashedDuringSyncAlreadyLocallyRemoved = 
		shelfsUtils.synchronizeNodesPlayersGameservicesLocallyAsClientable(nodesConnectionInfosMap, playersMap);
		
		// broadcast for update crashed player will be skipping here, because we don't really need this:
		// if any player crashs during each sync, we can hide this event until the bucket arrives to that 
		// (and the game will be in "opening" phase"), so its predecessor will handle its crash 
		
		// TODO too bad here, but it works
		game.setStarted();
	}
	
	/*private void update() {
		Map<String, GameService> services = gameServicesShelf.getServices();
		
	}*/
	
	/*private void updatePartecipants(GameService gameServiceExternalInjected) {
		PlayersSynchronizar ps = ((PlayersSynchronizar) gameServiceExternalInjected);
		if (gameServiceExternalInjected.getId().equals(nodeId);
		ps.synchronizeAllNodesAndPlayersFromInitiliazer(
				nodesConnectionInfosShelf.getNodesConnectionInfosMap(), 
				playersShelf.getIdsPlayersMap() 
				);
	}*/
}
