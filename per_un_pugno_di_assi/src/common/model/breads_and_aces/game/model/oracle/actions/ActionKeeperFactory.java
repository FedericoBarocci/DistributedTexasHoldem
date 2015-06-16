package breads_and_aces.game.model.oracle.actions;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;

public class ActionKeeperFactory {

	public static ActionKeeper get(Action action) {
		return new ActionKeeper(action, 0);
	}
	
	public static ActionKeeper get(Action action, int value) {
		return new ActionKeeper(action, value);
	}
}
