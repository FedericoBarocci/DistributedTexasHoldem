package it.unibo.cs.sd.poker.gui.controllers;

import it.unibo.cs.sd.poker.game.core.Card;

import java.io.Serializable;

public class PlayerElements implements Serializable {

	private static final long serialVersionUID = 1218843694103815791L;
	
	private final String name;
	private final Card card1;
	private final Card card2;
	private final int score;
	
	public PlayerElements(String name, Card card1, Card card2, int score) {
		this.name = name;
		this.card1 = card1;
		this.card2 = card2;
		this.score = score;
	}
	
	public String getName() {return name;}
	public Card getCard1() {return card1;}
	public Card getCard2() {return card2;}
	public int getScore() {return score;}
}
