package it.unibo.cs.sd.poker.game.core;

public enum CardSuit {
	Cuori {
		public String getSuitChar() {
			return "h";
		}
	},
	Quadri {
		public String getSuitChar() {
			return "d";
		}
	},
	Fiori {
		public String getSuitChar() {
			return "c";
		}
	},
	Picche {
		public String getSuitChar() {
			return "s";
		}
	};
	
	abstract public String getSuitChar();
}