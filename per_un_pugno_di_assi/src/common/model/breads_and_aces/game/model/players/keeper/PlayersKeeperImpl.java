package breads_and_aces.game.model.players.keeper;

import it.unibo.cs.sd.poker.game.core.Action;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.utils.observatory.ObservableDelegate;
import breads_and_aces.utils.observatory.Observer;

@Singleton
public class PlayersKeeperImpl implements GamePlayersKeeper, RegistrarPlayersKeeper, PlayersObservable {

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
	public List<Player> getRegistredPlayers() {
		return getPlayers();
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

	@Override
	public void resetActions() {
		for (Player p : players) {
			p.setAction(Action.NONE);
		}
	}
	
	@Override
	public void resetPlayers(List<Player> players) {
		this.players.clear();
		addPlayers(players);
	}
}
