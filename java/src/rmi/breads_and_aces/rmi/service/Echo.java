package breads_and_aces.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Echo extends Remote {
	public void echo(String next) throws RemoteException;
}
