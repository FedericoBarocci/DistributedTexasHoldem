package bread_and_aces.game.model.oracle.actions;


public class MessageFactory {

	public static Message build(Action action) {
		return new Message(action, 0);
	}
	
	public static Message build(Action action, int value) {
		return new Message(action, value);
	}
	
	public static Message buildForCrash(String player) {
		return new Message(Action.FOLD, 0, player);
	}
}
