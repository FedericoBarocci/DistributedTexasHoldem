package bread_and_aces.services.rmi.utils.communicator;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.services.rmi.game.core.GameService;
import bread_and_aces.utils.DevPrinter;

@LazySingleton
public class Communicator {

	private final Deliverator deliverator;
	private String current;
	
	@Inject
	public Communicator(Deliverator deliverator) {
		this.deliverator = deliverator;
	}

	public void toAll(String meId, CommunicatorFunctorNoArg communicatorFunctor) {
		DevPrinter.println();
		deliverator.broadcast(meId, communicatorFunctor, this);
	}
	
	public void toAll(String meId, CommunicatorFunctorNoArgWithId CommunicatorFunctorNoArgWithId) {
		DevPrinter.println();
		deliverator.broadcast(meId, CommunicatorFunctorNoArgWithId);
	}

	public <T> void toAll(String meId, CommunicatorFunctorWithArg<T> communicatorFunctorWithArg, T arg) {
		deliverator.broadcast(meId, communicatorFunctorWithArg, arg);
	}

	
	@FunctionalInterface
	public interface CommunicatorFunctorNoArg {
		void exec(GameService gameService);
	}
	
	@FunctionalInterface
	public interface CommunicatorFunctorNoArgWithId {
		void exec(GameService gameService, String nodeId);
	}
	
	@FunctionalInterface
	public interface CommunicatorFunctorWithArg<T> {
		void exec(GameService gameService, T arg);
	}
	
	public void setCurrentInterlocutor(String current) {
		this.current = current;
		DevPrinter.println("current is: "+current);
	}
	
	public String getCurrentInterlocutor() {
		return current;
	}
}
