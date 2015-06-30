package bread_and_aces.node;

import javax.inject.Inject;

import bread_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import bread_and_aces.game.Game;
import bread_and_aces.utils.misc.Waiter;

public class NodeAsInitializerClientable extends DefaultNode {

	private Game game;

//	@AssistedInject
	@Inject
	public NodeAsInitializerClientable(
//			@Assisted(value="nodeFactoryIdAsClientable") String dummyId,
			InputHandlerProvider inputHandlerProvider, Game game) {
		super(/*dummyId,*/ inputHandlerProvider);
		this.game = game;
	}
	
	@Override
	public void start(int initialGoal, int initialCoins) {
		Waiter.sleep(game::isStarted, 1);
		super.start(initialGoal, initialCoins);
	}
}
