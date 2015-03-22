package breads_and_aces.game.init.clientable.observer;

import breads_and_aces.utils.observatory.Observable;
import breads_and_aces.utils.observatory.Observer;

public class NewPlayersObserverAsClientable implements Observer<String> {

	
	@Override
	public void update(Observable<String> o, String newplayerId) {
		notifyNewPlayers(newplayerId);
	}
	
	private void notifyNewPlayers(String allplayersIds) {
			System.out.println("Synchronized players from initializer: "+allplayersIds);
	}
}
