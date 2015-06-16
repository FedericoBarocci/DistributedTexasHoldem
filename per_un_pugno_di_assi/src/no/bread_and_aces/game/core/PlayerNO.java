package bread_and_aces.game.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bread_and_aces.game.exceptions.MaxReachedException;


public class PlayerNO implements Serializable {
	
	private static final long serialVersionUID = 4664480702994610549L;

	private final List<Card> cards = new ArrayList<Card>();
	private final Ranking rank = new Ranking();
	private final String name;
	private final PositiveInteger chips = new PositiveInteger();
	private Action action = Action.NONE;
	
	//public Boolean clientPlayer = false;
	
	/*public Player(String string, Boolean clientPlayer) {
		this.name = string;
		//this.clientPlayer = clientPlayer;
		this.setAction(Action.NULL);
	}*/
	
	public PlayerNO(String string) {
		this.name = string;
	}
	
	public void deal(Deck deck) {
		cards.clear();
		cards.add(deck.pop());
		cards.add(deck.pop());
	}

	public PositiveInteger getChip() {
		return chips;
	}

	public void setChip(int chips) {
		try {
			this.chips.add(chips);
		} catch (MaxReachedException e) {}
	}
	
	/*public Boolean bet(Integer num) {
		if (num <= getChip()) {
			setChip(getChip() - num);
			return true;
		}
		return false;
	}*/
	
	/*public Integer allin() {
		Integer tot = getChip();
		setChip(0);
		return tot;
	}*/

	public void evaluateRanking(List<Card> tablecards) {
		rank.setCards(cards, tablecards);
	}
	
	public Rankings getRanking() {
		return rank.getRanking();
	}
	
	public Integer getRankingInt(){
		return rank.getRankingToInt();
	}
	
	public List<Card> getSolutionCards() {
		return rank.getSolutionCards();
	}

	public String getName() {
		return name;
	}
	
	/*public void setName(String name) {
		this.name = name;
	}*/

	public List<Card> getCards() {
		return cards;
	}

	/*public void setCards(List<Card> cards) {
		this.cards = cards;
	}*/
	
	@Override
	public String toString()
	{
		String s = this.getName() + " \t";
		
		for (Card carta : this.getCards()) {
			s += carta.toString() + "  \t";
		}
		
		if (rank.getRanking().ordinal() == 2)
			s += "\t" + rank.getRanking().toString() + "\t";
		else
			s += "\t" + rank.getRanking().toString() + "    \t";
		
		for (Card carta : rank.getSolutionCards()) {
			s += carta.toString() + "  \t";
		}
		
		return s;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public Boolean isHandReady() {
		return getCards().size() == 2;
	}
}
