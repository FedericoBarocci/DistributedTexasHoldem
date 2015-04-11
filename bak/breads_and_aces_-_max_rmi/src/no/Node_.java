package breads_and_aces.node;

import breads_and_aces.registration.Game;
import breads_and_aces.utils.misc.Waiter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class Node_ {

	private final String nodeId;
	private final InputHandler inputHandler;
	private final Game game;
	
	@AssistedInject
	public Node_(@Assisted String thisNodeId, 
			Game game // dummy presence
			, InputHandler inputHandler
			) {
		this.nodeId = thisNodeId;
		this.game = game;
		this.inputHandler = inputHandler;
	}
	
	public String getId() {
		return nodeId;
	}
	
	public void start() {
		// this while is necessary if node acts as clientable
		Waiter.sleep(game::isStarted, 1);
		inputHandler.exec();
	}
}
