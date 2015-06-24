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
import bread_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

@LazySingleton
public class Pinger {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final GameServicesKeeper gameServicesKeeper;
	private final CrashHandler crashHandler;
	private final DistributedController distributedController;
	private static final long TIMEOUT = 5;

	private ScheduledFuture<?> beeperHandle;

	@Inject
	public Pinger(DistributedController distributedController, GameServicesKeeper gameServicesKeeper, CrashHandler crashHandler) {
		this.distributedController = distributedController;
		this.gameServicesKeeper = gameServicesKeeper;
		this.crashHandler = crashHandler;
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
						DevPrinter.print(/*new Throwable(),*/ "pinging "+leader+": ");
						s.isAlive();
						System.out.println("ok");
					} catch (RemoteException e) {
						DevPrinter.println("leader "+leader+" crashed: removing.. ");
						crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(leader);
						distributedController.removePlayer(leader);
						DevPrinter.println("leader "+leader+" crashed removed");
					}
				});
			}
		};
	}

}
