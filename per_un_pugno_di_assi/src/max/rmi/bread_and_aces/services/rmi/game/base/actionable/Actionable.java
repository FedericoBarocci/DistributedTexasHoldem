package bread_and_aces.services.rmi.game.base.actionable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.ActionKeeper;

public interface Actionable {
	void receiveAction(String fromPlayer, ActionKeeper actionKeeper) throws RemoteException;
}
