package bread_and_aces.registration.initializers.servable.registrar;

import java.util.List;
import java.util.Map;

import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;
import bread_and_aces.registration.model.NodeConnectionInfos;

//@Singleton
public interface GameRegistrar {
	RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId);
	List<NodeConnectionInfos> getRegisteredNodesConnectionInfos();
	Map<PlayerRegistrationId, Player> getRegisteredPlayers();
	Player getFirst();
}
