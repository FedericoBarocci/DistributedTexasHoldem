package breads_and_aces.game.model.oracle.actions;

import java.io.Serializable;

public class ActionKeeper implements Serializable {

	private static final long serialVersionUID = 718055758031363514L;

	private final Action action;
	private final int value;
	
	ActionKeeper(Action action, int value) {
		this.action = action;
		this.value = value;
	}
	
	public Action getAction() {
		return action;
	}
	
	public int getValue() {
		return value;
	}
}
