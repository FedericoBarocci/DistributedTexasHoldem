package breads_and_aces.services.rmi.utils.communicator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.crashhandler.CrashHandler;

public class Communicator {
	
	private final GameServicesKeeper gameServicesKeeper;
	private final CrashHandler crashHandler;
	
	@Inject
	public Communicator(
			GameServicesKeeper gameServicesKeeper,
			CrashHandler crashHandler
			) {
		this.gameServicesKeeper = gameServicesKeeper;
		this.crashHandler = crashHandler;
	}
	
	public void toAll(String meId, CommunicatorFunctor communicatorFunctor) {
		broadcast(meId, communicatorFunctor);
	}
	
	private void broadcast(String meId, CommunicatorFunctor communicatorFunctor) {
		// this below is always updated each times we arrive here, because, eventually "handleRemovingLocally" remove crashed id 
		Set<String> idsFromGameService = gameServicesKeeper.getServices().keySet();
		ArrayList<String> arrayList = new ArrayList<>(idsFromGameService);
		ListIterator<String> idsListIterator = arrayList.listIterator();
		
		
		// we use listiterator because we can not change a collection during its iteration, instead listiterator can do 
		while(idsListIterator.hasNext()) {
			String id = idsListIterator.next();
			// skipping me
			if (meId.equals(id))
				continue;
			
			Optional<GameService> optService = gameServicesKeeper.getService(id);
			optService.ifPresent(service->{
				try {
					communicatorFunctor.exec(service/*, arg1, arg2*/);
				} catch (RemoteException e) {
					e.printStackTrace();
					// TODO the node is unreachable, so handle the crash removing player/node/service
					crashHandler.handleRemovingLocally(id);
					idsListIterator.remove(); // needed ?
				}
			});
		}
//		return crashHandler.isHappenedCrash();
	}
	
	public void toOne(CommunicatorFunctor communicatorFunctor, String targetId) {
		Optional<GameService> optService = gameServicesKeeper.getService(targetId);
		optService.ifPresent(service->{
			try {
				communicatorFunctor.exec(service);
			} catch (RemoteException e) {
				// TODO node is unreachable, we need to handle...
			}
		});
	}
	
	@FunctionalInterface
	public interface CommunicatorFunctor {
		void exec(GameService gameService) throws RemoteException;
	}
//	@FunctionalInterface
//	public interface CommunicatorFunctor1Argument {
//		<T> void exec(GameService gameService, T arg) throws RemoteException;
//	}
//	@FunctionalInterface
//	public interface CommunicatorFunctor2Argument {
//		<T1, T2> void exec(GameService gameService, T1 arg1, T2 arg2) throws RemoteException;
//	}
}
