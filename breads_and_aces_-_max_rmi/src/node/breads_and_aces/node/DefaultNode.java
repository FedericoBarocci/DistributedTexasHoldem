package breads_and_aces.node;

import breads_and_aces.dummy.InputHandler;
import breads_and_aces.dummy.InputHandlerFactory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;


public class DefaultNode implements Node {

	private final String nodeId;
	private final InputHandler inputHandler;
	
	@AssistedInject
	public DefaultNode(@Assisted String thisNodeId, 
			InputHandlerFactory inputHandlerFactory) {
		this.nodeId = thisNodeId;
		this.inputHandler = inputHandlerFactory.create(thisNodeId);
	}
	
	public String getId() {
		return nodeId;
	}
	
	public void start() {
		inputHandler.exec();
	}
}
