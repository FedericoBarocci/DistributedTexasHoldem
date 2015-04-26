package breads_and_aces.game.model.oracle;

import java.util.Arrays;
import java.util.List;

import breads_and_aces.game.core.Action;

public enum GameStates {
	NULL {
		public GameStates nextState(Action action) {
			switch(action) {
				case ALLIN: 	return ALLIN;
				case CHECK:  	return CHECK;
				case RAISE: 	return RAISE;
				default: 		return NULL;
			}
		}
		
		public List<GameStates> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	CHECK {
		public GameStates nextState(Action action) {
			switch(action) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CHECK;
			}
		}
		
		public List<GameStates> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	RAISE {
		public GameStates nextState(Action action) {
			switch(action) {
				case ALLIN: 	return ALLIN;
				case CALL:  	return CALL;
				default: 		return RAISE;
			}
		}
		
		public List<GameStates> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	CALL {
		public GameStates nextState(Action action) {
			switch(action) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CALL;
			}	
		}
		
		public List<GameStates> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	ALLIN {
		public GameStates nextState(Action action) {
			return ALLIN;
		}
		
		public List<GameStates> getEdges() {
			return Arrays.asList(ALLIN);
		}
	};
	
	abstract GameStates nextState(Action action);
	abstract List<GameStates> getEdges();
}
