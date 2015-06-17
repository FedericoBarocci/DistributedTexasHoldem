package bread_and_aces.game.model.state;

import javax.inject.Singleton;

import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;

@Singleton
public class GameState {
	
	private ActionsLogic actionlogic = ActionsLogic.NULL;
	private int minbet = 0;
	
	/*for recovery*/
	public void setGameState(ActionsLogic actionlogic) {
		this.actionlogic = actionlogic;
	}
	
	/*for recovery*/
	public ActionsLogic getGameState() {
		return actionlogic;
	}
	
	/*for recovery*/
	public void nextGameState(ActionKeeper actionKeeper) {
		if (! actionKeeper.getAction().equals(Action.FOLD)) {
			actionlogic = actionlogic.nextState(actionKeeper.getAction());
			minbet = Math.max(minbet, actionKeeper.getValue());
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
