package breads_and_aces.game.model.table;

import it.unibo.cs.sd.poker.game.core.Card;

import java.util.ArrayList;
import java.util.List;

public class Table {
	
	private List<Card> tableCards = new ArrayList<>();
	private TableState state = TableState.DEAL;
	
	public Table() {}

	public void addCards(Card card) {
		tableCards.add(card);
	}
	
	public List<Card> getCards() {
		return tableCards;
	}
	
	public void setNextState() {
		state = state.next();
	}
	
	public TableState getState() {
		return state;
	}
	
	public List<Card> showCards() {
		return state.getCards(tableCards);
	}

	public void reset() {
		state = TableState.DEAL;
		tableCards = new ArrayList<>();
	}
}
