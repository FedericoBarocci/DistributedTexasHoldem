package bread_and_aces.services.rmi.utils.communicator;

import java.util.List;

import javax.inject.Inject;

import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.services.rmi.utils.crashhandler.CrashHandler;
import bread_and_aces.utils.DevPrinter;

public class Communicator {
	
	private final CrashHandler crashHandler;
//	private final RegistrarPlayersKeeper registrarPlayersKeeper;
	private final Deliverator deliverator;
	private String current;
	
	@Inject
	public Communicator(
//			RegistrarPlayersKeeper playersKeeper,
			Deliverator deliverator,
			CrashHandler crashHandler
			) {
//		this.registrarPlayersKeeper = playersKeeper;
		this.deliverator = deliverator;
		this.crashHandler = crashHandler;
	}

	/**
	 * @param <T>
	 * @param meId
	 * @param communicatorFunctor
	 * @return crashed ids list
	 */
//	public <T> List<String> toAll(String meId, CommunicatorFunctor<T> communicatorFunctor, T arg) {
//		List<String> eventuallyCrashedPeers = broadcast(meId, communicatorFunctor, arg);
//		return crashHandler.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, this, eventuallyCrashedPeers);
//	}
//	public List<String> toAll(String meId, CommunicatorFunctorNoArg communicatorFunctor) {
	public void toAll(String meId, CommunicatorFunctorNoArg communicatorFunctor) {
		DevPrinter.println(/*new Throwable()*/);
		deliverator.broadcast(meId, communicatorFunctor, this);
	}
	public void toAll(String meId, CommunicatorFunctorNoArgWithId CommunicatorFunctorNoArgWithId) {
		DevPrinter.println(/*new Throwable()*/);
		deliverator.broadcast(meId, CommunicatorFunctorNoArgWithId);
	}
//	public <T> List<String> toAll(String meId, CommunicatorFunctorWithArg<T> communicatorFunctor, T arg) {
	public <T> void toAll(String meId, CommunicatorFunctorWithArg<T> communicatorFunctor, T arg) {
//		System.out.println("toAll:46");
//		List<String> eventuallyCrashedPeers = 
				deliverator.broadcast(meId, communicatorFunctor, arg);
//		return crashHandler.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, eventuallyCrashedPeers);
	}
	public List<String> broadcastRemoveServiceKeeper(String meId, String crashedPeer) {
		return crashHandler.handleCrashRemotelySayingToOtherNodesToRemoveFromTheirGameServiceKeeper(meId, crashedPeer);
	}
	
	// pass token zone, never used
	/*public <T> NextHolder toNext(String meId, CommunicatorFunctorWithArg<T> communicatorFunctor, T arg) {
		// meId as default, if all services will be not reachable iterating on while below
		NextHolder nextHolder = new NextHolder(meId);
		do {
			String next = registrarPlayersKeeper.getNext(meId).getName();
//			gameServicesKeeper.getService( next ).ifPresent(c->{
//				try {
//					communicatorFunctor.exec(c, arg);
//					onCommunicationWasFine(next, nextHolder);
//				} catch (RemoteException e) {
//					onErrorHandleNextHolder( meId, next, nextHolder, this );
//				}
//			});
			if (deliverator.unicast(meId, next, communicatorFunctor, arg)) {
				onCommunicationWasFine(next, nextHolder);
			} else {
				onErrorHandleNextHolder( meId, next, nextHolder, this );
			}
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
	}*/
	
	@FunctionalInterface
	public interface CommunicatorFunctorNoArg {
		void exec(GameService gameService) /*throws RemoteException*/;
	}
	
	@FunctionalInterface
	public interface CommunicatorFunctorNoArgWithId {
		void exec(GameService gameService, String nodeId) /*throws RemoteException*/;
	}
	
	@FunctionalInterface
	public interface CommunicatorFunctorWithArg<T> {
		void exec(GameService gameService, T arg) /*throws RemoteException*/;
	}
	
	public void setCurrentInterlocutor(String current) {
		this.current = current;
		DevPrinter.println(/*new Throwable(),*/ "current is: "+current);
	}
	
	public String getCurrentInterlocutor() {
		return current;
	}
}
