package breads_and_aces.node;

import breads_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import breads_and_aces.node.inputhandler.InputHandler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;


public class DefaultNode implements Node {

	private final String nodeId;
	private final InputHandler inputHandler;
	
	@AssistedInject
	public DefaultNode(@Assisted String thisNodeId, 
//			InputHandlerFactory inputHandlerFactory
			InputHandlerProvider inputHandlerProvider
			) {
		this.nodeId = thisNodeId;
		this.inputHandler = 
//				inputHandlerFactory.create(thisNodeId);
				inputHandlerProvider.init(thisNodeId).get();
//				inputHandler;
	}
	
	public String getId() {
		return nodeId;
	}
	
	public void start() {
		inputHandler.exec();
	}
}
