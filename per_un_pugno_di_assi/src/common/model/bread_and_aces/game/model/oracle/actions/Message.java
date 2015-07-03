package bread_and_aces.game.model.oracle.actions;

import java.io.Serializable;


public class Message implements Serializable {

	private static final long serialVersionUID = -210328786584017591L;
	
	private Action action;
	private int value;
	private String player;
	private boolean crashed;
	
	public Message() {	}
	
	public Message(Action action, int value, String player) {
		this.action = action;
		this.value = value;
		this.player = player;
		this.crashed = true;
	}
	
	public Message(Action action, int value) {
		this.action = action;
		this.value = value;
		this.player = "";
		this.crashed = false;
	}
	
	public Action getAction() {
		return action;
	}
	
	public int getValue() {
		return value;
	}

	public String getCrashed() {
		return player;
	}
	
	public boolean hasCrashed() {
		return crashed;
	}
	
	@Override
	public String toString() {
		if (crashed) {
			return "crashed";
		}
		
		String result = action.toString();
		
		if (value > 0) {
			result += " " + value;
		}
		
		return result;
	}
}
