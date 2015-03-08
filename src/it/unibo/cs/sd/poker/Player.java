package it.unibo.cs.sd.poker;

import java.io.Serializable;
import java.util.List;


public class Player implements IPlayer, Serializable {

	private static final long serialVersionUID = 4664480702994610549L;

	private Card[] cards = new Card[2];

	private RankingEnum rankingEnum = null;

	private List<Card> rankingList = null;
	
	private String name = null;

	public Player(String string) {
		this.name = string;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public RankingEnum getRankingEnum() {
		return rankingEnum;
	}

	public void setRankingEnum(RankingEnum rankingEnum) {
		this.rankingEnum = rankingEnum;
	}

	public List<Card> getRankingList() {
		return rankingList;
	}

	public void setRankingList(List<Card> rankingList) {
		this.rankingList = rankingList;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
	public void printStatus()
	{
		System.out.print(this.getName() + " \t");
		
		for (Card carta : this.getCards()) {
			System.out.print( carta.toString() + "  \t");
		}
		
		if (this.getRankingEnum().ordinal() == 2)
			System.out.print("\t" + this.getRankingEnum().toString() + "\t");
		else
			System.out.print("\t" + this.getRankingEnum().toString() + "    \t");
		
		for (Card carta : this.getRankingList()) {
			System.out.print( carta.toString() + "  \t");
		}
		
		System.out.println("");
	}
}
