package it.unibo.cs.sd.poker.game.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Player implements Serializable {
	
	private static final long serialVersionUID = 4664480702994610549L;

	private List<Card> cards = new ArrayList<Card>();
	private Ranking rank = new Ranking();
	private String name = new String();
	private Integer chips;
	private Action action;
	
	//public Boolean clientPlayer = false;
	
	public Player(String string, Boolean clientPlayer) {
		this.name = string;
		//this.clientPlayer = clientPlayer;
		this.setAction(Action.NULL);
	}
	
	public Player(String string) {
		this.name = string;
		this.setAction(Action.NULL);
	}
	
	public void deal(Deck deck) {
		cards.clear();
		cards.add(deck.pop());
		cards.add(deck.pop());
	}

	public Integer getChip() {
		return chips;
	}

	public void setChip(Integer chips) {
		this.chips = chips;
	}
	
	public Boolean bet(Integer num) {
		if (num <= getChip()) {
			setChip(getChip() - num);
			
			return true;
		}
		
		return false;
	}
	
	public Integer allin() {
		Integer tot = getChip();
		setChip(0);
		
		return tot;
	}

	public void evaluateRanking(List<Card> tablecards) {
		rank.setCards(getCards(), tablecards);
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
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
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
