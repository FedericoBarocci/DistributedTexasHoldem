package bread_and_aces.registration.initializers.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import bread_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;
import bread_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult.Cause;
import bread_and_aces.registration.model.NodeConnectionInfos;

public class GameRegistrarStarted implements GameRegistrar {

	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	
	private final List<NodeConnectionInfos> registeredNodesConnectionInfos = new ArrayList<>();
	
	@Inject
	public GameRegistrarStarted(RegistrarPlayersKeeper playersKeeper) {
		this.registrarPlayersKeeper = playersKeeper;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		return new RegistrationResult(false, Cause.GAME_STARTED);
	}
	
	@Override
	public List<NodeConnectionInfos> getRegisteredNodesConnectionInfos() {
		return registeredNodesConnectionInfos;
	}
	
	@Override
	public Player getFirst() {
		return registrarPlayersKeeper.getFirst();
	}

	public void passNodesInfos(List<NodeConnectionInfos> registeredNodesConnectionInfos) {
		this.registeredNodesConnectionInfos.addAll(registeredNodesConnectionInfos);
	}

	@Override
	public Map<PlayerRegistrationId, Player> getRegisteredPlayers() {
		return registrarPlayersKeeper.getRegisteredPlayers();
	}
	
//	@Override
//	public void removeCrashedPlayerOnRegistrationPhase(String playerId) {
//		registrarPlayersKeeper.remove(playerId);
//	}
}
