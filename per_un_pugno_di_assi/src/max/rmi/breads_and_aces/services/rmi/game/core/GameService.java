package breads_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;

import breads_and_aces.services.rmi.game.base.bucketable.Bucketable;
import breads_and_aces.services.rmi.game.base.echo.Echo;

public interface GameService extends Echo, Bucketable {
	public static final String SERVICE_NAME = "TexasHoldemGameService";
	void receiveCheck(String fromPlayer) throws RemoteException;
	void receiveCall(String fromPlayer, int coins) throws RemoteException;
}
