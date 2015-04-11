package breads_and_aces.services.rmi.game.keeper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Singleton;

import breads_and_aces.services.rmi.game.core.GameService;

@Singleton
public class GameServicesKeeper {

	private final Map<String, GameService> servicesMap = new LinkedHashMap<>();

	public void addService(String playerId, GameService gameService) {
		servicesMap .put(playerId, gameService);
	}
	
	public Optional<GameService> getService(String playerId) {
		return Optional.ofNullable(servicesMap.get(playerId));
	}
	
	public void removeService(String playerId) {
		servicesMap.remove(playerId);
	}
	
	public Map<String, GameService> getServices() {
		return servicesMap;
	}

	public boolean contains(String playerId) {
		return servicesMap.containsKey(playerId);
	}

}
