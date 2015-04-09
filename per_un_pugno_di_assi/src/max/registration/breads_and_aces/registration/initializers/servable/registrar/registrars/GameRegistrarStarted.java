package breads_and_aces.registration.initializers.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult.Cause;
import breads_and_aces.registration.model.NodeConnectionInfos;

public class GameRegistrarStarted implements GameRegistrar {

	private final List<NodeConnectionInfos> registeredNodesConnectionInfos = new ArrayList<>();
	private final RegistrarPlayersKeeper playersKeeper;
	
	@Inject
	public GameRegistrarStarted(RegistrarPlayersKeeper playersKeeper) {
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
	public Map<PlayerRegistrationId,Player> getRegisteredPlayersMap() {
		return playersKeeper.getIdsPlayersMap();
	}
	
	@Override
	public Player getFirst() {
		return ((GamePlayersKeeper)playersKeeper).getPlayers().get(1);
	}

	public void passNodesInfos(List<NodeConnectionInfos> registeredNodesConnectionInfos) {
		this.registeredNodesConnectionInfos.addAll(registeredNodesConnectionInfos);
	}
}
