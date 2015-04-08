package it.unibo.cs.sd.poker.mvc;

import it.unibo.cs.sd.poker.exceptions.TurnException;
import it.unibo.cs.sd.poker.game.Action;
import it.unibo.cs.sd.poker.game.Card;
import it.unibo.cs.sd.poker.game.Deck;
import it.unibo.cs.sd.poker.game.GameTexasHoldem;
import it.unibo.cs.sd.poker.game.Player;
import it.unibo.cs.sd.poker.rmi.Node;
import it.unibo.cs.sd.poker.rmi.RMICommand;
import it.unibo.cs.sd.poker.rmi.RMIMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Model extends GameTexasHoldem {

	private static final long serialVersionUID = -6399531008858834768L;

	private Boolean leader;
	public Node node;
	
	public Model() {
		super();
	}
	
	public Boolean isLeader() {
		return this.leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}
	
	public Node getService() {
		return this.node;
	}
	
	public void setService(Boolean server) {
		this.node = new Node();
		this.node.bindNetwork(server);
	}
	
	public void startAsClient() {
		this.setLeader(false);
		this.setService(false);
	}
	
	public void startAsServer() {
		this.setLeader(true);
		this.setService(true);
	}
	
	public void setup() throws RemoteException {
		if( isLeader() ) {
			init();
			startGame();
		}
	}
	
	private void init() throws RemoteException {
		getService().getGlobal().resetActions();
		getService().getGlobal().setTurn(0);
		getService().getGlobal().setTableCards( new ArrayList<Card>() );
		getService().getGlobal().setDeck( new Deck() );
	}
	
	public void reset() throws RemoteException {
		getService().getGlobal().resetActions();
		getService().getGlobal().setTurn(0);
		getService().setToken(false);
		getService().broadcast( new RMIMessage(RMICommand.WAKE_UP) );
	}
	
	public void registerPlayer(Player player) throws RemoteException {
		getService().setLocal(player);
		getService().setId( getService().getGlobal().getPlayers().size() );
		getService().getGlobal().addPlayer(player);
	}
	
	public void updateLocalByGlobal() {
		try {
			super.setPlayers( getService().getGlobal().getPlayers() );
			super.setTableCards( getService().getGlobal().getTableCards() );
			super.setDeck( getService().getGlobal().getDeck() );
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void updateGlobalByLocal() {
		try {
			getService().getGlobal().setPlayers( super.getPlayers() );
			getService().getGlobal().setTableCards( super.getTableCards() );
			getService().getGlobal().setDeck( super.getDeck() );
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean isMyTurn(Integer myID) throws RemoteException {
		return isRunning() && myID == this.getTurn();
	}
	/*
	public Integer getTurn() throws RemoteException {
		return getService().getGlobal().getTurn();
	}*/
	/*
	public Boolean executeCheck() throws RemoteException {
		if (getService().getToken()) {
			getService().getGlobal().setAction(getService().getId(), Action.CHECK);
			getService().getGlobal().next( new RMIMessage(RMICommand.CHECK) );
			getService().setToken(false);
			
			return true;
		}
		else return false;
	}*/
	
	public Boolean isComplete() throws RemoteException {
		try {
			for (Player p : getService().getGlobal().getPlayers()) {
				if (! p.getAction().equals(Action.CHECK)) {
					throw(new TurnException());
				}
			}
		}
		catch(TurnException e) {
			return false;
		}
		
		return true;
	}
	
	public void stopGame() throws RemoteException {
		if(isLeader()) {
			getService().getGlobal().setExecute(false);
		}
	}
	
	public void startGame() throws RemoteException {
		if(isLeader()) {
			getService().getGlobal().setExecute(true);
		}
	}
	
	public Boolean isRunning() throws RemoteException {
		return getService().getGlobal().isExecuting();
	}
	
	public void tokenDeal() throws RemoteException {
		// Recupero il deck dallo stato globale
		Deck deck = getService().getGlobal().getDeck();
		
		// Deal sullo stato locale
		getService().getLocal().deal(deck);
		
		// Aggiorno lo stato locale del deck
		super.setDeck(deck);
		
		// Aggiorno lo stato globale del deck
		getService().getGlobal().setDeck(deck);
		
		// Aggiorno lo stato globale del player attuale
		getService().getGlobal().setPlayer(getTurn(), getService().getLocal());
	}
	
}
