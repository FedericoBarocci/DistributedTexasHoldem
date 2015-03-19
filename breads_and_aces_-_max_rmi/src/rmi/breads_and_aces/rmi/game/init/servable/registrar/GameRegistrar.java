package breads_and_aces.rmi.game.init.servable.registrar;

import breads_and_aces.rmi.game.model.Player;

public interface GameRegistrar {
	boolean registerPlayerNode(Player nodeConnectionInfo);
	boolean isStarted();
}
