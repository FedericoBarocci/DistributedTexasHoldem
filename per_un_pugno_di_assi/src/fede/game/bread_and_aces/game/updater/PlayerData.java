package bread_and_aces.game.updater;

import java.io.Serializable;

import bread_and_aces.game.core.Card;

public class PlayerData implements Serializable {

	private static final long serialVersionUID = 1218843694103815791L;
	
	private final String name;
	private final Card card1;
	private final Card card2;
	private final int score;
	
	public PlayerData(String name, Card card1, Card card2, int score) {
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
