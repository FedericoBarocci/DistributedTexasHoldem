package breads_and_aces.game.init.servable.registrar;

import javax.inject.Singleton;

import breads_and_aces.game.init.servable.registrar.result.RegistrationResult;
import breads_and_aces.node.model.NodeConnectionInfos;

@Singleton
public interface GameRegistrar {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId);
	boolean isStarted();
}
