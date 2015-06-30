package bread_and_aces.services.rmi.game.core.impl;

import java.rmi.RemoteException;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import bread_and_aces.game.model.controller.DistributedControllerForRemoteHandling;
import bread_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.services.rmi.game.core.AbstractGameService;
import bread_and_aces.services.rmi.game.core.GameServiceServable;
import bread_and_aces.services.rmi.game.core.Pinger;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;

public class GameServiceAsSessionInitializerServable extends
		AbstractGameService implements GameServiceServable {

	private static final long serialVersionUID = 4075894245372521497L;
	
	private final GameRegistrarProvider gameRegistrarProvider;

	@AssistedInject
	public GameServiceAsSessionInitializerServable(@Assisted String nodeId,
			DistributedControllerForRemoteHandling distributedControllerForRemoteHandling,
			Pinger pinger,
			CrashHandler crashHandler,
			GameRegistrarProvider gameRegistrarProvider) throws RemoteException {
		super(nodeId, distributedControllerForRemoteHandling, pinger, crashHandler);
		this.gameRegistrarProvider = gameRegistrarProvider;
	}

	@Override
	public RegistrationResult registerPlayer(
			NodeConnectionInfos nodeConnectionInfos, String playerId) throws RemoteException {
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		return gameRegistrar.registerPlayer(nodeConnectionInfos, playerId);
	}
}
