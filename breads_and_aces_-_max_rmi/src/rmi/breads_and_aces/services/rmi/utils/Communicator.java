package breads_and_aces.services.rmi.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;

import breads_and_aces.services.rmi.GameServicesRegistry;
import breads_and_aces.services.rmi.game.GameService;

public class Communicator {
	
	private final GameServicesRegistry gameServicesRegistry;
	private final CrashHandler crashHandler;
	
	public Communicator(
			GameServicesRegistry gameServicesRegistry,
			CrashHandler crashHandler
			) {
		this.gameServicesRegistry = gameServicesRegistry;
		this.crashHandler = crashHandler;
	}
	
	public void communicate(CommunicatorFunctor communicatorFunctor) {
		do {
			iterateOnNodes(communicatorFunctor);
		} while (crashHandler.isHappenedCrash());
		crashHandler.noMoreCrash();
	}
	@FunctionalInterface
	public interface CommunicatorFunctor {
		void exec(GameService gameService) throws RemoteException;
	}
	
	private boolean iterateOnNodes(CommunicatorFunctor communicatorFunctor) {
		ListIterator<String> idsListIterator = new ArrayList<>(gameServicesRegistry.getServices().keySet()).listIterator();
		
		while(idsListIterator.hasNext()) {
			String id = idsListIterator.next();
			Optional<GameService> service = gameServicesRegistry.getService(id);
			service.ifPresent(c->{
				try {
					communicatorFunctor.exec(c);
				} catch (RemoteException e) {
					// the node is unreachable, so handle
					crashHandler.handle(id);
				}
			});			
		}
		return crashHandler.isHappenedCrash();
	}
}
