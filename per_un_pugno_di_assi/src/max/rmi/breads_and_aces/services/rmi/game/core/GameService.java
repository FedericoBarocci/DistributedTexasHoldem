package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.services.rmi.game.base.bucketable.Bucketable;
import breads_and_aces.services.rmi.game.base.echo.Echo;
import breads_and_aces.services.rmi.game.base.updatable.Updatable;

public interface GameService extends Echo, Bucketable, Updatable {
	public static final String SERVICE_NAME = "DistributedHoldemGameService";
	
//	void receiveCheck(String fromPlayer, String toPlayer) throws RemoteException;
//	void receiveCall(String fromPlayer, int coins) throws RemoteException;
//	void receiveCheckAndDeal(String fromPlayer, String toPlayer, GameUpdater gameUpdater) throws RemoteException;
	
//	void receiveStartGame(String whoHasToken) throws RemoteException;
	
	void receiveAction(String fromPlayer, Action action) throws RemoteException;
	void receiveActionAndDeal(String fromPlayer, Action action, GameUpdater gameUpdater) throws RemoteException;
	void receiveWinnerEndGame(String fromPlayer, Action action) throws RemoteException;
}
