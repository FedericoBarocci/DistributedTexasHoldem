package bread_and_aces.services.rmi.game.base.actionable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.Action;

public interface Actionable {
	void receiveAction(String fromPlayer, Action action) throws RemoteException;
}
