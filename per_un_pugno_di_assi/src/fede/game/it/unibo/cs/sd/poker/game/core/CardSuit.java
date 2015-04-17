package it.unibo.cs.sd.poker.game.core;

public enum CardSuit {
	Hearts {
		public String getSuitChar() {
			return h;
		}
	},
	Diamonds {
		public String getSuitChar() {
			return d;
		}
	},
	Clubs {
		public String getSuitChar() {
			return c;
		}
	},
	Spades {
		public String getSuitChar() {
			return s;
		}
	};
	
	abstract public String getSuitChar();
	
	private static final String s = "s";
	private static final String c = "c";
	private static final String d = "d";
	private static final String h = "h";
}