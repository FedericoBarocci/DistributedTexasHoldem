package breads_and_aces.node.inputhandler.gui;

import it.unibo.cs.sd.poker.gui.view.GameViewInitializerReal;

import javax.inject.Inject;

import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	private GameViewInitializerReal gameViewInitializer;

	@Inject
	public InputHandlerGUI(GameViewInitializerReal gameViewInitializer) {
		this.gameViewInitializer = gameViewInitializer;
	}

	public void exec() {
		gameViewInitializer.start();
	}
}
