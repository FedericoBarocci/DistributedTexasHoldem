package breads_and_aces.rmi.game.init.clientable;

import breads_and_aces.rmi.game.model.Player;
import breads_and_aces.rmi.game.model.Players;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public abstract class AbstractGameInitializerClientable implements GameInitializerClientable {

	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final Players players;

	@AssistedInject
	public AbstractGameInitializerClientable(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, Players players) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.players = players;
	}
	
	@Override
	public void initialize(Player nodeConnectionInfo) {
		players.addObserver( new NewPlayersObserverAsClientable( /*nodeConnectionInfo.getId()*/ ) );
System.out.print("Starting as client: ");
		registerNodeThenDo(nodeConnectionInfo, initializingHostAddress, initializingHostPort);
	}
	
	protected abstract void registerNodeThenDo(Player nodeConnectionInfo, String initializingHostAddress, int initializingHostPort);

}
