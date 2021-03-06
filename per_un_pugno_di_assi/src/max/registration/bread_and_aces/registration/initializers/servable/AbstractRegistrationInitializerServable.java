package bread_and_aces.registration.initializers.servable;

import java.rmi.RemoteException;

import bread_and_aces._di.providers.registration.initializers.servable.registrar.GameRegistrarProvider;
import bread_and_aces.game.Game;
import bread_and_aces.game.core.Deck;
import bread_and_aces.game.model.players.keeper.PlayersObservable;
import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.services.rmi.game.base._init.PlayersSynchronizar;
import bread_and_aces.services.rmi.game.base.dealable.Dealer;
import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.game.core.GameServiceClientable;
import bread_and_aces.services.rmi.game.core.Pinger;
import bread_and_aces.services.rmi.utils.communicator.Communicator;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

public abstract class AbstractRegistrationInitializerServable implements RegistrationInitializerServable {
	
	private final String myId;
	private final GameRegistrarProvider gameRegistrarProvider;
	private final Communicator communicator;
	private final Table table;
	
	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	private final Game game;
//	private final Printer printer;
	private final CrashHandler crashHandler;
	private final Pinger pinger;
	
	public AbstractRegistrationInitializerServable(String nodeId, 
			GameRegistrarProvider gameRegistrarProvider,
			RegistrarPlayersKeeper registrarPlayersKeeper,
			Communicator communicator,
			Table table,
			Game game,
			Pinger pinger,
			CrashHandler crashHandler
//			,Printer printer
			) {
		this.myId = nodeId;
		this.gameRegistrarProvider = gameRegistrarProvider;
		this.registrarPlayersKeeper = registrarPlayersKeeper;
		this.communicator = communicator;
		this.table = table;
		this.game = game;
		this.pinger = pinger;
		this.crashHandler = crashHandler;
//		this.printer = printer;
		((PlayersObservable)registrarPlayersKeeper).addObserver( new NewPlayersObserverAsServable( nodeId) );
	}

	@Override
	public void initialize(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		DevPrinter.println("Acting as initializer: waiting for players");

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
		startPinger();
	}

	private void registerMyPlayer(NodeConnectionInfos thisNodeConnectionInfo, String playerId) {
		// add itself
		String msg = "Adding myself as player: ";
//		printer.print("Adding myself as player: ");
		RegistrationResult registrationResult = gameRegistrarProvider.get().registerPlayer(thisNodeConnectionInfo, playerId);
		msg+=registrationResult.isAccepted();
		DevPrinter.println(msg);
	}
	protected abstract void waitForRegistrationsClosingWhileAcceptPlayersThenStartGame();
	private void closeRegistrations() {
		gameRegistrarProvider.changeRegistrar();
	}
	private void giveCards() {
		Dealer.deal(table, gameRegistrarProvider.get().getRegisteredPlayers().values(), new Deck() );
	}
	
	protected void updateAllNodesForPartecipants() {
		DevPrinter.println();
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
			DevPrinter.println("remote exception");
			crashHandler.removeLocallyFromEverywhere(nodeId);
			// must remove in gameservicekeeper but also in playerskeeper .. and nodesconnectioninfos?  
		}
	}
	
	private void startGame() {
		DevPrinter.println("Game can start!");
		game.start();
		DevPrinter.println("Sono il primo a giocare");
		registrarPlayersKeeper.setMyselfAsLeader();
		communicator.toAll(myId, this::sayToAllStartGame);
	}
	
	private void sayToAllStartGame(GameService gameServiceExternalInjected) {
		try {
			((GameServiceClientable) gameServiceExternalInjected).receiveStartGame(myId);
		} catch (RemoteException e) {
			DevPrinter.println("sayToAllStartGame exception");
			e.printStackTrace();
		}
	}
	
	private void startPinger() {
		pinger.ping();		
	}
}
