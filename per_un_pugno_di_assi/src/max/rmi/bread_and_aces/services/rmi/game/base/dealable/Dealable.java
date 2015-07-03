package bread_and_aces.services.rmi.game.base.dealable;

import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.Message;
import bread_and_aces.game.updater.GameUpdater;

public interface Dealable {
	void receiveActionAndDeal(String fromPlayer, Message message, GameUpdater gameUpdater) throws RemoteException;
}
