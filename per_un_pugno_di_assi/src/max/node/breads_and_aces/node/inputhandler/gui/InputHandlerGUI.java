package breads_and_aces.node.inputhandler.gui;

import javax.inject.Inject;

import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	
	private GameViewInitializerInstancer gameViewInitializerInstancer;

	@Inject
	public InputHandlerGUI(GameViewInitializerInstancer gameViewInitializerInstancer) {
		this.gameViewInitializerInstancer = gameViewInitializerInstancer;
	}

	public void exec() {
		gameViewInitializerInstancer.create().start();
	}
}
