package bread_and_aces._di.providers.node.inputhandler;

import javax.inject.Provider;

import bread_and_aces.node.inputhandler.InputHandler;

public interface InputHandlerProvider extends Provider<InputHandler> {
	InputHandlerProvider init(/*String nodeId*/);
	InputHandler get();
}
