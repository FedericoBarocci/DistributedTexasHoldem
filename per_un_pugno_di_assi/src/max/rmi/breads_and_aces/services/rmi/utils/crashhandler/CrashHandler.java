package breads_and_aces.services.rmi.utils.crashhandler;

import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.utils.DevPrinter;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Deliverator;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class CrashHandler {

//	private final KeepersUtilDelegate registriesUtils;
	private final GameServicesKeeper gameServicesKeeper;
	private final Printer printer;
	private final Deliverator deliverator;
	
//	private boolean isExistingCrash = false;
	
	
	@Inject
	public CrashHandler(/*KeepersUtilDelegate keepersUtilDelegate,*/ GameServicesKeeper gameServicesKeeper, Deliverator deliverator, Printer printer) {
//		this.registriesUtils = keepersUtilDelegate;
		this.gameServicesKeeper = gameServicesKeeper;
		this.deliverator = deliverator;
		this.printer = printer;
	}
	
//	public void noMoreCrash() {
//		setHappenedCrash(false);
//	}
//	private void setHappenedCrash(boolean isExistingCrashed) {
//		this.isExistingCrash = isExistingCrashed;
//	}
//	public boolean isHappenedCrash() {
//		return isExistingCrash;
//	}

	public void handleCrashLocallyRemovingFromLocalGameServiceKeeper(String id) {
//		setHappenedCrash(true);
		gameServicesKeeper.removeService(id);
		printer.println(id+" not responding, removed it.");
	}
	/*public void removeFromLocalGameServiceKeeper(List<String> crashedDuringSync) {
//		setHappenedCrash(true);
		ListIterator<String> listIterator = crashedDuringSync.listIterator();
		while (listIterator.hasNext()) {
			String next = listIterator.next();
//			registriesUtils.removePlayerGameService(next);
			gameServicesKeeper.removeService(next);
			System.out.println("removed "+next+" from GameServiceKeeper");
			listIterator.remove();
		}
	}*/
	public void removeFromLocalGameServiceKeeper(String crashedDuringSync) {
		gameServicesKeeper.removeService(crashedDuringSync);
		
		new DevPrinter(new Throwable()).println("removed "+crashedDuringSync+" from GameServiceKeeper");
	}
	
	/*public List<String> handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(String meId, List<String> eventuallyCrashedPeers) {
		boolean inCrash = false;
		if (eventuallyCrashedPeers.size() > 0)
			inCrash = true;
		while (inCrash) {
			// nodes are unreachable, so handle the crash removing services
			removeFromLocalGameServiceKeeper(eventuallyCrashedPeers);
			
			// we say to all to update players removing those specified in list
			List<String> eventuallyCrashedAgain = deliverator.broadcast(meId, this::updatePlayersFunctor, new CrashedHolder(eventuallyCrashedPeers));
			System.out.println("CrashHandler:70 - eventuallyCrashedAgain = "+eventuallyCrashedAgain);
			if (eventuallyCrashedAgain.size() > 0) {
				eventuallyCrashedPeers.addAll(eventuallyCrashedAgain);
			} else {
				inCrash = false;
			}
		}
		return eventuallyCrashedPeers;
	}
	private void updatePlayersFunctor(GameService gameService, CrashedHolder crashedHolderPeers) {
		try {
			System.out.println("CrashHandler:80");
			gameService.removeService( crashedHolderPeers.getCrashed() );
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}*/
	

	public void handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(String meId, String crashedPeer) {
//		boolean inCrash = false;
//		if (eventuallyCrashedPeers.size() > 0)
//			inCrash = true;
//		while (inCrash) {
			// nodes are unreachable, so handle the crash removing services
			removeFromLocalGameServiceKeeper(crashedPeer);
			
			// we say to all to update players removing those specified in list
			List<String> eventuallyCrashedAgain = deliverator.broadcast(meId, this::updatePlayersFunctor, 
//					new CrashedHolder(eventuallyCrashedPeers)
					crashedPeer
			);
			new DevPrinter(new Throwable()).println(""+eventuallyCrashedAgain);
//			if (eventuallyCrashedAgain.size() > 0) {
//				eventuallyCrashedPeers.addAll(eventuallyCrashedAgain);
//			} else {
//				inCrash = false;
//			}
//		}
//		return eventuallyCrashedPeers;
	}
	private void updatePlayersFunctor(GameService gameService, String crashedPeer) {
		try {
			new DevPrinter(new Throwable()).println();
			gameService.removeService( crashedPeer );
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
