package breads_and_aces.game.model.oracle.actions;

import breads_and_aces.game.model.oracle.GameStates;


public enum ActionSimple implements Action {
	NONE {
		public GameStates getGameState() {
			return GameStates.NULL;
		}
	},
	CHECK {
		public GameStates getGameState() {
			return GameStates.CHECK;
		}
	},
	FOLD {
		public GameStates getGameState() {
			return GameStates.NULL;
		}
	},
	ALLIN {
		public GameStates getGameState() {
			return GameStates.ALLIN;
		}
	};
}
