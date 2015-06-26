package bread_and_aces.game.model.oracle.actions;


public class ActionKeeperFactory {

	public static ActionKeeper build(Action action) {
		return new ActionKeeper(action, 0);
	}
	
	public static ActionKeeper build(Action action, int value) {
		return new ActionKeeper(action, value);
	}
}
