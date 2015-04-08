package breads_and_aces.node;

import breads_and_aces.dummy.InputHandler;
import breads_and_aces.game.Game;
import breads_and_aces.utils.misc.Waiter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeAsInitializerClientable extends DefaultNode {

	private Game game;

	@AssistedInject
	public NodeAsInitializerClientable(@Assisted String thisNodeId,
//			InputHandlerFactory inputHandlerFactory,
			InputHandler inputHandler, 
			Game game) {
		super(thisNodeId, inputHandler);
		this.game = game;
	}
	
	@Override
	public void start() {
		Waiter.sleep(game::isStarted, 1);
		super.start();
	}
}
