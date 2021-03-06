package bread_and_aces.game.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranking implements Serializable {

	private static final long serialVersionUID = -3417533368420199960L;

	private RankingEvaluator rank = new RankingEvaluator();

	public Ranking() {}
	
	public Ranking(List<Card> cards) {
		evaluateRanking(cards);
	}
	
	public Rankings getRanking() {
		return rank.getRankings();
	}

	public List<Card> getSolutionCards() {
		return rank.getSolutionCards();
	}
	
	//ok: 0 = "carta alta" .. max = "scala reale"
	public Integer getRankingToInt() {
		return getRanking().ordinal();
	}
	
	public Ranking(List<Card> playerCards, List<Card> tableCards) {
		List<Card> mergedList = getOrderedCardList(playerCards, tableCards);
		evaluateRanking(mergedList);
	}
	
	public void setCards(List<Card> cards) {
		evaluateRanking(cards);
	}
	
	public void setCards(List<Card> playerCards, List<Card> tableCards) {
		List<Card> mergedList = getOrderedCardList(playerCards, tableCards);
		evaluateRanking(mergedList);
	}

	private void evaluateRanking(List<Card> cards) {
		rank.setAllCards(cards);

		if (rank.isRankable()) {
			do {
				if (rank.isScalaReale()) break;
				if (rank.isScalaColore()) break;
				if (rank.isPoker()) break;
				if (rank.isFull()) break;
				if (rank.isColore()) break;
				if (rank.isScala()) break;
				if (rank.isTris()) break;
				if (rank.isDoppiaCoppia()) break;
				if (rank.isCoppia()) break;
				
				rank.isCartaAlta();
			} while (false);
		}
	}
	
	//ok
	private static List<Card> getOrderedCardList(List<Card> playerCards, List<Card> tableCards) {
		List<Card> ordered = new ArrayList<Card>();
		
		ordered.addAll(playerCards);
		ordered.addAll(tableCards);
		
		Collections.sort(ordered, new Comparator<Card>() {
			public int compare(Card c1, Card c2) {
				return c1.getRankToInt() < c2.getRankToInt() ? 1 : -1;
			}
		});
		
		return ordered;
	}

	public void setRankNotDef() {
		this.rank.setRankings(Rankings.NOT_DEF);
	}
}
