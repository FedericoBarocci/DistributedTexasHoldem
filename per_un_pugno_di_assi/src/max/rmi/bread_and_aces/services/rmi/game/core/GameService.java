package bread_and_aces.services.rmi.game.core;


import bread_and_aces.services.rmi.game.base.actionable.Actionable;
import bread_and_aces.services.rmi.game.base.dealable.Dealable;
import bread_and_aces.services.rmi.game.base.echo.Echo;
import bread_and_aces.services.rmi.game.base.nodeable.Nodeable;
import bread_and_aces.services.rmi.game.base.pingable.Aliveable;
import bread_and_aces.services.rmi.game.base.updatable.Updatable;
import bread_and_aces.services.rmi.game.base.winnerable.Winnerable;

public interface GameService extends Echo, /*Bucketable,*/ Updatable, Actionable, Dealable, Winnerable, Nodeable, Aliveable {
	public static final String SERVICE_NAME = "DistributedHoldemGameService";
}
