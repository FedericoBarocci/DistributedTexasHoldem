package bread_and_aces.game.model.state;

import javax.inject.Singleton;

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
	public void nextGameState(ActionKeeper m) {
		actionlogic = actionlogic.nextState(m.getAction());
		//preventing fold value (=0)
		minbet = Math.max(minbet, m.getValue());
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
