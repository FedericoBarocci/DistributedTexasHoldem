package breads_and_aces.rmi.game.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import breads_and_aces.utils.observatory.Observable;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class Players implements Observable<String> {

	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	
	// we could use flyweight(gof use static way) - instead, we use singleton
	private /*static*/ final Map<String, Player> playersMap = new LinkedHashMap<>();
	
	public void addPlayer(Player nodeConnectionInfo) {
		String id = nodeConnectionInfo.getId();
		playersMap.put(id, nodeConnectionInfo);
		notifyObservers(id);
	}
	
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
	
	public Map<String, Player> getPlayersInfos() {
		return playersMap;
	}

	public void setPlayers(Map<String, Player> players) {
		this.playersMap.clear();
		this.playersMap.putAll(players);
		notifyObservers( players.values().stream().map(Player::getId).collect(Collectors.joining(", ")) );
	}
}
