package breads_and_aces.game.init.servable;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.NavigableMap;
import java.util.stream.Collectors;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersObservable;
import breads_and_aces.game.registry.PlayersShelf;
import breads_and_aces.node.NodesConnectionInfosShelf;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.GameServicesShelf;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game._init.PlayersSynchronizar;
import breads_and_aces.services.rmi.utils.ArgumentHolder;
import breads_and_aces.services.rmi.utils.Communicator;
import breads_and_aces.utils.printer.Printer;

public abstract class AbstractGameInitializerServable implements GameInitializerServable {
	
	protected final NodesConnectionInfosShelf nodesConnectionInfosRegistry;
	protected final PlayersShelf playersRegistry;
	protected final GameRegistrarProvider gameRegistrarProvider;
	protected final GameServicesShelf gameServicesRegistry;
	protected final Game game;
//	protected final TokenRinger tokenRinger;
	protected final Printer printer;
	
	private String meId;
	private Communicator communicator;
	
	
	public AbstractGameInitializerServable(GameRegistrarProvider gameRegistrarProvider,
			/*Nodes*/GameServicesShelf gameServicesRegistry,
			PlayersShelf playersRegistry, 
			NodesConnectionInfosShelf nodesConnectionInfosRegistry,
//			RegistriesUtils registriesUtils,
			Communicator communicator,
//			TokenRinger tokenRinger,
			Game game,
			Printer printer) {
		this.gameRegistrarProvider = gameRegistrarProvider;
		this./*nodesG*/gameServicesRegistry = gameServicesRegistry;
		this.playersRegistry = playersRegistry;
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
//		this.registriesUtils = registriesUtils;
		this.communicator = communicator;
//		this.tokenRinger = tokenRinger;
		this.game = game;
		this.printer = printer;
	}

	@Override
	public void initialize(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		printer.println("Acting as initializer: waiting for players");

		registerMyPlayer(thisNodeConnectionInfo, playerId);
		((PlayersObservable)playersRegistry).addObserver( new NewPlayersObserverAsServable( playerId) );
		
		// here wait for remote connections
		waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();
		
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted
		closeRegistrations();
		updateAllNodesForPartecipants();
		startGame();
	}
	private void registerMyPlayer(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		this.meId = playerId;
		// add itself to game
		printer.print("Adding myself as player: ");
		gameRegistrarProvider.get().registerPlayer(thisNodeConnectionInfo, playerId);
//		nodesConnectionInfosRegistry.addNodeInfo(thisNodeConnectionInfo);
		printer.println("ok");
	}
	protected abstract void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();
	private void closeRegistrations() {
		gameRegistrarProvider.changeRegistrar();
	}

	protected void updateAllNodesForPartecipants() {
		communicator.toAll(
				this::updatePartecipantsFunction,
				nodesConnectionInfosRegistry.getNodesConnectionInfosMap(),
				playersRegistry.getIdsPlayersMap()
				);
		
		printer.print("Ok: final list partecipants has: ");
		printer.println( 
				playersRegistry.getIdsPlayersMap().values().stream().map(Player::getId).collect(Collectors.joining(", "))
		);
	}
	
	private <T1,T2> void updatePartecipantsFunction(GameService gameServiceExternalInjected, 
			T1 nodesConnectionInfosMap, 
			T2 playersMap
			) throws RemoteException {
		PlayersSynchronizar ps = ((PlayersSynchronizar) gameServiceExternalInjected);
		ps.synchronizeAllNodesAndPlayersFromInitiliazer(
//				nodesConnectionInfosRegistry.getNodesConnectionInfosMap(),
				(Map<String, NodeConnectionInfos>) nodesConnectionInfosMap,
//				playersRegistry.getIdsPlayersMap()
				(NavigableMap<PlayerRegistrationId, Player>) playersMap
				);
	}
	/*private static class Holder {
		Map<String, NodeConnectionInfos> ns;
		NavigableMap<PlayerRegistrationId, Player> ps;
		public Holder(Map<String, NodeConnectionInfos> ns, NavigableMap<PlayerRegistrationId, Player> ps) {
			this.ns = ns;
			this.ps = ps;
		}
	}*/
	
	private void startGame() {
		printer.print("Game can start! ");
		game.setStarted();
		passToken();
	}
	private void passToken() {
		Player next = playersRegistry.getNext(meId);
		printer.println("First player is: "+next.getId());
		communicator.toOne(this::passToken, next.getId());
	}
	private void passToken(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveToken();
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
	
	public static String START_GAME = "START";
}
