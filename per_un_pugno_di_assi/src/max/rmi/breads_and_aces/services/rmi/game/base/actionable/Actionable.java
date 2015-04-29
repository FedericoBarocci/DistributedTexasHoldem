package breads_and_aces.services.rmi.game.base.actionable;

import java.rmi.RemoteException;

import breads_and_aces.game.model.oracle.actions.ActionSimple;

public interface Actionable {
	void receiveAction(String fromPlayer, ActionSimple action) throws RemoteException;
}
