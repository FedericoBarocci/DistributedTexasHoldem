package breads_and_aces.services.rmi.game.base.updatable;

import java.rmi.RemoteException;
import java.util.Collection;

public interface Updatable {
	void removeService(Collection<String> crashedPeers) throws RemoteException;
	void removeService(String crashedPeer) throws RemoteException;
}
