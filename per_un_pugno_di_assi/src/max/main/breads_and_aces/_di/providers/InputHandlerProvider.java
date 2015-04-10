package breads_and_aces._di.providers;

import javax.inject.Provider;

import breads_and_aces.node.inputhandler.InputHandler;

public interface InputHandlerProvider extends Provider<InputHandler> {
	InputHandlerProvider init(String nodeId);
	InputHandler get();
	
}
