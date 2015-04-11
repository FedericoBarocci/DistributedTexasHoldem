package breads_and_aces.registration.initializers.clientable._shell;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.registration.initializers.clientable.AbstractRegistrationInitializerClientable;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.utils.printer.Printer;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RegistrationInitializerClientableShell extends AbstractRegistrationInitializerClientable {
	
	private final Printer printer;

	@AssistedInject
	public RegistrationInitializerClientableShell(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, RegistrarPlayersKeeper playersRegistry, Printer printer) {
		super(initializingHostAddress, initializingHostPort, playersRegistry);
//		this.initializingHostAddress = initializingHostAddress;
//		this.initializingHostPort = initializingHostPort;
//		this.playersKeeper = playersRegistry;
		this.printer = printer;
//		((PlayersObservable)playersKeeper).addObserver( new NewPlayersObserverAsClientable() );
	}
	
	@Override
	public void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId/*, CountDownLatch latch*/) {
//		((PlayersObservable) playersRegistry).addObserver( new NewPlayersObserverAsClientable( ) );
		printer.print("Starting as client: ");
		super.init(nodeConnectionInfo, playerId);
//		if (latch!=null)
//			latch.countDown();
//		goFurther();
	}
	
	@Override
	protected void onError(String message) {
		printer.println(message);
		System.exit(0);
	}

	@Override
	protected void onAccepted(RegistrationResult registrationResult) {
		printer.println("initializer confirmed my registration as new player.");
	}

	@Override
	protected void onRejected(RegistrationResult registrationResult) {
		printer.println("initializer rejected my registration as new player, because: "+registrationResult.getCause().name().toLowerCase().replace("_", " "));
	}

	@Override
	public void goFurther() {}

}
