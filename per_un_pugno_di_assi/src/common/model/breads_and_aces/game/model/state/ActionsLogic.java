package breads_and_aces.game.model.state;

import java.util.Arrays;
import java.util.List;

import breads_and_aces.game.model.oracle.actions.Action;

public enum ActionsLogic {
	NULL {
		public ActionsLogic nextState(Action action) {
			/*switch(action) {
				case ActionSimple.ALLIN: 	return ALLIN;
				case ActionSimple.CHECK:  	return CHECK;
				case ActionValue.RAISE: 	return RAISE;
				default: 					return NULL;
			}*/
			return action.getGameState();
		}
		
		public List<ActionsLogic> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	CHECK {
		public ActionsLogic nextState(Action action) {
			/*switch(action) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CHECK;
			}*/
			return action.getGameState();
		}
		
		public List<ActionsLogic> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	RAISE {
		public ActionsLogic nextState(Action action) {
			/*switch(action) {
				case ALLIN: 	return ALLIN;
				case CALL:  	return CALL;
				default: 		return RAISE;
			}*/
			return action.getGameState();
		}
		
		public List<ActionsLogic> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	CALL {
		public ActionsLogic nextState(Action action) {
			/*switch(action) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CALL;
			}	*/
			return action.getGameState();
		}
		
		public List<ActionsLogic> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	ALLIN {
		public ActionsLogic nextState(Action action) {
			return ALLIN;
		}
		
		public List<ActionsLogic> getEdges() {
			return Arrays.asList(ALLIN);
		}
	};
	
	abstract ActionsLogic nextState(Action action);
	abstract List<ActionsLogic> getEdges();
}
