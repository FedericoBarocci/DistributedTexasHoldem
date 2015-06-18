package bread_and_aces.services.rmi.game.keeper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Singleton;

import bread_and_aces.services.rmi.game.core.GameService;

@Singleton
public class GameServicesKeeper {

	private final Map<String, GameService> servicesMap = new LinkedHashMap<>();

	public void addService(String nodeId, GameService gameService) {
		servicesMap.put(nodeId, gameService);
	}
	
	public Optional<GameService> getService(String playerId) {
		return Optional.ofNullable(servicesMap.get(playerId));
	}
	
	public void removeService(String nodeId) {
		servicesMap.remove(nodeId);
	}
	
	public Map<String, GameService> getServices() {
		return servicesMap;
	}

	public boolean contains(String playerId) {
		return servicesMap.containsKey(playerId);
	}

}
