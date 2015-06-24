package bread_and_aces.services.rmi.game.base.winnerable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.ActionKeeper;

public interface Winnerable {
	void receiveWinnerEndGame(String fromPlayer, ActionKeeper actionKeeper) throws RemoteException;
}
