package breads_and_aces.game.model.players.keeper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.players.player.PlayerRegistrationId;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class PlayersKeeperImpl implements GamePlayersKeeper, RegistrarPlayersKeeper, PlayersObservable {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
	// we could use flyweight(gof use static way) - instead, we use singleton
//	private /*static*/ final Map<String, Player> playersMap = new LinkedHashMap<>();
	
	private final NavigableMap<PlayerRegistrationId, Player> playersMap = new TreeMap<PlayerRegistrationId, Player>();
	
	
	@Override
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player) {
		playersMap.put(playerRegistrationId, player);
		notifyObservers(player.getName());
	}
	
	/**
	 * @return a map which key is playerId and value is player itself - order is the same of insertion
	 */
	@Override
	public Map<PlayerRegistrationId, Player> getIdsPlayersMap() {
		return playersMap;
	}
	
	/**
	 * @return Players list, in same insertion order
	 */
	@Override
	public List<Player> getPlayers() {
		final LinkedList<Player> linkedList = new LinkedList<Player>();
		linkedList.addAll(playersMap.values());
		return linkedList;
	}
	
	@Override
	public Player getPlayer(String playerId) {
		return findValue(playerId).get();
	}

	@Override
	public void addPlayers(Map<PlayerRegistrationId, Player> players) {
		this.playersMap.putAll(players);
		notifyObservers( players.values().stream().map(Player::getName).collect(Collectors.joining(", ")) );
	}

	/**
	 * @param playerId
	 * @return playerId successor
	 */
	@Override
	public Player getNext(String playerId) {
		Optional<PlayerRegistrationId> key = findKey(playerId);
		PlayerRegistrationId playerRegistrationId = key.get();
		return Optional
				.ofNullable( playersMap.tailMap(playerRegistrationId, false).firstEntry() )
				.orElse( playersMap.firstEntry() )
				.getValue();
	}

	@Override
	public boolean contains(String playerId) {
		return findValue(playerId).isPresent();
	}
	
	private Optional<PlayerRegistrationId> findKey(String playerId) {
		Optional<PlayerRegistrationId> first = playersMap.keySet().stream().filter(p->p.getId().equals(playerId)).findFirst();
		return first;
	}
	private Optional<Player> findValue(String playerId) {
		Optional<Player> first = playersMap.values().stream().filter(p->{return p.getName().equals(playerId);}).findFirst();
		return first;
	}
	
	@Override
	public void remove(String playerId) {
		findKey(playerId).ifPresent(pri->{playersMap.remove(pri);});
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
