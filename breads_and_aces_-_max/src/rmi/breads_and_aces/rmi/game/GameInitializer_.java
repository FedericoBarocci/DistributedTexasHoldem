package breads_and_aces.rmi.game;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.Main;
import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.services.init.registrar.PlayerRegistrar;
import breads_and_aces.rmi.services.rmi.GameService;

/**
 * a Facade for GameStatus
 */
public class GameInitializer_ {
	
//	private Map<String, NodeConnectionInfo> players;
//	private final Game game;
	private final Players players;

	public GameInitializer_(Players players) {
		this.players = players;
//		this.game = game;
	}

	/*public void initializeSession(NodeConnectionInfo nodeConnectionInfo GameService gameServiceLocal, String nodeId) throws RemoteException {
		// add itself to game
		System.out.print("Adding myself as player: ");
		game.registerPlayer(nodeConnectionInfo);
		// here wait for remote connections
		waitForPlayersAndStartGame();
//		game.closeRegistrations();
		updateAllPlayers();
	}*/
	
	/*private void updateAllPlayers() throws RemoteException {
		Collection<NodeConnectionInfo> players = game.getPlayers().getPlayersNodeInfos().values();
		System.out.println("We have "+players.size()+" players");
		players.stream().forEach(n->{
			try {
				GameService gameServerFromRemoteNode = (GameService) Naming.lookup("rmi://"+n.getAddress()+":"+n.getPort()+"/"+GameService.SERVICE_NAME);
//				gameServerFromRemoteNode.echo(nodeConnectionInfo.getId(),  playersAsString);
					players.stream().forEach(p->{
						try {
							gameServerFromRemoteNode.registerPlayer(p);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					});
			} catch (RemoteException|MalformedURLException|NotBoundException e) {
				e.printStackTrace();
			}
		});
	}
	public static String START_GAME = "start";*/
	
	/*private void waitForPlayersAndStartGame() {
		System.out.println("Acting as initializer: waiting for players");
		Scanner scanner = new Scanner(System.in);
		String next = "";
		while(!next.equals(START_GAME)) {
			next = scanner.next();
		}
		scanner.close();
		System.out.println("Game can start!");
	}*/

//	@Override
	private void registerPlayer(NodeConnectionInfo nodeConnectionInfo) throws RemoteException {
		/*if (!game. isGameStarted()) {
//			String nodeId = nodeConnectionInfo.getId();
			game.registerPlayer(nodeConnectionInfo);
	//		optionalObserverNode.ifPresent(o->o.notifyNewPlayer(nodeId)); // O?
//			gameStatus.notifyObservers(nodeId);
		}*/
		players.addPlayer(nodeConnectionInfo);
//		game.registerPlayer(nodeConnectionInfo);
	}


	/*public static void connectToInitializedSession(NodeConnectionInfo nodeConnectionInfo) {
//		this.node = node;
		try {
			System.out.print("starting as client: ");
			PlayerRegistrar remoteService = (PlayerRegistrar) Naming.lookup("rmi://"+Main.initializingHostAddress+":"+Main.initializingHostPort+"/"+GameService.SERVICE_NAME);
			remoteService.registerPlayer(nodeConnectionInfo);
			System.out.println("added itself as new player");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}*/
}
