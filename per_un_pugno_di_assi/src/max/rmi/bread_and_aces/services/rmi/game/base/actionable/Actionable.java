package bread_and_aces.services.rmi.game.base.actionable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.Message;

public interface Actionable {
	void receiveAction(String fromPlayer, Message message) throws RemoteException;
}
