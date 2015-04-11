package breads_and_aces.registration.initializers.servable;

import java.rmi.RemoteException;

import breads_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.PlayersObservable;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.base._init.PlayersSynchronizar;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.printer.Printer;

public abstract class AbstractRegistrationInitializerServable implements RegistrationInitializerServable {
	
	protected final GameRegistrarProvider gameRegistrarProvider;
	protected final Communicator communicator;
	protected final Game game;
	protected final Printer printer;
	private final String meId;
//	private final CountDownLatch latch;
	
	public AbstractRegistrationInitializerServable(String nodeId, 
			GameRegistrarProvider gameRegistrarProvider,
			GameServicesKeeper gameServicesRegistry,
			RegistrarPlayersKeeper playersKeeper, 
			Communicator communicator,
			Game game,
//			CountDownLatch latch,
			Printer printer
			) {
		this.gameRegistrarProvider = gameRegistrarProvider;
		this.communicator = communicator;
		this.game = game;
		this.meId = nodeId;
		this.printer = printer;
//		this.latch = latch;
		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsServable( nodeId) );
	}

	@Override
	public void initialize(NodeConnectionInfos thisNodeConnectionInfo, String playerId/*, CountDownLatch latch*/) {
		printer.println("Acting as initializer: waiting for players");

		registerMyPlayer(thisNodeConnectionInfo, playerId);
//		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsServable( playerId) );
		
		// here wait for remote connections
		waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();

		// here the thread is unlocked
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted
		closeRegistrations();
		updateAllNodesForPartecipants();
		startGame();
//		if (latch!=null) 
//			latch.countDown();
	}
	private void registerMyPlayer(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		// add itself
		printer.print("Adding myself as player: ");
		gameRegistrarProvider.get().registerPlayer(thisNodeConnectionInfo, playerId);
		printer.println("ok");
	}
	protected abstract void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();
	private void closeRegistrations() {
		gameRegistrarProvider.changeRegistrar();
	}

	protected void updateAllNodesForPartecipants() {
//		printer.print("Ok: final list partecipants has: ");
//		printer.println(playersKeeper.getPlayers().stream().map(Player::getId).collect(Collectors.joining(", "))
//		);
		communicator.toAll(meId, this::updatePartecipantsOnClientFunction);
	}
	
	private void updatePartecipantsOnClientFunction(GameService clientGameServiceExternalInjected) throws RemoteException {
		PlayersSynchronizar ps = ((PlayersSynchronizar) clientGameServiceExternalInjected);
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		ps.synchronizeAllNodesAndPlayersFromInitiliazer(
			gameRegistrar.getRegisteredNodesConnectionInfos(),
			gameRegistrar.getRegisteredPlayersMap()
		);
	}
	
	private void startGame() {
		printer.print("Game can start! ");
		game.setStarted();
		passBucket();
	}
	private void passBucket() {
		Player next = 
//				playersKeeper.getNext(meId);
				gameRegistrarProvider.get().getFirst();
		String nextId = next.getName();
		printer.println("First player is: "+nextId);
		communicator.toOne(this::passBucket, nextId);
	}
	private void passBucket(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
	}
	

	/*protected void updateAllNodes() {
	printer.print("We have " + nodesConnectionInfosRegistry.getNodesConnectionInfos().size() + " players: "+meId+" ");
	Map<String, NodeConnectionInfos> nodesConnectionInfosMap = nodesConnectionInfosRegistry.getNodesConnectionInfosMap();
//	Map<PlayerRegistrationId, Player> playersMap = playersRegistry.getIdsPlayersMap();
	ListIterator<NodeConnectionInfos> nodeConnectionInfosIterator = nodesConnectionInfosRegistry.getNodesConnectionInfos().listIterator();
//	boolean error = false;
	while (nodeConnectionInfosIterator.hasNext() && !error) {
		NodeConnectionInfos nodeConnectionInfo = nodeConnectionInfosIterator.next();
		String nodeId = nodeConnectionInfo.getNodeId();
		if (nodeId.equals(meId)) {
//			System.out.println("skipping me "+meId);
			continue;
		}
		
		sendPlayersAndNodesConnectionInfosToNode(nodeId, nodeConnectionInfo.getAddress(), nodeConnectionInfo.getPort(), 
				nodesConnectionInfosMap,
				playersRegistry.getIdsPlayersMap(),
				nodeConnectionInfosIterator
		);
	}
	printer.println("- ok");
	}*/
	/*private void sendPlayersAndNodesConnectionInfosToNode(String targetnodeId, String targetnodeAddress, int targetnodePort,
			Map<String, NodeConnectionInfos> nodesConnectionInfosMap,
			Map<PlayerRegistrationId, Player> playersMap,
			ListIterator<NodeConnectionInfos> nodeConnectionInfosIterator) {
		
		try {
//			PlayersSynchronizar gameServerFromRemoteNode = 
			GameService lookup = ServiceUtils.lookup(targetnodeAddress, targetnodePort);
			PlayersSynchronizar gameServerFromRemoteNode = (PlayersSynchronizar) lookup;
//			gameServiceRegistry.add(targetnodeId, lookup);
			gameServerFromRemoteNode.synchronizeAllNodesAndPlayersFromInitiliazer(nodesConnectionInfosMap, playersMap);
			printer.print(targetnodeId+" ");
		} catch (MalformedURLException e) {
		} catch (RemoteException|NotBoundException e) {
			// remote host not responding, so remove
			playersRegistry.remove(targetnodeId);
//			playersMap.remove(targetnodeId);
			nodeConnectionInfosIterator.remove();
			printer.println("(removed: not responding)");
//			e.printStackTrace();
		}
	}*/
	
//	public static String START_GAME = "START";
}
