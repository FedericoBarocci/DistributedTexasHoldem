package breads_and_aces.registration.init.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.registration.init.model.NodeConnectionInfos;
import breads_and_aces.registration.init.servable.registrar.GameRegistrar;
import breads_and_aces.registration.init.servable.registrar.RegistrationResult;
import breads_and_aces.registration.init.servable.registrar.RegistrationResult.Cause;
import breads_and_aces.utils.keepers.KeepersUtils;

public class GameRegistrarInit implements GameRegistrar {

//	private final boolean gameNotStarted = false;
	private final KeepersUtils keepersUtils;
	private final List<NodeConnectionInfos> nodesConnectionInfos = new ArrayList<>();
	
	@Inject
	public GameRegistrarInit(KeepersUtils keepersUtils) {
		this.keepersUtils = keepersUtils;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		if (nodesConnectionInfos.size()==0) { // if size is zero, we are adding servable node
			nodesConnectionInfos.add(nodeConnectionInfos);
			keepersUtils.registerPlayer(/*nodeConnectionInfos,*/ playerId);
			// dummy return
			return new RegistrationResult(true, Cause.OK);
		} else {
			if (keepersUtils.contains(playerId))
				return new RegistrationResult(false, Cause.EXISTING);
	
			RegistrationResult registerResult = keepersUtils.registerNodePlayerGameServiceAsServable(nodeConnectionInfos, playerId);
			if (registerResult.isAccepted())
				nodesConnectionInfos.add(nodeConnectionInfos);
//			System.out.println("registered: "+nodeConnectionInfos+" "+playerId);
			return registerResult;
		}
	}
	
	@Override
	public List<NodeConnectionInfos> getRegisteredNodesConnectionInfos() {
		return nodesConnectionInfos;
	}
	
//	@Override
//	public boolean isStarted() {
//		//still not started, so false
//		return gameNotStarted;
//	}

	/**
	 * do not invoke
	 * @return empty map
	 */
	@Override
	public Map<PlayerRegistrationId, Player> getRegisteredPlayer() {
		return Collections.emptyMap();
	}
	
	@Override
	public Player getFirst() {
		// too bad, but this is never invoked
		return null;
	}
}
