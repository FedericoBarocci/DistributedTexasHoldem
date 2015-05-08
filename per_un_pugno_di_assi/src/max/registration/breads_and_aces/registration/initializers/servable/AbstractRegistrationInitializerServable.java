package breads_and_aces.registration.initializers.servable;

import java.rmi.RemoteException;

import bread_and_aces.utils.DevPrinter;
import breads_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import breads_and_aces.game.Game;
import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.players.keeper.PlayersObservable;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.base._init.PlayersSynchronizar;
import breads_and_aces.services.rmi.game.base.dealable.Dealer;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.core.GameServiceClientable;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.printer.Printer;

public abstract class AbstractRegistrationInitializerServable implements RegistrationInitializerServable {
	
	private final String myId;
	private final GameRegistrarProvider gameRegistrarProvider;
	private final Communicator communicator;
	private final Table table;
	
	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	private final Game game;
	private final Printer printer;
	
	public AbstractRegistrationInitializerServable(String nodeId, 
			GameRegistrarProvider gameRegistrarProvider,
			RegistrarPlayersKeeper registrarPlayersKeeper,
			Communicator communicator,
			Table table,
			Game game,
			Printer printer
			) {
		this.myId = nodeId;
		this.gameRegistrarProvider = gameRegistrarProvider;
		this.registrarPlayersKeeper = registrarPlayersKeeper;
		this.communicator = communicator;
		this.table = table;
		this.game = game;
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
		
//		communicator.toAll(myId, (gameService)->{
//			try {
//				updatePartecipantsOnClientFunction(gameService);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		
//		try {
		new DevPrinter(new Throwable()).println();
		communicator.toAll(myId, this::updatePartecipantsOnClientFunction);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void updatePartecipantsOnClientFunction(GameService clientGameServiceExternalInjected)/* throws RemoteException */{
		GameRegistrar gameRegistrar = gameRegistrarProvider.get();
		PlayersSynchronizar ps = ((PlayersSynchronizar) clientGameServiceExternalInjected);
		try {
			ps.synchronizeAllNodesAndPlayersFromInitiliazer(
				gameRegistrar.getRegisteredNodesConnectionInfos(),
				gameRegistrar.getRegisteredPlayers(),
				table.getAllCards(),
				game.getCoins(),
				game.getGoal()
			);
		} catch (RemoteException e) {
			new DevPrinter(new Throwable()).println("");
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	private void startGame() {
		printer.println("Game can start!");
		game.setStarted();
//		passBucket();
		printer.println("Inizio io perché comando io!");
		registrarPlayersKeeper.getMyPlayer().receiveToken();
		new DevPrinter(new Throwable()).println("just before to sayToAllStartGame");
		communicator.toAll(myId, this::sayToAllStartGame);
		
//		communicator.toAll(myId, (gameService) ->{
//				sayToAllStartGame(gameService);
//		});
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
	
	private void sayToAllStartGame(GameService gameServiceExternalInjected) /*throws RemoteException*/ {
		try {
			((GameServiceClientable) gameServiceExternalInjected).receiveStartGame(myId);
		} catch (RemoteException e) {
			System.out.println("sayToAllStartGame exception");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
