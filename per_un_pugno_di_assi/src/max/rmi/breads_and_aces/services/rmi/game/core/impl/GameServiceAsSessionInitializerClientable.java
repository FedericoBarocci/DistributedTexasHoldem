package breads_and_aces.services.rmi.game.core.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import breads_and_aces.game.Game;
import breads_and_aces.game.core.Card;
import breads_and_aces.game.model.controller.DistributedController;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.registration.initializers.clientable.RegistrationInitializerClientable;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.core.AbstractGameService;
import breads_and_aces.services.rmi.game.core.GameServiceClientable;
import breads_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import breads_and_aces.utils.keepers.KeepersUtilDelegateForClientable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameServiceAsSessionInitializerClientable extends AbstractGameService implements GameServiceClientable {

	private static final long serialVersionUID = 5332389646575258965L;
	
	private final KeepersUtilDelegateForClientable keepersUtilDelegateForClientable;
	private final RegistrationInitializerClientable registrationInitializerClientable;
	private final Game game;
	private final Table table;
	private final DistributedController distributedController;
	
	@AssistedInject
	public GameServiceAsSessionInitializerClientable(
			@Assisted String nodeId,
			Table table,
			Game game,
			KeepersUtilDelegateForClientable keepersUtilDelegateForClientable,
			@Assisted RegistrationInitializerClientable registrationInitializerClientable,
			DistributedController distributedController,
			CrashHandler crashHandler) throws RemoteException {
		super(distributedController, crashHandler);
		this.table = table;
		this.game = game;
		this.keepersUtilDelegateForClientable = keepersUtilDelegateForClientable;
		this.registrationInitializerClientable = registrationInitializerClientable;
		this.distributedController = distributedController;
	}

	@Override
	public void synchronizeAllNodesAndPlayersFromInitiliazer(
			List<NodeConnectionInfos> nodesConnectionInfos,
			Map<PlayerRegistrationId, Player> playersMap,
			List<Card> tablesCard,
			int initialCoins,
			int initialGoal)
			throws RemoteException {
		keepersUtilDelegateForClientable.synchronizeNodesPlayersGameservicesLocallyAsClientable(nodesConnectionInfos, playersMap);
		table.getAllCards().addAll(tablesCard);

		// broadcast for update crashed player will be skipping here, because we don't really need this:
		// if any player crashs during each sync, we can hide this event until the bucket arrives to that 
		// (and the game will be in "opening" phase"), so its predecessor will handle its crash 
		
		// TODO too bad here, but it works
		game.setCoins(initialCoins);
		game.setGoal(initialGoal);
		game.setStarted();
		
		registrationInitializerClientable.goFurther();
	}
	
	@Override
	public void receiveStartGame(String whoHasToken) {
		distributedController.receiveStartGame(whoHasToken);
	}
}
