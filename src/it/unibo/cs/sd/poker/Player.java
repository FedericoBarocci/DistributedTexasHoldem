package it.unibo.cs.sd.poker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Player implements Serializable {
	
	private static final long serialVersionUID = 4664480702994610549L;

	private List<Card> cards = new ArrayList<Card>();
	
	private Ranking rank = new Ranking();
	
	private String name = new String();
	
	private Integer chips;
	
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
	
	public RankingEnum getRanking() {
		return rank.getRanking();
	}
	
	public Integer getRankingInt(){
		return rank.getRankingToInt();
	}
	
	public List<Card> getSolutionCards() {
		return rank.getSolutionCards();
	}

	public Player(String string) {
		this.name = string;
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
}
