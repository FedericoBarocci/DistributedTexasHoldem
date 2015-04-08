package it.unibo.cs.sd.poker.game.core;

public enum CardSuit {
	Cuori {
		public String getSuitChar() {
			return "c";
		}
	},
	Quadri {
		public String getSuitChar() {
			return "q";
		}
	},
	Fiori { public String getSuitChar() {
		return "f";
	}
	},
	Picche { public String getSuitChar() {
		return "p";
	}
	};
	
	abstract public String getSuitChar();
}