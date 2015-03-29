package breads_and_aces.services.rmi.game.impl;

import java.rmi.RemoteException;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.registry.PlayersShelf;
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
	public GameServiceAsSessionInitializerServable(@Assisted String nodeId, Game game, PlayersShelf playersShelf, GameRegistrarProvider gameRegistrarProvider) throws RemoteException {
		super(nodeId, game, playersShelf);
		this.gameRegistrarProvider = gameRegistrarProvider;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) throws RemoteException {
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		return gameRegistrar.registerPlayer(nodeConnectionInfos, playerId);
	}
	
	private static final long serialVersionUID = 4075894245372521497L;
}
