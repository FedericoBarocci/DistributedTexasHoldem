package breads_and_aces.services.rmi.game.tokenable;

import java.rmi.RemoteException;

public interface TokenHandler {
	void receiveToken() throws RemoteException;
}
