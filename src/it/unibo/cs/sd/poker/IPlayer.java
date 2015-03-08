package it.unibo.cs.sd.poker;

import java.util.List;


public interface IPlayer {
	public Card[] getCards();

	public List<Card> getRankingList();

	public void setRankingList(List<Card> rankingList);

	public RankingEnum getRankingEnum();

	public void setRankingEnum(RankingEnum rankingEnum);
	
	public String getName();
	
	public void setName(String name);
	
	public void printStatus();
}
