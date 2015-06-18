package bread_and_aces.services.rmi.game.base.updatable;

import java.rmi.RemoteException;

public interface Updatable {
//	void removeService(Collection<String> crashedPeers) throws RemoteException;
	void removeCrashedPeerService(String crashedPeer) throws RemoteException;
}
