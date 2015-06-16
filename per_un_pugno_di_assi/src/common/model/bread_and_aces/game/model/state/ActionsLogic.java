package bread_and_aces.game.model.state;

import bread_and_aces.game.model.oracle.actions.Action;

public enum ActionsLogic {
	NULL {
		public ActionsLogic nextState(Action action) {
			return action.getGameState();
		}

		@Override
		public ActionsLogic getMaxBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMinBetState() {
			return CHECK;
		}

		@Override
		public ActionsLogic getBetState() {
			return RAISE;
		}

		@Override
		public Action getAction() {
			return Action.CHECK;
		}
	},
	CHECK {
		public ActionsLogic nextState(Action action) {
			return action.getGameState();
		}

		@Override
		public ActionsLogic getMaxBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMinBetState() {
			return CHECK;
		}

		@Override
		public ActionsLogic getBetState() {
			return RAISE;
		}

		@Override
		public Action getAction() {
			return Action.CHECK;
		}
	},
	RAISE {
		public ActionsLogic nextState(Action action) {
			return action.getGameState();
		}

		@Override
		public ActionsLogic getMaxBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMinBetState() {
			return CALL;
		}

		@Override
		public ActionsLogic getBetState() {
			return RAISE;
		}

		@Override
		public Action getAction() {
			return Action.RAISE;
		}
	},
	CALL {
		public ActionsLogic nextState(Action action) {
			return action.getGameState();
		}

		@Override
		public ActionsLogic getMaxBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMinBetState() {
			return CALL;
		}

		@Override
		public ActionsLogic getBetState() {
			return RAISE;
		}

		@Override
		public Action getAction() {
			return Action.CALL;
		}
	},
	ALLIN {
		public ActionsLogic nextState(Action action) {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMaxBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getMinBetState() {
			return ALLIN;
		}

		@Override
		public ActionsLogic getBetState() {
			return ALLIN;
		}

		@Override
		public Action getAction() {
			return Action.ALLIN;
		}
	};
	
	abstract public ActionsLogic nextState(Action action);
	abstract public ActionsLogic getMaxBetState();
	abstract public ActionsLogic getMinBetState();
	abstract public ActionsLogic getBetState();
	abstract public Action getAction();
}
