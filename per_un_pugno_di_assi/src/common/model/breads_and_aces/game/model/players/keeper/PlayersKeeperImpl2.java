package breads_and_aces.game.model.players.keeper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class PlayersKeeperImpl2 implements GamePlayersKeeper, RegistrarPlayersKeeper2, PlayersObservable {
	
	private List<Player> players = new ArrayList<Player>();
	private final ObservableDelegate<String> observableDelegate = new ObservableDelegate<String>();
	private String me;

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

	@Override
	public String getMe() {
		return me;
	}
	
	@Override
	public void setMe(String playerId) {
		this.me = playerId;
	}

	@Override
	public void addPlayers(List<Player> players) {
		this.players.addAll(players);
		
		for (Player p : players) {
			notifyObservers(p.getName());
		}
	}

	@Override
	public List<String> getIdsPlayersMap() {
		List<String> list = new ArrayList<String>();
		
		for (Player p : players) { 
			list.add(p.getName());
		}
		
		return list;
	}

	@Override
	public Player getPlayer(String playerId) {
		for(Player p : players) { 
			if(p.getName().equals(playerId)) {
				return p;
			}
		}
		
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public Player getNext(String playerId) {
		for(int i=0; i<players.size(); i++) {
			if (players.get(i).getName().equals(playerId)) {
				return players.get((i + 1) % players.size());
			}
		}
		
		return null;
	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);
		notifyObservers(player.getName());
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
}
