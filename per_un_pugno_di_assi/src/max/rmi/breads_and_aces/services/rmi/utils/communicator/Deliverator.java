package breads_and_aces.services.rmi.utils.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import bread_and_aces.utils.DevPrinter;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.communicator.Communicator.CommunicatorFunctorNoArg;
import breads_and_aces.services.rmi.utils.communicator.Communicator.CommunicatorFunctorWithArg;

import com.google.inject.Inject;

public class Deliverator {
	
	private final GameServicesKeeper gameServicesKeeper;

	@Inject
	public Deliverator(GameServicesKeeper gameServicesKeeper) {
		this.gameServicesKeeper = gameServicesKeeper;
	}

//	public List<String> broadcast(String meId, CommunicatorFunctorNoArg communicatorFunctor, Communicator communicator) {
	public void broadcast(String meId, CommunicatorFunctorNoArg communicatorFunctor, Communicator communicator) {
		// this below is always updated each times we arrive here, because, eventually "handleRemovingLocally" remove crashed id
		Set<String> idsFromGameService = gameServicesKeeper.getServices().keySet();
		ListIterator<String> idsFromGameServiceListIterator = new ArrayList<>(idsFromGameService).listIterator();
	
//		List<String> crashedIds = new ArrayList<>();
		// we use listiterator because we can not change a collection during its iteration, instead listiterator can do
		while (idsFromGameServiceListIterator.hasNext()) {
			String id = idsFromGameServiceListIterator.next();
			// skipping me
			if (meId.equals(id))
				continue;
	
			communicator.setCurrent(id);
			
			Optional<GameService> optService = gameServicesKeeper.getService(id);
			optService.ifPresent(service -> {
//				try {
					communicatorFunctor.exec(service);
					
//				} catch (RemoteException e) {
//					System.out.println("crashed "+id);
//					crashedIds.add(id);
//				}
			});
		}
//		System.out.println("Crashed during broadcast: " + crashedIds.size());
//		return crashedIds;
		
	}

	public <T> List<String> broadcast(String meId, CommunicatorFunctorWithArg<T> communicatorFunctor, T arg) {
		// this below is always updated each times we arrive here, because, eventually "handleRemovingLocally" remove crashed id 
		Set<String> idsFromGameService = gameServicesKeeper.getServices().keySet();
		ListIterator<String> idsFromGameServiceListIterator = new ArrayList<>(idsFromGameService).listIterator();
		
		List<String> crashedIds = new ArrayList<>();
		// we use listiterator because we can not change a collection during its iteration, instead listiterator can do 
		while(idsFromGameServiceListIterator.hasNext()) {
			String id = idsFromGameServiceListIterator.next();
			// skipping me
			if (meId.equals(id))
				continue;
			
			Optional<GameService> optService = gameServicesKeeper.getService(id);
			optService.ifPresent(service->{
//				try {
					communicatorFunctor.exec(service, arg);
//				} catch (RemoteException e) {
//					crashedIds.add(id);
//				}
			});
		}
		new DevPrinter(new Throwable()).println("Crashed during broadcast: " + crashedIds.size());
		return crashedIds;
	}
	
	public <T> boolean unicast(String meId, String target, CommunicatorFunctorWithArg<T> communicatorFunctor, T arg) {
		Optional<GameService> service = gameServicesKeeper.getService( target );
		if (service.isPresent()) {
//			try {
				communicatorFunctor.exec(service.get(), arg);
				return true;
//			} catch (RemoteException e) {
//				return false;
//			}
		} else
			return false;
	}

}
