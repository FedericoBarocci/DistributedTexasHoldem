package bread_and_aces.registration.initializers.clientable;

import bread_and_aces.utils.DevPrinter;
import bread_and_aces.utils.observatory.Observer;

public class NewPlayersObserverAsClientable implements Observer<String> {
	
	@Override
	public void update(/*Observable<String> o,*/ String newplayerId) {
		notifyNewPlayers(newplayerId);
	}
	
	private void notifyNewPlayers(String allplayersIds) {
			DevPrinter.println("Synchronized players from initializer: " + allplayersIds);
	}
}
