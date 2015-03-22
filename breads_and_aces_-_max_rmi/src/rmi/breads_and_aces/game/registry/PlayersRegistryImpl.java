package breads_and_aces.game.registry;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import breads_and_aces.game.model.Player;
import breads_and_aces.game.model.PlayerRegistrationId;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class PlayersRegistryImpl implements PlayersRegistry, PlayersObservable {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
	// we could use flyweight(gof use static way) - instead, we use singleton
//	private /*static*/ final Map<String, Player> playersMap = new LinkedHashMap<>();
	
	private final NavigableMap<PlayerRegistrationId, Player> playersMap = new TreeMap<PlayerRegistrationId, Player>();
	
	
	@Override
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player) {
		playersMap.put(playerRegistrationId, player);
		notifyObservers(player.getId());
	}
	
	/**
	 * @return a map which key is playerId and value is player itself - order is the same of insertion
	 */
	@Override
	public NavigableMap<PlayerRegistrationId, Player> getIdsPlayersMap() {
		return playersMap;
	}
	
	/**
	 * @return Players list, in same insertion order
	 */
	@Override
	public List<Player> getPlayers() {
		return new LinkedList<Player>( playersMap.values() );
	}

	@Override
	public void setPlayers(Map<PlayerRegistrationId, Player> players) {
		this.playersMap.putAll(players);
		notifyObservers( players.values().stream().map(Player::getId).collect(Collectors.joining(", ")) );
	}

	/**
	 * @param playerId
	 * @return playerId successor
	 */
	@Override
	public Player getNext(String playerId) {
		return playersMap.tailMap(new PlayerRegistrationId(playerId), false).firstEntry().getValue();
	}

	@Override
	public boolean contains(String playerId) {
		return find(playerId).isPresent();
	}
	
	private Optional<Entry<PlayerRegistrationId, Player>> find(String playerId) {
		Optional<Entry<PlayerRegistrationId, Player>> findFirst = playersMap.entrySet().stream().filter(p->{return p.getKey().equals(playerId);}).findFirst();
		return findFirst;
	}
	
	@Override
	public void remove(String playerId) {
		find(playerId).ifPresent(c->{playersMap.remove(c.getKey());});
	}
	
	

	
	// observer zone	
	@Override
	public void addObserver(Observer<String> observer) {
		observableDelegate.addObserver(observer);
	}

	@Override
	public void removeObserver(Observer<String> observer) {
		observableDelegate.removeObserver(observer);
	}

	@Override
	public void notifyObservers(String data) {
		observableDelegate.notifyObservers(data);		
	}
}
