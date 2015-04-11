package breads_and_aces.services.rmi.game.core;

import breads_and_aces.services.rmi.game.base.bucketable.Bucketable;
import breads_and_aces.services.rmi.game.base.echo.Echo;

public interface GameService extends Echo, Bucketable {
	public static final String SERVICE_NAME = "TexasHoldemGameService";
}
