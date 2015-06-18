package bread_and_aces.registration.initializers.clientable;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.game.model.players.keeper.PlayersObservable;
import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.services.rmi.game.base._init.PlayersRegistrar;
import bread_and_aces.services.rmi.game.utils.ServiceUtils;
import bread_and_aces.utils.DevPrinter;

public abstract class AbstractRegistrationInitializerClientable implements RegistrationInitializerClientable {
	
	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final RegistrarPlayersKeeper playersKeeper;
//	private final CountDownLatch latch;

	@AssistedInject
	public AbstractRegistrationInitializerClientable(
			@Assisted String initializingHostAddress, 
			@Assisted int initializingHostPort, 
			RegistrarPlayersKeeper playersRegistry
//			, CountDownLatch latch
			) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.playersKeeper = playersRegistry;
//		this.latch = latch;
		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsClientable() );
	}
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId/*, CountDownLatch latch*/) {
		init(nodeConnectionInfo, playerId);
	}
	
	private void registerNodeInfosPlayerIdThenDo(NodeConnectionInfos nodeConnectionInfo, String playerId, String initializingHostAddress, int initializingHostPort) {
		try {
			PlayersRegistrar remoteService = (PlayersRegistrar) ServiceUtils.lookup(initializingHostAddress, initializingHostPort);
			RegistrationResult registrationResult = remoteService.registerPlayer(nodeConnectionInfo, playerId);
			
			DevPrinter.println( new Throwable(), "accepted: "+registrationResult.isAccepted() );
			
			if ( registrationResult.isAccepted() ) {
				playersKeeper.setMyName(playerId);
//				printer.println("initializer confirmed my registration as new player.");
				onAccepted(registrationResult);
			}
			else {
//				printer.println("initializer rejected my registration as new player, because: "+registered.getCause().name().toLowerCase().replace("_", " "));
				onRejected(registrationResult);
			}
		} catch (MalformedURLException e) {
			onError(e.getMessage());
		} catch (NotBoundException e) {
			onError("Something was wrong: remote host not bounded. Exit.");
		} catch(RemoteException e) {
			onError("Something was wrong: remote host not responding. Exit.");
		}
	}
	
//	@Override
//	public void goFurther() {
//		if (latch!=null)
//			latch.countDown();
//	}
	
	abstract protected void onError(String message);
	abstract protected void onAccepted(RegistrationResult registrationResult);
	abstract protected void onRejected(RegistrationResult registrationResult);

	protected void init(NodeConnectionInfos nodeConnectionInfo, String playerId) {
		registerNodeInfosPlayerIdThenDo(nodeConnectionInfo, playerId, initializingHostAddress, initializingHostPort);
	}
}
