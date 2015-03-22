package breads_and_aces.services.rmi.game.echo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Echo extends Remote {
	void echo(String nodeId, String string) throws RemoteException;
}