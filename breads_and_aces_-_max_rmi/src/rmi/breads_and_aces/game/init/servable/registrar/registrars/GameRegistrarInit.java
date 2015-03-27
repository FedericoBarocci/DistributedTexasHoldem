package breads_and_aces.game.init.servable.registrar.registrars;

import javax.inject.Inject;

import breads_and_aces.game.init.registrar.utils.RegistriesUtils;
import breads_and_aces.game.init.servable.registrar.GameRegistrar;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.game.init.servable.registrar.result.RegistrationResult.Cause;
import breads_and_aces.node.model.NodeConnectionInfos;

public class GameRegistrarInit implements GameRegistrar {

	private final boolean gameStarted = false;
	private final RegistriesUtils registriesUtils;
	
	@Inject
	public GameRegistrarInit(RegistriesUtils registriesUtils) {
		this.registriesUtils = registriesUtils;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		if (registriesUtils.contains(playerId))
			return new RegistrationResult(false, Cause.EXISTING);

		return registriesUtils.addNodePlayerGameService(nodeConnectionInfos, playerId);
	}
	
	@Override
	public boolean isStarted() {
		//still not started, so false
		return gameStarted; 
	}
}
