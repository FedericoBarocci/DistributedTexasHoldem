package breads_and_aces.node;

import breads_and_aces._di.providers.InputHandlerProvider;
import breads_and_aces.game.Game;
import breads_and_aces.utils.misc.Waiter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class NodeAsInitializerClientable extends DefaultNode {

	private Game game;

	@AssistedInject
	public NodeAsInitializerClientable(@Assisted String thisNodeId,
//			InputHandlerFactory inputHandlerFactory,
//			GUIInputHandler inputHandler,
			InputHandlerProvider inputHandlerProvider,
			Game game) {
		super(thisNodeId, inputHandlerProvider);
		this.game = game;
	}
	
	@Override
	public void start() {
		Waiter.sleep(game::isStarted, 1);
		super.start();
	}
}
