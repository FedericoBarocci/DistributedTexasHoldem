package breads_and_aces.rmi.game.init;

import breads_and_aces.rmi.game.model.Player;

public interface GameInitializer {
	void initialize(Player player) /*throws RemoteException, MalformedURLException, NotBoundException*/;
}
