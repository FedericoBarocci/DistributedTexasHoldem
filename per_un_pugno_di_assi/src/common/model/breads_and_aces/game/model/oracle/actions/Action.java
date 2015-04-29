package breads_and_aces.game.model.oracle.actions;

import breads_and_aces.game.model.oracle.GameStates;

public interface Action {
	abstract public GameStates getGameState();
}
