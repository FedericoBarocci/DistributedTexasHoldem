package breads_and_aces.registration.initializers.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult.Cause;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.utils.keepers.KeepersUtilDelegateForServable;

public class GameRegistrarInit implements GameRegistrar {

//	private final boolean gameNotStarted = false;
	private final KeepersUtilDelegateForServable keepersUtilsForServable;
	private final List<NodeConnectionInfos> nodesConnectionInfos = new ArrayList<>();
	
	@Inject
	public GameRegistrarInit(KeepersUtilDelegateForServable keepersUtilsForServable) {
		this.keepersUtilsForServable = keepersUtilsForServable;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		if (nodesConnectionInfos.size() == 0) { 
			// if size is zero, we are adding servable node
			nodesConnectionInfos.add(nodeConnectionInfos);
			keepersUtilsForServable.registerPlayer(playerId, true);
			// dummy return
			return new RegistrationResult(true, Cause.OK);
		} 
		else { 
			// here we add client nodes
			// if existing...
			if (keepersUtilsForServable.contains(playerId)) {
				return new RegistrationResult(false, Cause.EXISTING);
			}
			
			// if not existing...
			RegistrationResult registerResult = keepersUtilsForServable.registerClientableNodePlayerGameService(nodeConnectionInfos, playerId);
			
			if (registerResult.isAccepted())
				nodesConnectionInfos.add(nodeConnectionInfos);

			return registerResult;
		}
	}
	
	@Override
	public List<NodeConnectionInfos> getRegisteredNodesConnectionInfos() {
		return nodesConnectionInfos;
	}

	@Override
	public Map<PlayerRegistrationId, Player> getRegisteredPlayers() {
		return Collections.emptyMap();
	}

	
	/**
	 * do not invoke
	 * @return empty map
	 */
//	@Override
//	@Deprecated
//	public Map<PlayerRegistrationId,Player> getRegisteredPlayersMap() {
//		return Collections.emptyMap();
//	}
//	
	@Override
	@Deprecated
	public Player getFirst() {
		// too bad, but this is never invoked
		return new Player("dummy player", 0/*, printer*/);
	}
}
