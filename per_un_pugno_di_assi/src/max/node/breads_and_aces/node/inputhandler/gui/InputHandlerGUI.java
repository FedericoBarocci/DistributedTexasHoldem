package breads_and_aces.node.inputhandler.gui;

import it.unibo.cs.sd.poker.gui.view.GameViewInitializerReal;

import javax.inject.Inject;

import breads_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	
	private GameViewInitializerReal/*Instancer*/ gameViewInitializer/*Instancer*/;

	@Inject
	public InputHandlerGUI(GameViewInitializerReal/*Instancer*/ gameViewInitializer/*Instancer*/) {
		this.gameViewInitializer/*Instancer*/ = gameViewInitializer/*Instancer*/;
	}

	public void exec() {
//		gameViewInitializerInstancer.create().start();
		gameViewInitializer.start();
	}
}
