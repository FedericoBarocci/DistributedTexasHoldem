package breads_and_aces.services.rmi.game.base.dealable;

import java.util.Collection;

import breads_and_aces.game.core.Card;
import breads_and_aces.game.core.Deck;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.game.model.utils.Pair;

public class Dealer {

	private final Table table;
	private final Deck deck;
	private final Collection<Player> players;

	public Dealer(Table table, Collection<Player> players, Deck deck) {
		this.table = table;
		this.players = players;
		this.deck = deck;
	}
	
	public void deal() {
		table.reset();
		
		for (int i=0; i<5; i++) {
			table.addCard( deck.pop() );
		}
		
		players.forEach(p->{
			Pair<Card> cards = new Pair<>(deck.pop(), deck.pop());
			p.deal( cards );
		});
	}
}
