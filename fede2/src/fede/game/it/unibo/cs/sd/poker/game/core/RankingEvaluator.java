package it.unibo.cs.sd.poker.game.core;

import static it.unibo.cs.sd.poker.game.core.CardRank._2;
import static it.unibo.cs.sd.poker.game.core.CardRank._3;
import static it.unibo.cs.sd.poker.game.core.CardRank._4;
import static it.unibo.cs.sd.poker.game.core.CardRank._5;
import static it.unibo.cs.sd.poker.game.core.CardRank._A;
import static it.unibo.cs.sd.poker.game.core.CardRank._K;
import static it.unibo.cs.sd.poker.game.core.Rankings.CARTA_ALTA;
import static it.unibo.cs.sd.poker.game.core.Rankings.COLORE;
import static it.unibo.cs.sd.poker.game.core.Rankings.COPPIA;
import static it.unibo.cs.sd.poker.game.core.Rankings.DOPPIA_COPPIA;
import static it.unibo.cs.sd.poker.game.core.Rankings.FULL;
import static it.unibo.cs.sd.poker.game.core.Rankings.NOT_DEF;
import static it.unibo.cs.sd.poker.game.core.Rankings.POKER;
import static it.unibo.cs.sd.poker.game.core.Rankings.SCALA;
import static it.unibo.cs.sd.poker.game.core.Rankings.SCALA_COLORE;
import static it.unibo.cs.sd.poker.game.core.Rankings.SCALA_REALE;
import static it.unibo.cs.sd.poker.game.core.Rankings.TRIS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RankingEvaluator implements Serializable {
	
	private static final long serialVersionUID = -8121774337357960749L;

	private List<Card> allCards = new ArrayList<Card>();
	private List<Card> solutionCards = new ArrayList<Card>();
	private Rankings rankings = NOT_DEF;
	
	public RankingEvaluator() { }
	
	public RankingEvaluator(List<Card> cards) {
		this.setAllCards(cards);
	}
	
	public Rankings getRankings() {
		return rankings;
	}
	
	private void setRankings(Rankings rankings) {
		this.rankings = rankings;
	}
	
	public List<Card> getAllCards() {
		return allCards;
	}

	public void setAllCards(List<Card> allCards) {
		this.allCards = allCards;
	}
	
	public List<Card> getSolutionCards() {
		return solutionCards;
	}

	private Boolean setSolution(List<Card> cards, Rankings rankings) {
		this.solutionCards = cards;
		setRankings(rankings);
		
		return true;
	}
	
	public Boolean isRankable() {
		return getAllCards().size() >= 5;
	}

	public Boolean isScalaReale() {
		RankingEvaluator test = new RankingEvaluator(getAllCards());
		
		if (test.isScalaColore()) {
			List<CardRank> r = toRankEnumList(test.getSolutionCards());

			if (r.contains(_K) && r.contains(_A) ) {
				return setSolution(test.getSolutionCards(), SCALA_REALE);
			}
		}
		
		return false;
	}

	public Boolean isScalaColore() {
		RankingEvaluator test = new RankingEvaluator(getAllCards());
		
		if (test.isScala()) { 
			RankingEvaluator test2 = new RankingEvaluator(test.getSolutionCards());
			
			if (test2.isColore()) {
				return setSolution(test2.getSolutionCards(), SCALA_COLORE);
			}
		}
		
		return false;
	}

	public Boolean isPoker() {
		return sameCards(4, 5, POKER);
	}

	public Boolean isFull() {
		RankingEvaluator tris = new RankingEvaluator(getAllCards());
		
		if (tris.sameCards(3, 3, TRIS)) {
			List<Card> l = new ArrayList<Card>(getAllCards()); 
			
			l.removeAll(tris.getSolutionCards());
			
			RankingEvaluator pair = new RankingEvaluator(l);
			
			if (pair.sameCards(2, 2, COPPIA)) {
				l.clear();
				l.addAll(tris.getSolutionCards());
				l.addAll(pair.getSolutionCards());
				
				return setSolution(l, FULL);
			}
		}
		
		return false;
	}

	public Boolean isColore() {
		List<Card> list = new ArrayList<Card> ();

		for (Card card1 : getAllCards()) {
			for (Card card2 : getAllCards()) {
				if (card1.getSuit().equals(card2.getSuit())) {
					if (!list.contains(card1)) {
						list.add(card1);
					}
					
					if (!list.contains(card2)) {
						list.add(card2);
					}
				}
			}
			
			if (list.size() == 5) {
				return setSolution(list, COLORE);
			}
			
			list.clear();
		}
		
		return false;
	}

	public Boolean isScala() {
		List<Card> list = new ArrayList<Card> ();
		Card cardPrevious = null;
		
		for (Card card : getAllCards()) {
			if (cardPrevious != null) {
				if (cardPrevious.getRankToInt() == card.getRankToInt()) 
					continue;
					
				if (cardPrevious.getRankToInt() - card.getRankToInt() == 1) {
					if (list.size() == 0) {
						list.add(cardPrevious);
					}
					
					list.add(card);
					
					if (list.size() == 5) {
						return setSolution(list, SCALA);
					}
				}
				else {
					list.clear();
				}
			}
			
			cardPrevious = card;
		}
		
		if (list.size() == 5) {
			return setSolution(list, SCALA);
		}
		else {
			List<CardRank> r = toRankEnumList(getAllCards());

			//Check Scala Minima - tested (ok)
			if (r.contains(_A) && r.contains(_2) && r.contains(_3) && r.contains(_4) && r.contains(_5) ) {
				list.clear();
				
				for (Card c : getAllCards()) {
					//looking for cards: A(rank 12) 2(rank 0) 3(rank 1) 4(rank 2) 5(rank 3)
					if (c.getRankToInt() == 12 || c.getRankToInt() <= 3) {
						Boolean presente = false;
						
						//avoid duplicate - tested (ok)
						for (Card c2 : list){
							if (c.getRank().equals(c2.getRank())) presente = true;
						}
						
						if (! presente) list.add(c);
					}
				}
				
				//put card A at the end of the list (ordered min value)
				list.add(list.remove(0));
				
				return setSolution(list, SCALA);
			}
		}

		return false;
	}

	public Boolean isTris() {
		return sameCards(3, 5, TRIS); 
	}

	public Boolean isDoppiaCoppia() {
		RankingEvaluator pair1 = new RankingEvaluator(getAllCards());
		
		if (pair1.sameCards(2, 2, COPPIA)) {
			List<Card> l = new ArrayList<Card>(getAllCards()); 
			
			l.removeAll(pair1.getSolutionCards());
			
			RankingEvaluator pair2 = new RankingEvaluator(l);
			
			if (pair2.sameCards(2, 3, COPPIA)) {
				l.clear();
				l.addAll(pair1.getSolutionCards());
				l.addAll(pair2.getSolutionCards());
				
				return setSolution(l, DOPPIA_COPPIA);
			}
		}
		
		return false;
	}

	public Boolean isCoppia() {
		return sameCards(2, 5, COPPIA);
	}

	public Boolean isCartaAlta() {
		List<Card> l = new ArrayList<Card>();
		
		for(int i = 0; i < 5; i++) {
			l.add(getAllCards().get(i));
		}
		
		return setSolution(l, CARTA_ALTA);
	}

	//ok
	private Boolean sameCards(Integer pairSize, Integer retSize, Rankings rankEnum) {
		List<Card> checkedPair = new ArrayList<Card>();
		
		for (Card card1 : getAllCards()) {
			checkedPair.add(card1);
			
			for (Card card2 : getAllCards()) {
				if (!card1.equals(card2) && card1.getRank().equals(card2.getRank())) {
					checkedPair.add(card2);
				}
			}
			
			if (checkedPair.size() == pairSize) {
				if (checkedPair.size() < retSize) {
					for (Card card3 : getAllCards()) {
						if (checkedPair.size() < retSize && ! card1.getRank().equals(card3.getRank())) {
							checkedPair.add(card3);
						}
					}
				}
				
				return setSolution(checkedPair, rankEnum);
			}
			
			checkedPair.clear();
		}
		
		return false;
	}

	//ok
	private List<CardRank> toRankEnumList(List<Card> cards) {
		List<CardRank> rankEnumList = new ArrayList<CardRank>();

		for (Card card : cards) {
			rankEnumList.add(card.getRank());
		}

		return rankEnumList;
	}

}
