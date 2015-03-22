package breads_and_aces.game.init.servable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ListIterator;
import java.util.Map;

import breads_and_aces._di.providers.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.game.registry.PlayersObservable;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.NodesConnectionInfosRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.NodesGameServiceRegistry;
import breads_and_aces.services.rmi.game.GameService;
import breads_and_aces.services.rmi.game._init.PlayersSynchronizar;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

public abstract class AbstractGameInitializerServable implements GameInitializerServable {
	
	protected final NodesConnectionInfosRegistry nodesConnectionInfosRegistry;
	protected final PlayersRegistry playersRegistry;
	protected final GameRegistrarProvider gameRegistrarProvider;
	protected final NodesGameServiceRegistry nodesGameServiceRegistry;
	protected final Game game;
	protected final Printer printer;
	
	private String meId;
	
	
	public AbstractGameInitializerServable(GameRegistrarProvider gameRegistrarProvider,
			NodesGameServiceRegistry nodesGameServiceRegistry,
			Game game, 
			PlayersRegistry playersRegistry, 
			NodesConnectionInfosRegistry nodes, 
			Printer printer) {
		this.gameRegistrarProvider = gameRegistrarProvider;
		this.nodesGameServiceRegistry = nodesGameServiceRegistry;
		this.game = game;
		this.playersRegistry = playersRegistry;
		this.nodesConnectionInfosRegistry = nodes;
		this.printer = printer;
	}

	@Override
	public void initialize(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		printer.println("Acting as initializer: waiting for players");

		registerMyPlayer(thisNodeConnectionInfo, playerId);
		((PlayersObservable)playersRegistry).addObserver( new NewPlayersObserverAsServable( playerId) );
		
		// here wait for remote connections
		waitForPlayersAndStartGame();
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted
		closeRegistrations();
		updateAllNodes();
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
	protected abstract void waitForPlayersAndStartGame();
	private void closeRegistrations() {
		gameRegistrarProvider.changeRegistrar();
	}
	private void startGame() {
		printer.print("Game can start! ");
		game.setStarted();
		passToken();
	}
	private void passToken() {
		Player next = playersRegistry.getNext(meId);
		printer.println("First player is: "+next.getId());
		// TODO TokenHandler here
	}

	protected void updateAllNodes() {
		printer.print("We have " + nodesConnectionInfosRegistry.getNodesConnectionInfos().size() + " players: "+meId+" ");
		Map<String, NodeConnectionInfos> nodesConnectionInfosMap = nodesConnectionInfosRegistry.getNodesConnectionInfosMap();
//		Map<PlayerRegistrationId, Player> playersMap = playersRegistry.getIdsPlayersMap();
		ListIterator<NodeConnectionInfos> nodeConnectionInfosIterator = nodesConnectionInfosRegistry.getNodesConnectionInfos().listIterator();
//		boolean error = false;
		while (nodeConnectionInfosIterator.hasNext() /*&& !error*/) {
			NodeConnectionInfos nodeConnectionInfo = nodeConnectionInfosIterator.next();
			String nodeId = nodeConnectionInfo.getNodeId();
			if (nodeId.equals(meId)) {
//				System.out.println("skipping me "+meId);
				continue;
			}
			
			sendPlayersAndNodesConnectionInfosToNode(nodeId, nodeConnectionInfo.getAddress(), nodeConnectionInfo.getPort(), 
					nodesConnectionInfosMap,
					playersRegistry.getIdsPlayersMap(),
					nodeConnectionInfosIterator
			);
		}
		printer.println("- ok");
	}
	private void sendPlayersAndNodesConnectionInfosToNode(String targetnodeId, String targetnodeAddress, int targetnodePort,
			Map<String, NodeConnectionInfos> nodesConnectionInfosMap,
			Map<PlayerRegistrationId, Player> playersMap,
			ListIterator<NodeConnectionInfos> nodeConnectionInfosIterator) {
		
		try {
//			PlayersSynchronizar gameServerFromRemoteNode = 
			GameService lookup = ServiceUtils.lookup(targetnodeAddress, targetnodePort);
			PlayersSynchronizar gameServerFromRemoteNode = (PlayersSynchronizar) lookup;
			nodesGameServiceRegistry.add(targetnodeId, lookup);
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
	}
	
	public static String START_GAME = "START";
}
