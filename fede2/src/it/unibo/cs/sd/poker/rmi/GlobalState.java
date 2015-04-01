package it.unibo.cs.sd.poker.rmi;

import it.unibo.cs.sd.poker.game.Action;
import it.unibo.cs.sd.poker.game.Card;
import it.unibo.cs.sd.poker.game.Deck;
import it.unibo.cs.sd.poker.game.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GlobalState extends UnicastRemoteObject implements PokerRMI /*, RemoteListener*/ {

	private static final long serialVersionUID = 2307705310733990201L;

	private Integer turn = 0;
	
	private Deck deck = new Deck();
	private List<Player> players = new ArrayList<Player>();
	private List<Card> tableCards = new ArrayList<Card>();
	
	private Boolean execute;	
	
	private List<RemoteListener> listeners = new ArrayList<RemoteListener>();

	public GlobalState() throws RemoteException {}
	
	public Integer getTurn() throws RemoteException {
		return turn;
	}

	public void setTurn(Integer turn) throws RemoteException {
		this.turn = turn;
	}

	public Deck getDeck() throws RemoteException {
		return deck;
	}

	public void setDeck(Deck deck) throws RemoteException {
		this.deck = deck;
	}

	public List<Player> getPlayers() throws RemoteException {
		return players;
	}

	public void setPlayers(List<Player> players) throws RemoteException {
		this.players = players;
	}

	public List<Card> getTableCards() throws RemoteException {
		return tableCards;
	}

	public void setTableCards(List<Card> tableCards) throws RemoteException {
		this.tableCards = tableCards;
	}
	
	public Boolean isExecuting() throws RemoteException {
		return execute;
	}

	public void setExecute(Boolean execute) throws RemoteException {
		this.execute = execute;
	}

	public void resetState() throws RemoteException {
		setTurn(0);
		setDeck(new Deck());
		setTableCards(new ArrayList<Card>());
		setExecute(false);
	}
	
	public void addPlayer(Player p) throws RemoteException {
		this.players.add(p);
		
		notifyListeners(new RMIMessage(RMICommand.NEW_PLAYER, p));
	}
	
	public void setPlayer(Integer index, Player player) throws RemoteException {
		this.players.set(index, player);
	}
	
	public void setAction(Integer playerID, Action action) throws RemoteException {
		this.players.get(playerID).setAction(action);
	}
	
	public void resetActions() throws RemoteException {
		for(Player p : this.getPlayers()) {
			p.setAction(Action.NULL);
		}
	}
	
	public void next(RMIMessage msg) throws RemoteException {
		this.setTurn((this.getTurn() + 1) % this.getPlayers().size());
		
		notifyListener(this.getTurn(), msg);
	}
	
	public void register(RemoteListener listener) throws RemoteException {
		listeners.add(listener);
		notifyListeners(new RMIMessage(RMICommand.NEW_LISTENER));
	}

	public void unregister(RemoteListener listener) throws RemoteException {
		listeners.add(listener);
	}

	public void notifyListeners(RMIMessage msg) throws RemoteException {
		for(RemoteListener r : this.listeners) {
			r.remoteEvent(msg);
		}
	}
	
	public void notifyListener(Integer id, RMIMessage msg) throws RemoteException {
		this.listeners.get(id).remoteEvent(msg);
	}
}
