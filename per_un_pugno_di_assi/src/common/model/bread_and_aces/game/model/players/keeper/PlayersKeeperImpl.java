package bread_and_aces.game.model.players.keeper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.players.player.PlayerRegistrationId;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.observatory.ObservableDelegate;
import bread_and_aces.utils.observatory.Observer;

@Singleton
public class PlayersKeeperImpl implements GamePlayersKeeper, RegistrarPlayersKeeper, PlayersObservable {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
// we could use flyweight(gof use static way) - instead, we use singleton
//	private /*static*/ final Map<String, Player> playersMap = new LinkedHashMap<>();
	
	private final NavigableMap<PlayerRegistrationId, Player> playersMap = new TreeMap<PlayerRegistrationId, Player>();
	
	private String me;
	private String leader;

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
	
	/**
	 * 
	 */
	@Override
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player) {
//		Player put = 
				playersMap.put(playerRegistrationId, player);
		notifyObservers( player.getName() );
//		return Optional.ofNullable(put);
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
				.ofNullable(playersMap.tailMap(playerRegistrationId, false).firstEntry())
				.orElse(playersMap.firstEntry())
				.getValue();
		/*
		DevPrinter.println("searching next of "+playerId);
		Optional<PlayerRegistrationId> optionalPlayerRegistrationId = findKey(playerId);
//		try {
//DevPrinter.println("playerRegistrationId: "+optionalPlayerRegistrationId.get().getId() );
//		} catch (Exception e) {
//			DevPrinter.println("argh!");
//		}
	
		PlayerRegistrationId playerRegistrationId = optionalPlayerRegistrationId.orElse( playersMap.firstKey() );

//		PlayerRegistrationId playerRegistrationId = optionalPlayerRegistrationId.get();
DevPrinter.println("playerRegistrationId: "+playerRegistrationId.getId());
		Player value = 
//				Optional
//				.ofNullable( playersMap.tailMap(playerRegistrationId, false).firstEntry().getValue() )
//				.orElse( playersMap.firstEntry().getValue() )
//				.getValue()
				playersMap.get(playerRegistrationId);
				;
		DevPrinter.println("next: "+value.getName());
		return value;*/
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
		findKey(playerId).ifPresent(pri->{
			DevPrinter.print("removing "+pri.getId()+": ");
			playersMap.remove(pri);
			DevPrinter.println("ok");
		});
	}

	@Override
	public String getMyName() {
		return me;
	}

	@Override
	public Player getMyPlayer() {
		return getPlayer( getMyName() );
	}
	
	@Override
	public void setMyName(String playerId) {
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
		// why this ?!
		final LinkedList<Player> linkedList = new LinkedList<Player>();
		Collection<Player> players = playersMap.values();
		
		AtomicReference<String> arMsg = new AtomicReference<>("");
		arMsg.set( arMsg.get()+ "returning actual players: ");
		linkedList.addAll( players  );
		players.stream().forEach(p-> arMsg.set( arMsg.get()+p.getName()+" ") );
		DevPrinter.println( arMsg.get() );
//		DevPrinter.println();
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
	public void resetActions(boolean forceReset) {
		for (Player p : playersMap.values()) {
			if (forceReset || !p.getAction().equals(Action.FOLD)) {
				p.setAction(Action.NONE);
			}
		}
	}

	@Override
	public Player getFirst() {
		return new LinkedList<Player>( playersMap.values() ).get(0);
	}

	@Override
	public List<Player> getActivePlayers() {
		List<Player> activePlayers = new ArrayList<Player> ();
		
		getPlayers().forEach(p->{
			if (! p.getAction().equals(Action.FOLD)) {
				activePlayers.add(p);
			}
		});
		
		return activePlayers;
	}

	@Override
	public String getLeaderId() {
		return leader;
	}

	@Override
	public void setLeaderId(String leader) {
		this.leader = leader;
	}

	@Override
	public void setMyselfAsLeader() {
		Player myPlayer = getMyPlayer();
		myPlayer.receiveToken();
		
		setLeaderId( myPlayer.getName() );
	}
}
