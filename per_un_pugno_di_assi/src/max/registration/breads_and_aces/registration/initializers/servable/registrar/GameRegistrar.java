package breads_and_aces.registration.initializers.servable.registrar;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.model.NodeConnectionInfos;

@Singleton
public interface GameRegistrar {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId);
	List<NodeConnectionInfos> getRegisteredNodesConnectionInfos();
//	boolean isStarted();
	Map<PlayerRegistrationId,Player> getRegisteredPlayersMap();
	Player getFirst();
}
