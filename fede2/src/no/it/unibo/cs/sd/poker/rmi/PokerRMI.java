package it.unibo.cs.sd.poker.rmi;

import it.unibo.cs.sd.poker.game.Action;
import it.unibo.cs.sd.poker.game.Card;
import it.unibo.cs.sd.poker.game.Deck;
import it.unibo.cs.sd.poker.game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PokerRMI extends Remote {
	public Integer getTurn() throws RemoteException;
	public void setTurn(Integer turn) throws RemoteException;
	public Deck getDeck() throws RemoteException;
	public void setDeck(Deck deck) throws RemoteException;
	public List<Player> getPlayers() throws RemoteException;
	public void setPlayers(List<Player> players) throws RemoteException;
	public List<Card> getTableCards() throws RemoteException;
	public void setTableCards(List<Card> tableCards) throws RemoteException;
	public Boolean isExecuting() throws RemoteException;
	public void setExecute(Boolean execute) throws RemoteException;
	
	public void resetState() throws RemoteException;
	public void addPlayer(Player p) throws RemoteException;
	public void setPlayer(Integer index, Player player) throws RemoteException;
	public void setAction(Integer playerID, Action action) throws RemoteException;
	public void resetActions() throws RemoteException;
	
	public void next(RMIMessage msg) throws RemoteException;
	
	public void register(RemoteListener l) throws RemoteException;
	public void unregister(RemoteListener l) throws RemoteException;
	public void notifyListeners(RMIMessage param) throws RemoteException;
	public void notifyListener(Integer id, RMIMessage param) throws RemoteException;
}