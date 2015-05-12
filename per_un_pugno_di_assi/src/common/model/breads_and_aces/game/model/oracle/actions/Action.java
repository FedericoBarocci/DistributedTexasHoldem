package breads_and_aces.game.model.oracle.actions;

import breads_and_aces.game.model.state.ActionsLogic;

public interface Action {
	abstract public ActionsLogic getGameState();
	abstract public String toString();
	abstract public void setValue(int value);
	abstract public int getValue();
}
