package breads_and_aces.registration.initializers.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import breads_and_aces.registration.initializers.servable.registrar.RegistrationResult.Cause;
import breads_and_aces.registration.model.NodeConnectionInfos;
import breads_and_aces.utils.keepers.KeepersUtilDelegate;

public class GameRegistrarInit implements GameRegistrar {

//	private final boolean gameNotStarted = false;
	private final KeepersUtilDelegate keepersUtils;
	private final List<NodeConnectionInfos> nodesConnectionInfos = new ArrayList<>();
	
	@Inject
	public GameRegistrarInit(KeepersUtilDelegate keepersUtils) {
		this.keepersUtils = keepersUtils;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		if (nodesConnectionInfos.size() == 0) { 
			// if size is zero, we are adding servable node
			nodesConnectionInfos.add(nodeConnectionInfos);
			keepersUtils.registerPlayer(playerId, true);
			// dummy return
			return new RegistrationResult(true, Cause.OK);
		} 
		else { 
			// here we add client nodes
			// if existing...
			if (keepersUtils.contains(playerId)) {
				return new RegistrationResult(false, Cause.EXISTING);
			}
			
			// if not existing...
			RegistrationResult registerResult = keepersUtils.registerNodePlayerGameServiceAsClientable(nodeConnectionInfos, playerId);
			
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
	public List<Player> getRegisteredPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Player getFirst() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
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
//	@Override
//	@Deprecated
//	public Player getFirst() {
//		// too bad, but this is never invoked
//		return new Player("dummy player", 0/*, printer*/);
//	}
}
