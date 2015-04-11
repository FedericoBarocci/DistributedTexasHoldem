package breads_and_aces.registration.initializers.servable;

import breads_and_aces.utils.observatory.Observable;
import breads_and_aces.utils.observatory.Observer;

public class NewPlayersObserverAsServable implements Observer<String> {

	private final String myNodeId;
	
	public NewPlayersObserverAsServable(String myNodeId) {
		this.myNodeId = myNodeId;
	}

	@Override
	public void update(Observable<String> o, String playerId) {
		if (!playerId.equals(myNodeId))
			System.out.println("Registered new player: "+playerId);
	}
}
