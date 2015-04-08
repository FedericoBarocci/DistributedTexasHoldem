package it.unibo.cs.sd.poker.game.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck implements Serializable {
	
	private static final long serialVersionUID = 2463644121163649891L;

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
