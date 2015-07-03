package bread_and_aces.game.model.state;

import javax.inject.Singleton;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.Message;

@Singleton
public class GameState {
	
	private ActionsLogic actionlogic = ActionsLogic.NULL;
	private int minbet = 0;
	
	/*for recovery*/
	public ActionsLogic getGameState() {
		return actionlogic;
	}
	
	/*for recovery*/
	public void nextGameState(Message message) {
		if (! message.getAction().equals(Action.FOLD)) {
			actionlogic = actionlogic.nextState(message.getAction());
			minbet = Math.max(minbet, message.getValue());
		}
	}
	
	public void reset() {
		actionlogic = ActionsLogic.NULL;
		minbet = 0;
	}
	
	public int getMinBet() {
		return minbet;
	}
	
//	public void setMinBet(int minbet) {
//		this.minbet = minbet;
//	}
}
