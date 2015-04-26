package breads_and_aces.game.model.table;

import java.util.LinkedList;
import java.util.List;

import breads_and_aces.game.core.Card;

public class Table {
	
	private final List<Card> tableCards = new LinkedList<>();
	private TableState state = TableState.DEAL;
	
	public void addCard(Card card) {
		tableCards.add(card);
	}
	
	public List<Card> getAllCards() {
		return tableCards;
	}
	
	public void setNextState() {
		state = state.next();
	}
	
	public TableState getState() {
		return state;
	}
	
	public List<Card> getRoundCards() {
		return state.getCards(tableCards);
	}

	public void reset() {
		state = TableState.DEAL;
		tableCards.clear();
	}
}
