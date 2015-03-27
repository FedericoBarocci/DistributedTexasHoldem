package breads_and_aces.game.init.servable.registrar.registrars;

import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult.Cause;
import breads_and_aces.node.model.NodeConnectionInfos;

public class GameRegistrarStarted implements GameRegistrar {

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		return new RegistrationResult(false,Cause.GAME_STARTED);
	}
	
	@Override
	public boolean isStarted() {
		return true;
	}
}
