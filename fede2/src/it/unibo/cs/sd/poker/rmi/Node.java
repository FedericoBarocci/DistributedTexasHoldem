package it.unibo.cs.sd.poker.rmi;

import it.unibo.cs.sd.poker.game.Player;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Node /*RemoteListener,*/ {

	public PokerRMI globalstate;
	private Player player;
	private Integer id;
	private Boolean token;
	
	/*  + Add properties:
	 * Host, port, service, playerName, etc.....
	 * */
	
	public Node() {}
	
	public void bindNetwork(Boolean leader) {
		setToken(leader);
		
		if (leader) {
			try {
				GlobalState obj = new GlobalState();
				Naming.rebind("rmi://localhost:5000/poker", obj);
				globalstate = obj;
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} 
		else {
			try {
				globalstate = (PokerRMI) Naming.lookup("rmi://localhost:5000/poker");
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			} 
			catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
		
		new GlobalListener(model, view);
	}

	public PokerRMI getGlobal() throws RemoteException {
		return this.globalstate;
	}

	public void setGlobal(GlobalState globalstate) {
		this.globalstate = globalstate;
	}

	public Player getLocal() {
		return this.player;
	}

	public void setLocal(Player player) {
		this.player = player;
	}
	
	public void broadcast(RMIMessage msg) throws RemoteException {
		this.getGlobal().notifyListeners(msg);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getToken() {
		return token;
	}

	public void setToken(Boolean token) {
		this.token = token;
	}
}
