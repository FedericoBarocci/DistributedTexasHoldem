package bread_and_aces.registration.initializers.servable;

import java.rmi.RemoteException;

import bread_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import bread_and_aces.game.Game;
import bread_and_aces.game.core.Deck;
import bread_and_aces.game.model.players.keeper.PlayersObservable;
import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.services.rmi.game.base._init.PlayersSynchronizar;
import bread_and_aces.services.rmi.game.base.dealable.Dealer;
import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.game.core.GameServiceClientable;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.printer.Printer;

public abstract class AbstractRegistrationInitializerServable implements RegistrationInitializerServable {
	
	private final String myId;
	private final GameRegistrarProvider gameRegistrarProvider;
	private final Communicator communicator;
	private final Table table;
	
	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	private final Game game;
	private final Printer printer;
	private final CrashHandler crashHandler;
	
	public AbstractRegistrationInitializerServable(String nodeId, 
			GameRegistrarProvider gameRegistrarProvider,
			RegistrarPlayersKeeper registrarPlayersKeeper,
			Communicator communicator,
			Table table,
			Game game,
			CrashHandler crashHandler,
			Printer printer
			) {
		this.myId = nodeId;
		this.gameRegistrarProvider = gameRegistrarProvider;
		this.registrarPlayersKeeper = registrarPlayersKeeper;
		this.communicator = communicator;
		this.table = table;
		this.game = game;
		this.crashHandler = crashHandler;
		this.printer = printer;
		((PlayersObservable)registrarPlayersKeeper).addObserver( new NewPlayersObserverAsServable( nodeId) );
	}

	@Override
	public void initialize(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		printer.println("Acting as initializer: waiting for players");

		registerMyPlayer(thisNodeConnectionInfo, playerId);
//		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsServable( playerId) );
		
		// here wait for remote connections
		waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();

		// here the thread is unlocked
		// after this line gameRegistrarStater will be referenced to GameRegistrarStarted
		closeRegistrations();
		giveCards();
		updateAllNodesForPartecipants();
		startGame();
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
	private void giveCards() {
		Dealer.deal(table, gameRegistrarProvider.get().getRegisteredPlayers().values(), new Deck() );
	}
	
	protected void updateAllNodesForPartecipants() {
		DevPrinter.println(new Throwable());
		communicator.toAll(myId, this::updatePartecipantsOnClientFunctor);
	}
	private void updatePartecipantsOnClientFunctor(GameService clientableGameServiceExternalInjected, String nodeId) {
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		PlayersSynchronizar ps = ((PlayersSynchronizar) clientableGameServiceExternalInjected);
		try {
			ps.synchronizeAllNodesAndPlayersFromInitiliazer(
				gameRegistrar.getRegisteredNodesConnectionInfos(),
				gameRegistrar.getRegisteredPlayers(),
				table.getAllCards(),
				game.getCoins(),
				game.getGoal()
			);
		} catch (RemoteException e) {
			// CRASH: it could happen servable sends infos to crashed clientable(s) 
			DevPrinter.println(new Throwable(), "remote exception");
//			try {
//				String nodeId = clientableGameServiceExternalInjected.getId();
//				System.out.println(nodeId);
				crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper( nodeId );
//			} catch(RemoteException e1) {
////				e.printStackTrace();
////				Main.handleException(e1);
//				System.out.println( e1 );
//			}
//			e.printStackTrace();

			//			distributedController.removePlayer(nodeId);
//			registrarPlayersKeeper.remove( nodeId );
//			 must remove in gameservicekeeper but also in playerskeeper .. and nodesconnectioninfos?  
		}
	}
	
	private void startGame() {
		printer.println("Game can start!");
		game.setStarted();
//		passBucket();
		printer.println("Inizio io perché comando io!");
		registrarPlayersKeeper.getMyPlayer().receiveToken();
		DevPrinter.println(new Throwable(), "just before to sayToAllStartGame");
		communicator.toAll(myId, this::sayToAllStartGame);
	}
	
	/*private void passBucket() {
		Player next = gameRegistrarProvider.get().getFirst();
		String nextId = next.getName();
//		printer.println("First player is: "+nextId);
		communicator.toOne(this::passBucket, nextId);
	}	
	private void passBucket(GameService gameServiceExternalInjected) throws RemoteException {
		gameServiceExternalInjected.receiveBucket();
		System.out.println("Ho passato bucket");
	}*/
	
	private void sayToAllStartGame(GameService gameServiceExternalInjected) {
		try {
			((GameServiceClientable) gameServiceExternalInjected).receiveStartGame(myId);
		} catch (RemoteException e) {
			System.out.println("sayToAllStartGame exception");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
