package breads_and_aces.rmi.game.init.servable;

import java.util.Iterator;
import java.util.Map;

import breads_and_aces.node.Node;
import breads_and_aces.node.NodesRegistry;
import breads_and_aces.node.model.ConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.rmi.game.init.servable.registrar.registrars.GameRegistrarInit;
import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;

public abstract class AbstractGameInitializerServable implements GameInitializerServable {
	
	protected final NodesRegistry nodes;
	protected final Players players;
	protected final Game game;
	protected Player me;
	protected GameRegistrar gameRegistrar;
	
	public AbstractGameInitializerServable(GameRegistrarInit gameRegistrarStateInit, Game game, Players players, NodesRegistry nodes) {
		this.gameRegistrar = gameRegistrarStateInit;
		this.game = game;
		this.players = players;
		this.nodes = nodes;
	}

	@Override
	public void initialize(Player thisPlayer) {
		this.me = thisPlayer;
		// add itself to game
System.out.print("Adding myself as player: ");
		gameRegistrar.registerPlayerNode(thisPlayer);
		nodes.addNode(nodeId, node);
		
//		System.out.println(itselfNodseConnectionInfo.getId());
		players.addObserver( new NewPlayersObserverAsServable( thisPlayer.getId()) );
		
		// here wait for remote connections
		waitForPlayersAndStartGame();
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted 
		updateAllNodes();
		game.setStarted();
	}
	protected abstract void waitForPlayersAndStartGame();

	protected void updateAllNodes() {
//		Map<String, Player> playersInfos = players.getPlayersInfos();
System.out.print("We have " + nodes.getNodes().size() + " players: +"
		+ ""+me.getId()+" ");
//		+ players.getPlayersNodeInfos().values().stream().map(NodeConnectionInfo::getId).collect(Collectors.joining(", ")) );
		Iterator<Node> iterator = 
//		= playersInfos.values().iterator();
		nodes.getNodes().iterator();
		while (iterator.hasNext()) {
//			Player player
			Node node 
			= iterator.next();
//			String nodeId = player.getId();
			String nodeId = node.getId();
			if (nodeId.equals(me.getId()))
				continue;
			
//			sendNodesConnectionInfosToNode(nodeId, playersInfos, players);
			sendNodesConnectionInfosToNode(nodeId, nodes);
		}
System.out.println("- ok");
	}
	protected abstract void sendNodesConnectionInfosToNode(String targetnodeId, ConnectionInfo targetnodeConnectionInfo, Map<String, Player> nodeConnectionInfos, Players players);
	protected abstract void sendNodesConnectionInfosToNode(String targetnodeId, ConnectionInfo targetnodeConnectionInfo, NodesRegistry nodes);
	
	public static String START_GAME = "START";
}
