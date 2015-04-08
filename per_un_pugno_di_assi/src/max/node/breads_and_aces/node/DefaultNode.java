package breads_and_aces.node;

import breads_and_aces.dummy.InputHandler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;


public class DefaultNode implements Node {

	private final String nodeId;
	private final InputHandler inputHandler;
	
	@AssistedInject
	public DefaultNode(@Assisted String thisNodeId, 
//			InputHandlerFactory inputHandlerFactory
			InputHandler inputHandler
			) {
		this.nodeId = thisNodeId;
		this.inputHandler = 
//				inputHandlerFactory.create(thisNodeId);
				inputHandler;
	}
	
	public String getId() {
		return nodeId;
	}
	
	public void start() {
		inputHandler.exec();
	}
}
