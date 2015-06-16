package bread_and_aces.game.model.oracle.actions;

import bread_and_aces.game.model.state.ActionsLogic;


public enum ActionSimple implements Action {
	NONE {
		public ActionsLogic getGameState() {
			return ActionsLogic.NULL;
		}
		
		public String toString() {
			return "WAIT";
		}
	},
	CHECK {
		public ActionsLogic getGameState() {
			return ActionsLogic.CHECK;
		}
		
		public String toString() {
			return "CHECK";
		}
	},
	FOLD {
		public ActionsLogic getGameState() {
			return ActionsLogic.NULL;
		}
		
		public String toString() {
			return "FOLD";
		}
	},
	ALLIN {
		public ActionsLogic getGameState() {
			return ActionsLogic.ALLIN;
		}
		
		public String toString() {
			return "ALL IN";
		}
	};
}
