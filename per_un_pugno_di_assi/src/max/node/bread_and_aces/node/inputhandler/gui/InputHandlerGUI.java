package bread_and_aces.node.inputhandler.gui;

import javax.inject.Inject;

import bread_and_aces.game.GameInitializer;
import bread_and_aces.node.inputhandler.InputHandler;

public class InputHandlerGUI implements InputHandler {
	
	private GameInitializer gameViewInitializer;

	@Inject
	public InputHandlerGUI(GameInitializer gameViewInitializer) {
		this.gameViewInitializer = gameViewInitializer;
	}

	public void exec(int initialGoal, int initialCoins) {
		gameViewInitializer.start(initialGoal, initialCoins);
	}
}
