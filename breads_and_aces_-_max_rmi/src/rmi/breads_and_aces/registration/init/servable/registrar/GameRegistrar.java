package breads_and_aces.registration.init.servable.registrar;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.init.model.NodeConnectionInfos;

@Singleton
public interface GameRegistrar {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId);
	List<NodeConnectionInfos> getRegisteredNodesConnectionInfos();
//	boolean isStarted();
	Map<PlayerRegistrationId, Player> getRegisteredPlayer();
	Player getFirst();
}
