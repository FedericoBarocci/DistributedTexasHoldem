package breads_and_aces.registration.init.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.init.model.NodeConnectionInfos;
import breads_and_aces.registration.init.servable.registrar.GameRegistrar;
import breads_and_aces.registration.init.servable.registrar.RegistrationResult;
import breads_and_aces.registration.init.servable.registrar.RegistrationResult.Cause;

public class GameRegistrarStarted implements GameRegistrar {

	private final List<NodeConnectionInfos> registeredNodesConnectionInfos = new ArrayList<>();
	private final PlayersKeeper playersKeeper;
	
	@Inject
	public GameRegistrarStarted(PlayersKeeper playersKeeper) {
		this.playersKeeper = playersKeeper;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		return new RegistrationResult(false, Cause.GAME_STARTED);
	}
	
//	@Override
//	public boolean isStarted() {
//		return true;
//	}
	
	@Override
	public List<NodeConnectionInfos> getRegisteredNodesConnectionInfos() {
		return registeredNodesConnectionInfos;
	}
	
	@Override
	public Map<PlayerRegistrationId, Player> getRegisteredPlayer() {
		return playersKeeper.getIdsPlayersMap();
	}
	
	@Override
	public Player getFirst() {
		return playersKeeper.getPlayers().get(1);
	}

	public void passNodesInfos(List<NodeConnectionInfos> registeredNodesConnectionInfos) {
		this.registeredNodesConnectionInfos.addAll(registeredNodesConnectionInfos);
	}
}
