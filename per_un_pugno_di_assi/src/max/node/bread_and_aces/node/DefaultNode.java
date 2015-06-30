package bread_and_aces.node;

import javax.inject.Inject;

import bread_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import bread_and_aces.node.inputhandler.InputHandler;

public class DefaultNode implements Node {

//	private final String nodeId;
	private final InputHandler inputHandler;

//	@AssistedInject
	@Inject
	public DefaultNode(
//			@Assisted(value="nodeFactoryIdAsServable") String dummyId, 
			InputHandlerProvider inputHandlerProvider) {
//		this.nodeId = thisNodeId;
		this.inputHandler = inputHandlerProvider.init(/*thisNodeId*/).get();
	}
	
	public void start(int initialGoal, int initialCoins) {
		inputHandler.exec(initialGoal, initialCoins);
	}
}
