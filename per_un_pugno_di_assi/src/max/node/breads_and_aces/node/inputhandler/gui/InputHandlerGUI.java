package breads_and_aces.node.inputhandler.gui;

import javax.inject.Inject;

import breads_and_aces.game.GameInitializerReal;
import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	private GameInitializerReal gameViewInitializer;

	@Inject
	public InputHandlerGUI(GameInitializerReal gameViewInitializer) {
		this.gameViewInitializer = gameViewInitializer;
	}

	public void exec() {
		gameViewInitializer.start();
	}
}
