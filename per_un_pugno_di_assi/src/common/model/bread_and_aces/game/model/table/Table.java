package bread_and_aces.game.model.table;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Singleton;

import bread_and_aces.game.core.Card;

@Singleton
public class Table {
	
	private final List<Card> tableCards = new LinkedList<>();
	private TableState state = TableState.DEAL;
	
	public void addCard(Card card) {
		tableCards.add(card);
	}
	
	public List<Card> getAllCards() {
		return tableCards;
	}

	public void setState(TableState tableState) {
		this.state = tableState;		
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
