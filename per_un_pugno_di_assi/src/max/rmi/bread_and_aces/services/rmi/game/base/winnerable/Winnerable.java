package bread_and_aces.services.rmi.game.base.winnerable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.Message;

public interface Winnerable {
	void receiveWinnerEndGame(String fromPlayer, Message message) throws RemoteException;
}
