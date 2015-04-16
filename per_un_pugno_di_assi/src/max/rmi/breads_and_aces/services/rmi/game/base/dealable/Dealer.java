package breads_and_aces.services.rmi.game.base.dealable;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.core.Deck;

import java.util.Collection;

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
		for (int i=0; i<5; i++) {
//			tableCardsToSend.add( deck.pop() );
			table.addCards( deck.pop() );
		}
		players.forEach(c->{
			Pair<Card> cards = new Pair<>(deck.pop(), deck.pop());
			c.deal( cards );
		});
	}
}
