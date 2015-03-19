package breads_and_aces.rmi.game.init.servable;

import breads_and_aces.utils.observatory.Observable;
import breads_and_aces.utils.observatory.Observer;

public class NewPlayersObserverAsServable implements Observer<String> {

	private final String myNodeId;
	
	public NewPlayersObserverAsServable(String myNodeId) {
		this.myNodeId = myNodeId;
	}

	@Override
	public void update(Observable<String> o, String arg) {
		notifyNewPlayer(arg);
	}
	
	private void notifyNewPlayer(String nodeId) {
		if (!nodeId.equals(myNodeId))
			System.out.println("Registered new player: "+nodeId);
	}
}
