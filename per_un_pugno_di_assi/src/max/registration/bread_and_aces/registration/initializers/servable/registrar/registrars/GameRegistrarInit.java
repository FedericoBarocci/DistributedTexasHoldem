package bread_and_aces.registration.initializers.servable.registrar.registrars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;
import bread_and_aces.registration.initializers.servable.registrar.GameRegistrar;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult;
import bread_and_aces.registration.initializers.servable.registrar.RegistrationResult.RegistrationStatus;
import bread_and_aces.registration.model.NodeConnectionInfos;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.keepers.KeepersUtilDelegateForServable;

@Singleton
public class GameRegistrarInit implements GameRegistrar {

	private final KeepersUtilDelegateForServable keepersUtilsForServable;
	private final List<NodeConnectionInfos> nodesConnectionInfos = new ArrayList<>(0);
	
	@Inject
	public GameRegistrarInit(KeepersUtilDelegateForServable keepersUtilsForServable) {
		this.keepersUtilsForServable = keepersUtilsForServable;
	}

	@Override
	public RegistrationResult registerPlayer(NodeConnectionInfos nodeConnectionInfos, String playerId) {
		if (nodesConnectionInfos.size() == 0) {
			// if size is zero, we are adding servable node
			nodesConnectionInfos.add(nodeConnectionInfos);
			keepersUtilsForServable.registerServable(playerId);
			DevPrinter.println("registered servable "+playerId);
			// dummy return
			return new RegistrationResult(RegistrationStatus.OK);
		} 
		else { 
			// here we add client nodes
			// if existing...
			if (keepersUtilsForServable.contains(playerId)) {
				DevPrinter.print("sorry, existing "+playerId);
				return new RegistrationResult(RegistrationStatus.EXISTING, playerId);
			}
			
			// if not existing...
			RegistrationResult registerResult = keepersUtilsForServable.registerClientable(nodeConnectionInfos, playerId);
			
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
	 * @return dummy player
	 */
	@Override
	@Deprecated
	public Player getFirst() {
		// too bad, but this is never invoked
		return new Player("dummy player", 0/*, printer*/);
	}

//	@Override
//	public void removeCrashedPlayerOnRegistrationPhase(String playerId) {}
	
}
