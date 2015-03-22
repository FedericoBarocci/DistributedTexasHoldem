package breads_and_aces.game.init.clientable.old_distinguish_abstract;

import breads_and_aces.game.init.clientable.observer.NewPlayersObserverAsClientable;
import breads_and_aces.game.model.Player;
import breads_and_aces.game.registry.PlayersObservable;
import breads_and_aces.game.registry.PlayersRegistry;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public abstract class AbstractGameInitializerClientable implements GameInitializerClientable {

	private final String initializingHostAddress;
	private final int initializingHostPort;
	private final PlayersRegistry players;

	@AssistedInject
	public AbstractGameInitializerClientable(@Assisted String initializingHostAddress, @Assisted int initializingHostPort, PlayersRegistry players) {
		this.initializingHostAddress = initializingHostAddress;
		this.initializingHostPort = initializingHostPort;
		this.players = players;
	}
	
	@Override
	public void initialize(Player nodeConnectionInfo) {
		((PlayersObservable)players).addObserver( new NewPlayersObserverAsClientable( /*nodeConnectionInfo.getId()*/ ) );
System.out.print("Starting as client: ");
		registerNodeThenDo(nodeConnectionInfo, initializingHostAddress, initializingHostPort);
	}
	
	protected abstract void registerNodeThenDo(Player nodeConnectionInfo, String initializingHostAddress, int initializingHostPort);

}
