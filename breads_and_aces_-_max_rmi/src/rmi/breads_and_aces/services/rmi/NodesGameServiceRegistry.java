package breads_and_aces.services.rmi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Singleton;

import breads_and_aces.services.rmi.game.GameService;

@Singleton
public class NodesGameServiceRegistry {

	private final Map<String, GameService> services = new HashMap<>();

	public void add(String nodeId, GameService gameService) {
		services.put(nodeId, gameService);		
	}
	
	public void remove(String nodeId) {
		if (services.containsKey(nodeId))
			services.remove(nodeId);
	}
	
	public Optional<GameService> getService(String nodeId) {
		return Optional.ofNullable( services.get(nodeId) );
	}
	
}
