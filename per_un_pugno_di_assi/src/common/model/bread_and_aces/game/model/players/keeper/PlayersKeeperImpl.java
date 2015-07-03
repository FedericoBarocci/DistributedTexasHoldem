package bread_and_aces.game.model.players.keeper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
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
public class PlayersKeeperImpl implements GamePlayersKeeper, RegistrarPlayersKeeper, FirstPlayersKeeper, NextablePlayersKeeper, /*, NavigablePlayersKeeper,*/ PlayersObservable {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
// we could use flyweight(gof use static way) - instead, we use singleton
//	private /*static*/ final Map<String, Player> playersMap = new LinkedHashMap<>();
	
	private final NavigableMap<PlayerRegistrationId, Player> navigableOrderedMap = new TreeMap<PlayerRegistrationId, Player>();
	private final Map<String, Player> simpleOrderedMap = new LinkedHashMap<>();
	
	private String me;
	private String leader;

	@Override
	public void addPlayer(PlayerRegistrationId playerRegistrationId, Player player) {
		navigableOrderedMap.put(playerRegistrationId, player);
		simpleOrderedMap.put(playerRegistrationId.getId(), player);
		notifyObservers( player.getName() );
	}
	
	@Override
	public void addPlayers(Map<PlayerRegistrationId, Player> players) {
		this.navigableOrderedMap.putAll(players);
		
		players.forEach((k,v)->{
			simpleOrderedMap.put(k.getId(), v);
		});
		
		notifyObservers( players.values().stream().map(Player::getName).collect(Collectors.joining(", ")) );
	}
	
	/*
	 * navigable zone - start 
	 */	
	/**
	 * @param playerId
	 * @return playerId successor
	 */
	@Override
	public Player getNext(String playerId) {
		// old workng
		Optional<PlayerRegistrationId> key = findKeyForNavigable(playerId);

		PlayerRegistrationId playerRegistrationId = key.get();

		return Optional
				.ofNullable(navigableOrderedMap.tailMap(playerRegistrationId, false).firstEntry())
				.orElse(navigableOrderedMap.firstEntry())
				.getValue();
		
		/*return Optional
				.ofNullable(playersMap.tailMap(playerId, false).firstEntry())
				.orElse(playersMap.firstEntry())
				.getValue();*/
		
		
		
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
	/*
	 * navigable zone - end 
	 */
	
	/*
	 * Firstable
	 */
	@Override
	public Player getFirst() {
		return new LinkedList<Player>( navigableOrderedMap.values() ).get(0);
	}
	
	
	
	@Override
	public boolean contains(String playerId) {
//		return findValue(playerId).isPresent();
		return simpleOrderedMap.containsKey(playerId);
	}
	
	private Optional<PlayerRegistrationId> findKeyForNavigable(String playerId) {
		Optional<PlayerRegistrationId> first = navigableOrderedMap.keySet().stream().parallel().filter(p->p.getId().equals(playerId)).findFirst();
		return first;
	}
	/*private Optional<Player> findValue(String playerId) {
		Optional<Player> first = navigableOrderedMap.values().stream().parallel().filter(p->{return p.getName().equals(playerId);}).findFirst();
		return first;
	}*/
	
	@Override
	public void remove(String playerId) {
		findKeyForNavigable(playerId).ifPresent(pri->{
			DevPrinter.print("removing "+pri.getId()+": ");
			navigableOrderedMap.remove(pri);
			DevPrinter.println("ok");
		});
		simpleOrderedMap.remove(playerId);
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
		return navigableOrderedMap;
	}

	/**
	 * @return Players list, in same insertion order
	 */
	@Override
	public List<Player> getPlayers() {
		// TODO why this ?!
		final LinkedList<Player> linkedList = new LinkedList<Player>();
		Collection<Player> players = 
//				navigableOrderedMap.values();
				simpleOrderedMap.values();
		linkedList.addAll( players  );
		
		AtomicReference<String> arMsg = new AtomicReference<>("");
		arMsg.set( arMsg.get()+ "returning actual players: ");
		players.stream().forEach(p-> arMsg.set( arMsg.get()+p.getName()+" ") );
		DevPrinter.println( arMsg.get() );
		
//		DevPrinter.println();
		return linkedList;
	}
	
	@Override
	public Player getPlayer(String playerId) {
//		return findValue(playerId).get();
		return simpleOrderedMap.get(playerId);
	}

	/*
	 * observer zone - start
	 */
	@Override
	public void addObserver(Observer<String> playerIdObserver) {
		observableDelegate.addObserver(playerIdObserver);
	}

	@Override
	public void removeObserver(Observer<String> playerIdObserver) {
		observableDelegate.removeObserver(playerIdObserver);
	}

	@Override
	public void notifyObservers(String playerId) {
		observableDelegate.notifyObservers(playerId);
	}
	/*
	 * observer zone - end
	 */

	@Override
	public void resetActions(boolean forceReset) {
		for (Player p : navigableOrderedMap.values()) {
			if (forceReset || !p.getAction().equals(Action.FOLD)) {
				p.setAction(Action.NONE);
			}
		}
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
