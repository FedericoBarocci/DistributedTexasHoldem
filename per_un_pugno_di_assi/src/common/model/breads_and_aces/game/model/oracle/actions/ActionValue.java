package breads_and_aces.game.model.oracle.actions;

import breads_and_aces.game.model.state.ActionsLogic;

public enum ActionValue implements Action {
	RAISE {
		private int value;
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public ActionsLogic getGameState() {
			return ActionsLogic.RAISE;
		}
		
		public String toString() {
			return "RAISE " + value;
		}
	},
	CALL {
		private int value;
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}

		public ActionsLogic getGameState() {
			return ActionsLogic.CALL;
		}
		
		public String toString() {
			return "CALL " + value;
		}
	};
	
	abstract public void setValue(int value);
	abstract public int getValue();
}
