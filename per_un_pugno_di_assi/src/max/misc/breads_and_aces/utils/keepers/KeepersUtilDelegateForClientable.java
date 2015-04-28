package breads_and_aces.utils.keepers;

import java.util.List;
import java.util.Map;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.model.NodeConnectionInfos;

public interface KeepersUtilDelegateForClientable {
	List<String> synchronizeNodesPlayersGameservicesLocallyAsClientable(List<NodeConnectionInfos> nodesConnectionInfos, Map<PlayerRegistrationId, Player> playersMap);
}
