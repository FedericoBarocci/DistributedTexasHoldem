package breads_and_aces.services.rmi.utils.communicator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.RegistrarPlayersKeeper;
import breads_and_aces.services.rmi.game.core.GameService;
import breads_and_aces.services.rmi.game.keeper.GameServicesKeeper;
import breads_and_aces.services.rmi.utils.crashhandler.CrashHandler;

public class Communicator {
	
	private final GameServicesKeeper gameServicesKeeper;
	private final CrashHandler crashHandler;
	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	
	@Inject
	public Communicator(
			GameServicesKeeper gameServicesKeeper,
			RegistrarPlayersKeeper playersKeeper,
			CrashHandler crashHandler
			) {
		this.gameServicesKeeper = gameServicesKeeper;
		this.registrarPlayersKeeper = playersKeeper;
		this.crashHandler = crashHandler;
	}

	/**
	 * @param <T>
	 * @param meId
	 * @param communicatorFunctor
	 * @return crashed ids list
	 */
	public <T> List<String> toAll(String meId, CommunicatorFunctor<T> communicatorFunctor, T arg) {
		List<String> eventuallyCrashedPeers = broadcast(meId, communicatorFunctor, arg);
		return crashHandler.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, this, eventuallyCrashedPeers);
	}
	
	// test
	public <T> List<String> broadcast(String meId, CommunicatorFunctor<T>  communicatorFunctor, T arg) {
	// this below is always updated each times we arrive here, because, eventually "handleRemovingLocally" remove crashed id 
			Set<String> idsFromGameService = gameServicesKeeper.getServices().keySet();
//			ArrayList<String> arrayList = new ArrayList<>(idsFromGameService);
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
					try {
						communicatorFunctor.exec(service, arg);
					} catch (RemoteException e) {
//						e.printStackTrace();
						crashedIds.add(id);
					}
				});
			}
			return crashedIds;
	}
	
	/*public <T> List<String> broadcastOld(String meId, CommunicatorFunctor communicatorFunctor, T arg) {
		// this below is always updated each times we arrive here, because, eventually "handleRemovingLocally" remove crashed id 
		Set<String> idsFromGameService = gameServicesKeeper.getServices().keySet();
//		ArrayList<String> arrayList = new ArrayList<>(idsFromGameService);
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
				try {
					communicatorFunctor.exec(service, arg);
				} catch (RemoteException e) {
//					e.printStackTrace();
					crashedIds.add(id);
				}
			});
		}
		
		return crashedIds;
//		return crashHandler.isHappenedCrash();
	}*/
	
	/*public <T> NextHolder toNext(String meId, CommunicatorFunctor2<T> communicatorFunctor, T arg) {
		// try first time
		String next = registrarPlayersKeeper.getNext(meId).getName();
		Optional<GameService> optService = gameServicesKeeper.getService(next);
//		NextHolder nextHolder = new NextHolder();
		if (optService.isPresent()) {
			try {
				communicatorFunctor.exec(optService.get(), arg);
				return new NextHolder(next);
			} catch (RemoteException e) {
				// TODO node is unreachable, we need to handle...
				return handleNextError(next, meId, communicatorFunctor, arg);
			}
		} else { // never should be here, but...
			return handleNextError(next, meId, communicatorFunctor, arg);
		}
	}
	private <T> NextHolder handleNextError(String next, String meId, CommunicatorFunctor2<T> communicatorFunctor, T arg) {
		crashHandler.removeFromLocalGameServiceKeeper(next);
		return toNext(meId, communicatorFunctor, arg);
	}
	*/
	/*public NextHolder toNextBrutt(String meId, CommunicatorFunctor<Void> communicatorFunctor) {
		boolean crashed = false;
		NextHolder nextHolder = new NextHolder();
		do {
			String next = registrarPlayersKeeper.getNext(meId).getName();
			Optional<GameService> optService = gameServicesKeeper.getService(next);
			if (optService.isPresent()) {
				try {
					communicatorFunctor.exec(optService.get(), null);
					nextHolder.next = next;
				} catch (RemoteException e) {
					// TODO node is unreachable, we need to handle...
					onErrorHandleNextHolder( meId, next, nextHolder, this );
					crashed = true;
				}
			} else { // never should be here, but...
				onErrorHandleNextHolder( meId, next,  nextHolder, this );
				crashed = true;
			}
		} while(crashed);
		return nextHolder;
	}*/
	public <T> NextHolder toNext(String meId, CommunicatorFunctor<T> communicatorFunctor, T arg) {
		// meId as default, if all services will be not reachable iterating on while below
		NextHolder nextHolder = new NextHolder(meId);
		do {
			String next = registrarPlayersKeeper.getNext(meId).getName();
			gameServicesKeeper.getService( next ).ifPresent(c->{
				try {
					communicatorFunctor.exec(c, arg);
					onCommunicationWasFine(next, nextHolder);
				} catch (RemoteException e) {
					onErrorHandleNextHolder( meId, next, nextHolder, this );
				}
			});
		} while(nextHolder.wasHappenedCrash);
		return nextHolder;
	}
	private void onCommunicationWasFine(String next, NextHolder nextHolder) {
		nextHolder.next = next;
		nextHolder.wasHappenedCrash = false;
	}
	private void onErrorHandleNextHolder(String meId, String crashed, NextHolder nextHolder, Communicator communicator) {
		nextHolder.wasHappenedCrash = true;
		nextHolder.crashed.add(crashed);
		crashHandler.handleCrashLocallyRemovingFromLocalGameServiceKeeper(crashed);
		crashHandler.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, communicator, nextHolder.crashed);
	}
	public class NextHolder {
		String next;
		final List<String> crashed = new LinkedList<>();
		boolean wasHappenedCrash;
		public NextHolder() {}
		public NextHolder(String next) {
			this.next = next;
		}
		public NextHolder(String next, List<String> crashed) {
			this.next = next;
			this.crashed.addAll(crashed);
		}
	} 
	
	/**
	 * @param <T>
	 * @param meId
	 * @param communicatorFunctor
	 * @return 
	 * @return crashed id
	 */
	/*public <T> BooleanHolder toOne(String meId, CommunicatorFunctor communicatorFunctor, String targetId, T arg) {
		Optional<GameService> optService = gameServicesKeeper.getService(targetId);
		BooleanHolder booleanHolder = new BooleanHolder();
		optService.ifPresent(service->{
			try {
				communicatorFunctor.exec(service, arg);
				booleanHolder.value = true;
			} catch (RemoteException e) {
				// TODO node is unreachable, we need to handle...
				booleanHolder.value = false;
//				crashHandler.handleCrashRemotely(meId, this, eventuallyCrashedPeers)
			}
		});
		return booleanHolder;
	}*/
	
	/*@FunctionalInterface
	public interface CommunicatorFunctorOld {
		<T> void exec(GameService gameService, T arg) throws RemoteException;
	}*/
	
	@FunctionalInterface
	public interface CommunicatorFunctor<T> {
		void exec(GameService gameService, T arg) throws RemoteException;
	}
	
	/*class BooleanHolder {
		boolean value;
	}*/
}
