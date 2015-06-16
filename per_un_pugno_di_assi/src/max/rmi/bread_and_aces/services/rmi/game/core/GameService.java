package bread_and_aces.services.rmi.game.core;


import java.rmi.RemoteException;

import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.services.rmi.game.base.actionable.Actionable;
import bread_and_aces.services.rmi.game.base.bucketable.Bucketable;
import bread_and_aces.services.rmi.game.base.dealable.Dealable;
import bread_and_aces.services.rmi.game.base.echo.Echo;
import bread_and_aces.services.rmi.game.base.updatable.Updatable;

public interface GameService extends Echo, Bucketable, Updatable, Actionable, Dealable {
	public static final String SERVICE_NAME = "DistributedHoldemGameService";
	
//	void receiveCheck(String fromPlayer, String toPlayer) throws RemoteException;
//	void receiveCall(String fromPlayer, int coins) throws RemoteException;
//	void receiveCheckAndDeal(String fromPlayer, String toPlayer, GameUpdater gameUpdater) throws RemoteException;
	
//	void receiveStartGame(String whoHasToken) throws RemoteException;
	void receiveWinnerEndGame(String fromPlayer, ActionKeeper actionKeeper) throws RemoteException;
}
