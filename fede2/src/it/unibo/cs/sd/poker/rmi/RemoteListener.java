package it.unibo.cs.sd.poker.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteListener extends Remote {
	public void remoteEvent (RMIMessage msg) throws RemoteException; 
}
