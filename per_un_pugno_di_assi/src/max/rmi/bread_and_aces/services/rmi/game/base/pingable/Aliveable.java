package bread_and_aces.services.rmi.game.base.pingable;

import java.rmi.RemoteException;

public interface Aliveable {
	void isAlive() throws RemoteException;
}
