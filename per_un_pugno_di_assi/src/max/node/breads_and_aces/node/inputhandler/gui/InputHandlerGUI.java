package breads_and_aces.node.inputhandler.gui;

import javax.inject.Inject;

import breads_and_aces.game.GameInitializer;
import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	private GameInitializer gameViewInitializer;

	@Inject
	public InputHandlerGUI(GameInitializer gameViewInitializer) {
		this.gameViewInitializer = gameViewInitializer;
	}

	public void exec() {
		gameViewInitializer.start();
	}
}
