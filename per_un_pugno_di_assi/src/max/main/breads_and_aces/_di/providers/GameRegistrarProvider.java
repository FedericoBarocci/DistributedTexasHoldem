package breads_and_aces._di.providers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.initializers.servable.registrar.registrars.GameRegistrarInit;
import breads_and_aces.registration.initializers.servable.registrar.registrars.GameRegistrarStarted;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class GameRegistrarProvider implements Provider<GameRegistrar> {

	private final GameRegistrarStarted gameRegistrarStarted;
	private final Printer printer;
	
	private GameRegistrar instance;

	@Inject
	public GameRegistrarProvider(GameRegistrarInit gameRegistrarInit, GameRegistrarStarted gameStarted, Printer printer) {
		this.instance = gameRegistrarInit;
		this.gameRegistrarStarted = gameStarted;
		this.printer = printer;
	}

	public void changeRegistrar() {
		gameRegistrarStarted.passNodesInfos( ((GameRegistrarInit)instance).getRegisteredNodesConnectionInfos() );
		instance = gameRegistrarStarted;
		
		Collection<Player> registeredPlayer = instance.getRegisteredPlayer().values();
		printer.print("Ok: final list partecipants has: ");
		printer.println(registeredPlayer.stream().map(Player::getName).collect(Collectors.joining(", "))
		);
	}

	@Override
	public GameRegistrar get() {
		return instance;
	}
}