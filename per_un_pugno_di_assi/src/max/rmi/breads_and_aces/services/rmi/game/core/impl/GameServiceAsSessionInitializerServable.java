package breads_and_aces.services.rmi.game.core.impl;

import java.rmi.RemoteException;

import breads_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.core.AbstractGameService;
import breads_and_aces.services.rmi.game.core.GameServiceServable;
import breads_and_aces.services.rmi.utils.crashhandler.CrashHandler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerServable extends
		AbstractGameService implements GameServiceServable {

	private static final long serialVersionUID = 4075894245372521497L;
	
	private final GameRegistrarProvider gameRegistrarProvider;

	@AssistedInject
	public GameServiceAsSessionInitializerServable(@Assisted String nodeId,
//			Game game, 
//			GamePlayersKeeper playersKeeper,
//			GameInitializerReal gameViewInitializer,
			DistributedController distributedController,
			CrashHandler crashHandler,
			GameRegistrarProvider gameRegistrarProvider) throws RemoteException {
		super(/*nodeId, playersKeeper,*/ distributedController, crashHandler);
		this.gameRegistrarProvider = gameRegistrarProvider;
	}

	@Override
	public RegistrationResult registerPlayer(
			NodeConnectionInfos nodeConnectionInfos, String playerId)
			throws RemoteException {
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		return gameRegistrar.registerPlayer(nodeConnectionInfos, playerId);
	}
}
