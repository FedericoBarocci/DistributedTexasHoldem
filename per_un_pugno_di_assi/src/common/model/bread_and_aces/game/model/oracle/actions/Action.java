package bread_and_aces.game.model.oracle.actions;

import bread_and_aces.game.model.state.ActionsLogic;

public enum Action {
//	abstract public ActionsLogic getGameState();
//	abstract public String toString();
//	abstract public void setValue(int value);
//	abstract public int getValue();
	
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
	},
	RAISE {
		public ActionsLogic getGameState() {
			return ActionsLogic.RAISE;
		}
		
		public String toString() {
			return "RAISE";
		}
	},
	CALL {
		public ActionsLogic getGameState() {
			return ActionsLogic.CALL;
		}
		
		public String toString() {
			return "CALL";
		}
	};
	
	abstract public ActionsLogic getGameState();
	abstract public String toString();
}
