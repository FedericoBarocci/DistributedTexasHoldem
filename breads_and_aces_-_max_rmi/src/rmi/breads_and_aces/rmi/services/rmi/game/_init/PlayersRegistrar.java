package breads_and_aces.rmi.services.rmi.game._init;

import java.rmi.Remote;
import java.rmi.RemoteException;

import breads_and_aces.rmi.game.model.Player;

public interface PlayersRegistrar extends Remote {
	boolean registerPlayer(Player nodeConnectionInfo) throws RemoteException;
}
