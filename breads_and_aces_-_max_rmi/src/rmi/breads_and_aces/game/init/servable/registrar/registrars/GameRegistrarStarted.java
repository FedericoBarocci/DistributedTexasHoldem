package breads_and_aces.game.init.servable.registrar.registrars;

import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.node.model.NodeConnectionInfos;

public class GameRegistrarStarted implements GameRegistrar {

	@Override
	public boolean registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		return false;
	}
	
	@Override
	public boolean isStarted() {
		return true;
	}
}
