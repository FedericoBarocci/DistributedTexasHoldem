package breads_and_aces._di.providers;

import javax.inject.Inject;

import breads_and_aces.node.inputhandler.InputHandler;
import breads_and_aces.node.inputhandler.shell.InputHandlerShell;
import breads_and_aces.node.inputhandler.shell.InputHandlerShellFactory;

public class InputHandlerShellProvider implements InputHandlerProvider {

	private InputHandlerShellFactory inputHandlerShellFactory;
	private InputHandlerShell inputHandlerShell;

	@Inject
	public InputHandlerShellProvider(InputHandlerShellFactory inputHandlerShellFactory) {
		this.inputHandlerShellFactory = inputHandlerShellFactory;
	}
	
	@Override
	public InputHandlerProvider init(String nodeId) {
		inputHandlerShell = inputHandlerShellFactory.create(nodeId);
		return this;
	}

	@Override
	public InputHandler get() {
		return inputHandlerShell;
	}

}
