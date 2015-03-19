package breads_and_aces.rmi.game.init.servable.registrar.registrars;

import breads_and_aces.rmi.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.rmi.game.model.Player;

public class GameRegistrarStarted implements GameRegistrar {

	@Override
	public boolean registerPlayerNode(Player nodeConnectionInfo) {
		// no more players accepted
		return false;
	}
	
	@Override
	public boolean isStarted() {
		return true;
	}
}
