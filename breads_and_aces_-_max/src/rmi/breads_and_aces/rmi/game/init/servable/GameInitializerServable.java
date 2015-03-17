package breads_and_aces.rmi.game.init.servable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.inject.Inject;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Game;
import breads_and_aces.rmi.game.Players;
import breads_and_aces.rmi.game.init.GameInitializer;
import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.rmi.game.init.servable.registrar.registrars.GameRegistrarInit;
import breads_and_aces.rmi.game.init.servable.registrar.registrars.GameRegistrarStarted;
import breads_and_aces.rmi.services.rmi.game._init.PlayersSynchronizar;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.InputUtils;

public class GameInitializerServable implements GameInitializer {

	
	private final Players players;
	private final Game game;
	private NodeConnectionInfo me;
	private GameRegistrar gameRegistrar;
	
	@Inject
	public GameInitializerServable(GameRegistrarInit gameRegistrarStateInit, Game game, Players players) {
		this.gameRegistrar = gameRegistrarStateInit;
		this.game = game;
		this.players = players;
	}

	@Override
	public void initialize(NodeConnectionInfo itselfNodeConnectionInfo) throws RemoteException, MalformedURLException, NotBoundException {
		this.me = itselfNodeConnectionInfo;
		// add itself to game
		System.out.print("Adding myself as player: ");
		gameRegistrar.registerPlayer(itselfNodeConnectionInfo);
		
//		System.out.println(itselfNodseConnectionInfo.getId());
		players.addObserver( new NewPlayersObserverAsServable( itselfNodeConnectionInfo.getId()) );
		
		// here wait for remote connections
		waitForPlayersAndStartGame();
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted 
		updateAllPlayers();
		game.setStarted();		
	}
	
	

	private void updateAllPlayers() throws RemoteException, MalformedURLException, NotBoundException {
		Map<String, NodeConnectionInfo> nodeConnectionInfos = players.getPlayersNodeInfos();
		System.out.print("We have " + nodeConnectionInfos.size() + " players: "+me.getId()+" ");
		Iterator<NodeConnectionInfo> iterator = nodeConnectionInfos.values().iterator();
		while (iterator.hasNext()) {
			NodeConnectionInfo n = iterator.next();
			if (n.getId().equals(me.getId()))
				continue;
			
			System.out.print(n.getId()+" ");
			PlayersSynchronizar gameServerFromRemoteNode = ServiceUtils.lookup(n.getAddress(), n.getPort());
			gameServerFromRemoteNode.synchronizeAllPlayersFromInitiliazer(nodeConnectionInfos);
		}
		System.out.println("- ok");
	}

	// TODO change using GUI
	private void waitForPlayersAndStartGame() {
		System.out.println("Acting as initializer: waiting for players");
		Scanner scanner = InputUtils.getScanner();
		String next = "";
		while (!next.equals(START_GAME)) {
			next = scanner.next();
		}
		gameRegistrar = new GameRegistrarStarted();
		System.out.print("Game can start! ");
	}
	
	public static String START_GAME = "START";
}
