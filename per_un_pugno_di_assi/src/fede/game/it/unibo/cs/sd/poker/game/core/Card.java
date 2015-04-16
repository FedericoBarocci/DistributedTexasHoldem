package it.unibo.cs.sd.poker.game.core;

import java.io.Serializable;


public class Card implements Serializable {

	private static final long serialVersionUID = 8343990871159439035L;

	private CardSuit suit;
	private CardRank rank;

	public Card(CardSuit suit, CardRank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardRank getRank() {
		return rank;
	}

	public Integer getRankToInt() {
		return rank.ordinal();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof Card)) {
			return false;
		}
		else {
			Card card2 = (Card) obj;
			return rank.equals(card2.getRank()) && suit.equals(card2.getSuit());
		}
	}

	@Override
	public String toString() {
		return rank.toString().substring(1) + " " + suit.toString();
	}
}
