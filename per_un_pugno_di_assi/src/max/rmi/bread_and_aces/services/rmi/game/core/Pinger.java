package bread_and_aces.services.rmi.game.core;

import java.rmi.RemoteException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.model.controller.DistributedController;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

@LazySingleton
public class Pinger {

	private static final long TIMEOUT = 5;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final GameServicesKeeper gameServicesKeeper;
	private final CrashHandler crashHandler;
	private final DistributedController distributedController;
	private final GamePlayersKeeper gamePlayersKeeper;

	private ScheduledFuture<?> beeperHandle;


	@Inject
	public Pinger(DistributedController distributedController, GameServicesKeeper gameServicesKeeper, CrashHandler crashHandler, GamePlayersKeeper gamePlayersKeeper) {
		this.distributedController = distributedController;
		this.gameServicesKeeper = gameServicesKeeper;
		this.crashHandler = crashHandler;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
	public void ping() {
		if (beeperHandle!=null) {
			beeperHandle.cancel(true);
		}
		
		beeperHandle = scheduler.scheduleAtFixedRate(getRunnable(), TIMEOUT, TIMEOUT , TimeUnit.SECONDS);
	}
	
	private Runnable getRunnable()  {
		return new Runnable() {
			@Override
			public void run() {
				String leader = distributedController.getLeader();
				Optional<GameService> optionalService = gameServicesKeeper.getService(leader);
				
				optionalService.ifPresent(s->{
					try {
						s.isAlive();
					} catch (RemoteException e) {
						DevPrinter.println("leader "+leader+" crashed: removing.. ");
						
						if (distributedController.setNextLeader().equals(gamePlayersKeeper.getMyName())) {
							distributedController.removeAndUpdate(leader);
							//ping();
						}
						else {
							crashHandler.removeLocallyFromEverywhere(leader);
						}
						
						//distributedController.setActionAndUpdate(leader, ActionKeeperFactory.build(Action.FOLD)).finaly();
						//crashHandler.removeLocallyFromEverywhere(leader);
						
						DevPrinter.println("leader "+leader+" crashed removed");
					}
				});
			}
		};
	}

}
