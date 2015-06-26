package bread_and_aces.services.rmi.utils.crashhandler;

import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.model.controller.DistributedControllerLocalDelegate;
import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import bread_and_aces.services.rmi.utils.communicator.Deliverator;
import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.printer.Printer;

@Singleton
public class CrashHandler {

	private final GameServicesKeeper gameServicesKeeper;
	private final Printer printer;
	private final Deliverator deliverator;
	private final DistributedControllerLocalDelegate distributedControllerLocalDelegate;
	
	
	@Inject
	public CrashHandler(GameServicesKeeper gameServicesKeeper, DistributedControllerLocalDelegate distributedControllerLocalDelegate, Deliverator deliverator, Printer printer) {
		this.gameServicesKeeper = gameServicesKeeper;
		this.distributedControllerLocalDelegate = distributedControllerLocalDelegate;
		this.deliverator = deliverator;
		this.printer = printer;
	}
	
//	public void handleCrash(String crashedPeer) {
//		handleCrashLocallyRemovingFromLocalGameServiceKeeper(crashedPeer);
//		handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, crashedPeer);
//	}

	/**
	 * use this to handle crash in local instance, removing node/player id from gameserviceKeeper (rmi level) and gameplayerskeeper (business logic level)
	 * @param crashedDuringSync
	 */
	public void removeLocallyFromEverywhere(String crashedDuringSync) {
		removeFromLocalGameServiceKeeper(crashedDuringSync);
		distributedControllerLocalDelegate.removePlayerLocally(crashedDuringSync);
	}
	
	private void removeFromLocalGameServiceKeeper(String crashedDuringSync) {
		printer.println(crashedDuringSync+" not responding, removed it.");
		gameServicesKeeper.removeService(crashedDuringSync);
		DevPrinter.println(/*new Throwable(),*/ "removed "+crashedDuringSync+" from GameServiceKeeper");
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
	

	/**
	 * use this to handle crash in other remote peers, so they can handle crash (really, inside call to functor {@link #updateNodesFunctor(GameService, String)}, they use also {@link #removeFromLocalGameServiceKeeper(String)} )
	 * @param meId
	 * @param crashedPeer
	 */
	public List<String> handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(String meId, String crashedPeer) {
			// nodes are unreachable, so handle the crash removing services
//			removeFromLocalGameServiceKeeper(crashedPeer);
			
			// we say to all to update players removing those specified in list
		DevPrinter.println(" "+meId + " -- " + crashedPeer);
		List<String> eventuallyCrashedAgain = deliverator.broadcast(meId, this::updateNodesFunctor,  crashedPeer);
		DevPrinter.println(/*new Throwable(), */""+eventuallyCrashedAgain);
		
		eventuallyCrashedAgain.add(crashedPeer);
			
		return eventuallyCrashedAgain;
	}
	
	private void updateNodesFunctor(GameService gameService, String crashedPeer) {
		try {
			DevPrinter.println("acting on gameservice of "+gameService.getId());
			gameService.removeCrashedPeerService( crashedPeer );
		} catch (RemoteException e) {
			//e.printStackTrace();
			DevPrinter.println(":: " + crashedPeer);
			gameServicesKeeper.removeService(crashedPeer);
		}
	}

}
