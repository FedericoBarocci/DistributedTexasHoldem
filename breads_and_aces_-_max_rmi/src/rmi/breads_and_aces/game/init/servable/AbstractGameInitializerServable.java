package breads_and_aces.game.init.servable;

import java.rmi.RemoteException;
import java.util.stream.Collectors;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersObservable;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.GameServicesRegistry;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game._init.PlayersSynchronizar;
import breads_and_aces.services.rmi.utils.Communicator;
import breads_and_aces.utils.printer.Printer;

public abstract class AbstractGameInitializerServable implements GameInitializerServable {
	
	protected final NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	protected final PlayersRegistry playersRegistry;
	protected final GameRegistrarProvider gameRegistrarProvider;
	protected final /*Nodes*/GameServicesRegistry gameServicesRegistry;
	protected final Game game;
	protected final Printer printer;
	
	private String meId;
//	private Boolean someNodeHasCrashed;
//	private final RegistriesUtils registriesUtils;
	private Communicator communicator;
	
	
	public AbstractGameInitializerServable(GameRegistrarProvider gameRegistrarProvider,
			/*Nodes*/GameServicesRegistry gameServicesRegistry,
			PlayersRegistry playersRegistry, 
			NodesConnectionInfosRegistry nodesConnectionInfosRegistry,
//			RegistriesUtils registriesUtils,
			Communicator communicator,
			Game game,
			Printer printer) {
		this.gameRegistrarProvider = gameRegistrarProvider;
		this./*nodesG*/gameServicesRegistry = gameServicesRegistry;
		this.playersRegistry = playersRegistry;
		this.nodesConnectionInfosRegistry = nodesConnectionInfosRegistry;
//		this.registriesUtils = registriesUtils;
		this.communicator = communicator;
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
		communicator.communicate(this::execUpdate);
		
		printer.print("Ok: final list partecipants has: ");
		playersRegistry.getIdsPlayersMap().values().stream().map(Player::getId).collect(Collectors.joining(", "));
	}
	
	private void execUpdate(GameService gameService) throws RemoteException {
		PlayersSynchronizar ps = ((PlayersSynchronizar) gameService);
		ps.synchronizeAllNodesAndPlayersFromInitiliazer(nodesConnectionInfosRegistry.getNodesConnectionInfosMap(), 
				playersRegistry.getIdsPlayersMap() );
	}
	
	
	/*private void iteratesUntilNoError() {
		do {
			iterateOnNodes();
		} while (someNodeHasCrashed);
	}	
	private boolean iterateOnNodes() {
		ListIterator<String> idsListIterator = new ArrayList<>(gameServicesRegistry.getServices().keySet()).listIterator();
		Map<String, NodeConnectionInfos> nodesConnectionInfosMap = nodesConnectionInfosRegistry.getNodesConnectionInfosMap();
		Map<PlayerRegistrationId, Player> playersMap = playersRegistry.getIdsPlayersMap();
		printer.println("We have " + nodesConnectionInfosMap.size() + "|"+playersMap.size()+ " nodes|players: "+meId+" ");
		
		while(idsListIterator.hasNext()) {
			String id = idsListIterator.next();
			Optional<GameService> service = gameServicesRegistry.getService(id);
			service.ifPresent(c->{
				try {
					PlayersSynchronizar ps = ((PlayersSynchronizar)c);
					ps.synchronizeAllNodesAndPlayersFromInitiliazer(nodesConnectionInfosMap, playersMap);
				} catch (RemoteException e) {
					// the node is unreachable, so remove
					registriesUtils.removeNodePlayerGameService(id);
					setSomeNodeHasCrashed(true);
					printer.println(id+" not responding, remove it.");
				}
			});			
		}
		return this.someNodeHasCrashed;
	}
	private void setSomeNodeHasCrashed(boolean crashed) {
		this.someNodeHasCrashed = crashed;
	}*/
	
	private void startGame() {
		printer.print("Game can start! ");
		game.setStarted();
		passToken();
	}
	private void passToken() {
		Player next = playersRegistry.getNext(meId);
//		next.receiveToken();
		printer.println("First player is: "+next.getId());
		// TODO TokenHandler here ?
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
