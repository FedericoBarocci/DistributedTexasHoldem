package breads_and_aces.game.model.table;

import java.util.ArrayList;
import java.util.List;

import breads_and_aces.game.core.Card;

public enum TableState {
	DEAL {
		public List<Card> getCards(List<Card> tableCards) {
			return new ArrayList<Card>();
		}
		public TableState next() {return FLOP;}
	},
	FLOP {
		public List<Card> getCards(List<Card> tableCards) {
			return tableCards.subList(0, 3);
		}
		public TableState next() {return TURN;}
	},
	TURN {
		public List<Card> getCards(List<Card> tableCards) {
			return tableCards.subList(3, 4);
		}
		public TableState next() {return RIVER;}
	},
	RIVER {
		public List<Card> getCards(List<Card> tableCards) {
			return tableCards.subList(4, 5);
		}
		public TableState next() {return WINNER;}
	},
	WINNER {
		public List<Card> getCards(List<Card> tableCards) {
			return tableCards;
		}
		public TableState next() {return DEAL;}
	};
	
	abstract List<Card> getCards(List<Card> tableCards);
	abstract TableState next();
}


