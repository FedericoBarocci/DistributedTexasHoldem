package breads_and_aces.registration.initializers.clientable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.players.keeper.PlayersObservable;
import breads_and_aces.registration.initializers.RegistrationInitializer;
import breads_and_aces.registration.initializers.clientable.observer.NewPlayersObserverAsClientable;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.services.rmi.game.base._init.PlayersRegistrar;
import breads_and_aces.services.rmi.game.utils.ServiceUtils;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerClientable implements RegistrationInitializer {
	
	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final RegistrarPlayersKeeper playersKeeper;
	private final Printer printer;

	@AssistedInject
	public RegistrationInitializerClientable(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, RegistrarPlayersKeeper playersRegistry, Printer printer) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.playersKeeper = playersRegistry;
		this.printer = printer;
		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsClientable() );
	}
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId) {
//		((PlayersObservable) playersRegistry).addObserver( new NewPlayersObserverAsClientable( ) );
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
				printer.println("initializer rejected my registration as new player, because: "+registered.getCause().name().toLowerCase().replace("_", " "));
			}
		} catch (MalformedURLException e) {
			onError(e.getMessage());
		} catch (NotBoundException e) {
			onError("Something was wrong: remote host not bounded. Exit.");
		} catch(RemoteException e) {
			onError("Something was wrong: remote host not responding. Exit.");
		}
	}
	
	private void onError(String message) {
		printer.println(message);
		System.exit(0);
	}

}
