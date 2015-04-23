package breads_and_aces.game.model.players.keeper;

import it.unibo.cs.sd.poker.game.core.Action;

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
	
	private String me;

	/*
	@Override
	public boolean contains(String playerId) {
		for(Player p : players) {
			if (p.getName().equals(playerId)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void remove(String targetplayerId) {
		for(int i=0; i<players.size(); i++) {
			if (players.get(i).getName().equals(targetplayerId)) {
				players.remove(i);
				return;
			}
		}
	}
	*/
	
	@Override
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player) {
		playersMap.put(playerRegistrationId, player);
		notifyObservers(player.getName());
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

	@Override
	public String getMe() {
		return me;
	}
	
	@Override
	public void setMe(String playerId) {
		this.me = playerId;
	}

	@Override
	public Map<PlayerRegistrationId, Player> getRegisteredPlayers() {
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

	/*
	 * observer zone - start
	 */
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
	/*
	 * observer zone - end
	 */

	@Override
	public void resetActions() {
		for (Player p : playersMap.values()) {
			p.setAction(Action.NONE);
		}
	}
}
