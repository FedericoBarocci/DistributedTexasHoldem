package breads_and_aces.game.init.clientable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.game.init.GameInitializer;
import breads_and_aces.game.init.clientable.observer.NewPlayersObserverAsClientable;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.registry.PlayersObservable;
import breads_and_aces.game.registry.PlayersRegistry;
import breads_and_aces.node.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game._init.PlayersRegistrar;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class GameInitializerClientable implements GameInitializer {
	
	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final PlayersRegistry playersRegistry;
	private final Printer printer;

	@AssistedInject
	public GameInitializerClientable(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, PlayersRegistry playersRegistry, Printer printer) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.playersRegistry = playersRegistry;
		this.printer = printer;
	}
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId) {
		((PlayersObservable) playersRegistry).addObserver( new NewPlayersObserverAsClientable( ) );
		printer.print("Starting as client: ");
		registerNodeInfosPlayerIdThenDo(nodeConnectionInfo, playerId, initializingHostAddress, initializingHostPort);
	}
	
	private void registerNodeInfosPlayerIdThenDo(NodeConnectionInfos nodeConnectionInfo, String playerId, String initializingHostAddress, int initializingHostPort) {
		try {
			PlayersRegistrar remoteService = (PlayersRegistrar) ServiceUtils.lookup(initializingHostAddress, initializingHostPort);
			RegistrationResult registered = remoteService.registerPlayer(nodeConnectionInfo, playerId);
			
			if (registered.isAccepted())
				printer.println("initializer confirmed my registration as new player.");
			else {
				printer.println("initializer rejected my registration as new player, becaus: "+registered.getCause().name().toLowerCase().replace("_", " "));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (NotBoundException e) {
			printer.println("Something was wrong: remote host not bounded. Exit.");
			e.printStackTrace();
			System.exit(0);
		} catch(RemoteException e) {
			printer.println("Something was wrong: remote host not responding. Exit.");
			e.printStackTrace();
			System.exit(0);
		}
	}

}
