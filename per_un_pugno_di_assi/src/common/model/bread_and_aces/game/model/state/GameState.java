package bread_and_aces.game.model.state;

import java.util.List;

import javax.inject.Singleton;

import bread_and_aces.game.model.oracle.actions.Action;

@Singleton
public class GameState {
	
	private ActionsLogic actionlogic = ActionsLogic.NULL;
	
	/*for recovery*/
	public void setGameState(ActionsLogic actionlogic) {
		this.actionlogic = actionlogic;
	}
	
	/*for recovery*/
	public ActionsLogic getGameState() {
		return actionlogic;
	}
	
	/*for recovery*/
	public void nextGameState(Action m) {
		actionlogic = actionlogic.nextState(m);
	}
	
	/* for gui: possible user input */
	public List<ActionsLogic> getLegalActions() {
		return actionlogic.getEdges();
	}
}
