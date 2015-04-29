package breads_and_aces.services.rmi.game.base.dealable;

import java.rmi.RemoteException;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.updater.GameUpdater;

public interface Dealable {
	void receiveActionAndDeal(String fromPlayer, Action action, GameUpdater gameUpdater) throws RemoteException;
}
