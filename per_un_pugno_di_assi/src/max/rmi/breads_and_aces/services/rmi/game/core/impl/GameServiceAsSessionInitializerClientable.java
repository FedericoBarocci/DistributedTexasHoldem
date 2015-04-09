package breads_and_aces.services.rmi.game.core.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.core.AbstractGameService;
import breads_and_aces.services.rmi.game.core.GameServiceClientable;
import breads_and_aces.utils.keepers.KeepersUtilDelegate;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerClientable 
	extends AbstractGameService 
	implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	private final KeepersUtilDelegate keepersUtilDelegate;
	
	@AssistedInject
	public GameServiceAsSessionInitializerClientable(@Assisted String nodeId, 
			Game game, 
			GamePlayersKeeper playersKeeper
			, KeepersUtilDelegate keepersUtilDelegate
			) throws RemoteException {
		super(nodeId, game, playersKeeper);
		this.keepersUtilDelegate = keepersUtilDelegate;
	}

	@Override
	public void synchronizeAllNodesAndPlayersFromInitiliazer(List<NodeConnectionInfos> nodesConnectionInfos, Map<PlayerRegistrationId, Player> playersMap) throws RemoteException {
//		System.out.println("here");
//		List<String> crashedDuringSyncAlreadyLocallyRemoved = 
		keepersUtilDelegate.synchronizeNodesPlayersGameservicesLocallyAsClientable(nodesConnectionInfos, playersMap);
		
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
