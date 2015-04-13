package breads_and_aces.game.model.table;

import it.unibo.cs.sd.poker.game.core.Card;

import java.util.LinkedList;
import java.util.List;

public class Table {
	
	private List<Card> tableCards = new LinkedList<>();

	public void addCards(Card card) {
		tableCards.add(card);
	}
	
	public List<Card> getCards() {
		return tableCards;
	}

}
