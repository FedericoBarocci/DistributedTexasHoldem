package breads_and_aces._di.providers.node.inputhandler.gui;

import javax.inject.Inject;

import breads_and_aces._di.providers.node.inputhandler.InputHandlerProvider;
import breads_and_aces.node.inputhandler.InputHandler;
import breads_and_aces.node.inputhandler.gui.InputHandlerGUI;

public class InputHandlerGUIProvider implements InputHandlerProvider {

	private final InputHandlerGUI inputHandlerGUI;

	@Inject
	public InputHandlerGUIProvider(InputHandlerGUI inputHandlerGUI) {
		this.inputHandlerGUI = inputHandlerGUI;
	}
	
	@Override
	public InputHandlerProvider init(String nodeId) {
		return this;
	}

	@Override
	public InputHandler get() {
		return inputHandlerGUI;
	}

}
