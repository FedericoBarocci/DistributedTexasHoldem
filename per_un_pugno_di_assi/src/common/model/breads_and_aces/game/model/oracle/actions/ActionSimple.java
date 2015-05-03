package breads_and_aces.game.model.oracle.actions;

import breads_and_aces.game.model.oracle.GameStates;


public enum ActionSimple implements Action {
	NONE {
		public GameStates getGameState() {
			return GameStates.NULL;
		}
		
		public String toString() {
			return "WAIT";
		}
	},
	CHECK {
		public GameStates getGameState() {
			return GameStates.CHECK;
		}
		
		public String toString() {
			return "CHECK";
		}
	},
	FOLD {
		public GameStates getGameState() {
			return GameStates.NULL;
		}
		
		public String toString() {
			return "FOLD";
		}
	},
	ALLIN {
		public GameStates getGameState() {
			return GameStates.ALLIN;
		}
		
		public String toString() {
			return "ALL IN";
		}
	};
}
