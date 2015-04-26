package breads_and_aces.game.core;

public enum Rankings {
	CARTA_ALTA {
		public String toString() { return "Carta Alta"; }
	},
	COPPIA {
		public String toString() { return "Coppia"; }
	},
	DOPPIA_COPPIA {
		public String toString() { return "Doppia Coppia"; }
	},
	TRIS {
		public String toString() { return "Tris"; }
	},
	SCALA {
		public String toString() { return "Scala"; }
	},
	COLORE {
		public String toString() { return "Colore"; }
	},
	FULL {
		public String toString() { return "Full"; }
	},
	POKER {
		public String toString() { return "Poker"; }
	},
	SCALA_COLORE {
		public String toString() { return "Scala Colore"; }
	},
	SCALA_REALE {
		public String toString() { return "Scala Reale"; }
	},
	NOT_DEF {
		public String toString() { return ""; }
	}
}
