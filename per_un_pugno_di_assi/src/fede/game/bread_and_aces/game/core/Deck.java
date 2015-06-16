package bread_and_aces.game.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	
	private final List<Card> cards = new ArrayList<Card>();
	
	private final Random random = new Random();

	public Deck() {
		for (CardSuit suit : CardSuit.values()) {
			for (CardRank rank : CardRank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
	}

	public Card pop() {
		return cards.remove(random.nextInt(cards.size()));
	}
}
