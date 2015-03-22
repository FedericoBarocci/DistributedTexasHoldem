package breads_and_aces._di.providers;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.init.servable.registrar.registrars.GameRegistrarInit;
import breads_and_aces.game.init.servable.registrar.registrars.GameRegistrarStarted;

@Singleton
public class GameRegistrarProvider implements Provider<GameRegistrar> {

	private GameRegistrar instance;
	private GameRegistrarStarted gameRegistrarStarted;

	@Inject
	public GameRegistrarProvider(GameRegistrarInit gameRegistrarInit, GameRegistrarStarted gameStarted) {
		this.instance = gameRegistrarInit;
		this.gameRegistrarStarted = gameStarted;
	}

	public void changeRegistrar() {
		instance = gameRegistrarStarted;
	}

	@Override
	public GameRegistrar get() {
		return instance;
	}
}