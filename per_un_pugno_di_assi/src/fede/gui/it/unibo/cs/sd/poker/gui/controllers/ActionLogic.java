package it.unibo.cs.sd.poker.gui.controllers;

import it.unibo.cs.sd.poker.game.core.Action;

import java.util.Arrays;
import java.util.List;

public enum ActionLogic {
	NULL {
		public ActionLogic nextState(Action m) {
			switch(m) {
				case ALLIN: 	return ALLIN;
				case CHECK:  	return CHECK;
				case RAISE: 	return RAISE;
				default: 		return NULL;
			}
		}
		
		public List<ActionLogic> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	CHECK {
		public ActionLogic nextState(Action m) {
			switch(m) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CHECK;
			}
		}
		
		public List<ActionLogic> getEdges() {
			return Arrays.asList(ALLIN, CHECK, RAISE);
		}
	},
	RAISE {
		public ActionLogic nextState(Action m) {
			switch(m) {
				case ALLIN: 	return ALLIN;
				case CALL:  	return CALL;
				default: 		return RAISE;
			}
		}
		
		public List<ActionLogic> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	CALL {
		public ActionLogic nextState(Action m) {
			switch(m) {
				case ALLIN: 	return ALLIN;
				case RAISE: 	return RAISE;
				default: 		return CALL;
			}	
		}
		
		public List<ActionLogic> getEdges() {
			return Arrays.asList(ALLIN, RAISE, CALL);
		}
	},
	ALLIN {
		public ActionLogic nextState(Action m) {
			return ALLIN;
		}
		
		public List<ActionLogic> getEdges() {
			return Arrays.asList(ALLIN);
		}
	};
	
	abstract ActionLogic nextState(Action m);
	abstract List<ActionLogic> getEdges();
}
