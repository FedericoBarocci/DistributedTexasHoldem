package breads_and_aces.services.rmi.utils.crashhandler;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator;
import breads_and_aces.utils.printer.Printer;

@Singleton
public class CrashHandler {

//	private final KeepersUtilDelegate registriesUtils;
	private final GameServicesKeeper gameServicesKeeper;
	
	private final Printer printer;
	
	private boolean isExistingCrash = false;
	
//	private final Set<String> crashed = new HashSet<>();
	
	@Inject
	public CrashHandler(/*KeepersUtilDelegate keepersUtilDelegate,*/ GameServicesKeeper gameServicesKeeper, Printer printer) {
//		this.registriesUtils = keepersUtilDelegate;
		this.gameServicesKeeper = gameServicesKeeper;
		this.printer = printer;
	}
	
	public void noMoreCrash() {
		setHappenedCrash(false);
//		crashed.clear();
	}
	private void setHappenedCrash(boolean isExistingCrashed) {
		this.isExistingCrash = isExistingCrashed;
	}
	public boolean isHappenedCrash() {
		return isExistingCrash;
	}

	public void handleCrashLocallyRemovingFromLocalGameServiceKeeper(String id) {
		setHappenedCrash(true);
//		registriesUtils.removePlayerGameService(id);
		gameServicesKeeper.removeService(id);
		printer.println(id+" not responding, removed it.");
//		crashed.remove(id);
	}
	public void removeFromLocalGameServiceKeeper(List<String> crashedDuringSync) {
		setHappenedCrash(true);
		ListIterator<String> listIterator = crashedDuringSync.listIterator();
		while (listIterator.hasNext()) {
			String next = listIterator.next();
//			registriesUtils.removePlayerGameService(next);
			gameServicesKeeper.removeService(next);
			listIterator.remove();
		}
	}
	
	public List<String> handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(String meId, Communicator communicator, List<String> eventuallyCrashedPeers) {
		boolean inCrash = false;
		if (eventuallyCrashedPeers.size() > 0)
			inCrash = true;
		while (inCrash) {
			// nodes are unreachable, so handle the crash removing services
			removeFromLocalGameServiceKeeper(eventuallyCrashedPeers);
			
			// we say to all to update players removing those specified in list
			List<String> eventuallyCrashedAgain = communicator.broadcast(meId, this::updatePlayersFunctor, new CrashedHolder(eventuallyCrashedPeers));
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
			gameService.removePlayersAndService( crashedHolderPeers.getCrashed() );
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

//	public void addCrashed(String id) {
//		crashed.add(id);
//	}
	
}
