package breads_and_aces.rmi.game.init.clientable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.node.server.NodeConnectionInfo;
import breads_and_aces.rmi.game.Players;
import breads_and_aces.rmi.game.init.GameInitializer;
import breads_and_aces.rmi.services.rmi.game._init.PlayersRegistrar;
import breads_and_aces.rmi.services.rmi.game.utils.ServiceUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameInitializerClientable implements GameInitializer {

	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final Players players;

	@AssistedInject
	public GameInitializerClientable(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, Players players) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.players = players;
	}
	
	@Override
	public void initialize(NodeConnectionInfo nodeConnectionInfo) throws RemoteException, MalformedURLException, NotBoundException {
		players.addObserver( new NewPlayersObserverAsClientable( /*nodeConnectionInfo.getId()*/ ) );
		System.out.print("Starting as client: ");
		PlayersRegistrar remoteService = (PlayersRegistrar) ServiceUtils.lookup(initializingHostAddress, initializingHostPort);
		boolean registered = remoteService.registerPlayer(nodeConnectionInfo);
		if (registered)
			System.out.println("initializer confirmed my registration as new player.");
//			if (registered) {
//				GameService gameService = (GameService) 
////						Naming.lookup("rmi://"+Main.initializingHostAddress+":"+Main.initializingHostPort+"/"+GameService.SERVICE_NAME);
//						ServiceUtils.lookup(initializingHostAddress, initializingHostPort);
//				// TODO do something with gameService
//			}
	}

}
