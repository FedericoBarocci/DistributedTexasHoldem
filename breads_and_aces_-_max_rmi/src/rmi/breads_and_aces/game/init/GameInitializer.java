package breads_and_aces.game.init;

import breads_and_aces.node.model.NodeConnectionInfos;

public interface GameInitializer {
	void initialize(NodeConnectionInfos nodeConnectionInfo, String playerId) /*throws RemoteException, MalformedURLException, NotBoundException*/;
}
