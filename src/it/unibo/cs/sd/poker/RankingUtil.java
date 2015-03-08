package it.unibo.cs.sd.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import static it.unibo.cs.sd.poker.CardRankEnum.*;
import static it.unibo.cs.sd.poker.RankingEnum.*;

public class RankingUtil {

	private RankingUtil() { }
	
	//ok: 0 = "carta alta" .. max = "scala reale"
	public static Integer getRankingToInt(IPlayer player) {
		return player.getRankingEnum().ordinal();
	}

	public static void checkRanking(IPlayer player, List<Card> tableCards) {
		List<Card> mergedList = getOrderedCardList(player, tableCards);

		//SCALA_REALE
		List<Card> rankingList = getScalaReale(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, SCALA_REALE, rankingList);
			return;
		}
		
		//SCALA_COLORE
		rankingList = getScalaColore(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, SCALA_COLORE, rankingList);
			return;
		}
		
		//POKER
		rankingList = getPoker(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, POKER, rankingList);
			return;
		}
		
		//FULL
		rankingList = getFull(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, FULL, rankingList);
			return;
		}
		
		//COLORE
		rankingList = getColore(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, COLORE, rankingList);
			return;
		}
		
		//SCALA
		rankingList = getScala(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, SCALA, rankingList);
			return;
		}
		
		//TRIS
		rankingList = getTris(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, TRIS, rankingList);
			return;
		}
		
		//DOPPIA_COPPIA
		rankingList = getDoppiaCoppia(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, DOPPIA_COPPIA, rankingList);
			return;
		}
		
		//COPPIA
		rankingList = getCoppia(mergedList);
		if (rankingList != null) {
			setRankingEnumAndList(player, COPPIA, rankingList);
			return;
		}
		
		//CARTA_ALTA
		rankingList = getCartaAlta(mergedList);
		setRankingEnumAndList(player, CARTA_ALTA, rankingList);

		return;
	}

	public static List<Card> getScalaReale(List<Card> cards) {
		List<Card> l = getScalaColore(cards);
		
		if (l != null) {
			List<CardRankEnum> r = toRankEnumList(l);

			if (r.contains(_10) && r.contains(_J) && r.contains(_Q) && r.contains(_K) && r.contains(_A) ) {
				return l;
			}
		}
		
		return null;
	}

	public static List<Card> getScalaColore(List<Card> cards) {
		List<Card> l = getScala(cards);
		
		if (l != null && getColore(l) != null) return l;
		else return null;
	}

	public static List<Card> getPoker(List<Card> cards) {
		return checkPair(cards, 4, 5);
	}

	public static List<Card> getFull(List<Card> cards) {
		List<Card> mergedList = new ArrayList<Card> (cards);
		List<Card> threeList = checkPair(mergedList, 3, 3);
		
		if (threeList != null) {
			mergedList.removeAll(threeList);
			List<Card> twoList = checkPair(mergedList, 2, 2);
			
			if (twoList != null) {
				threeList.addAll(twoList);
				return threeList;
			}
		}
		
		return null;
	}

	public static List<Card> getColore(List<Card> cards) {
		List<Card> flushList = new ArrayList<Card> ();

		for (Card card1 : cards) {
			for (Card card2 : cards) {
				if (card1.getSuit().equals(card2.getSuit())) {
					if (!flushList.contains(card1)) {
						flushList.add(card1);
					}
					
					if (!flushList.contains(card2)) {
						flushList.add(card2);
					}
				}
			}
			
			if (flushList.size() == 5) {
				return flushList;
			}
			
			flushList.clear();
		}
		
		return null;
	}

	public static List<Card> getScala(List<Card> cards) {
		List<Card> sequenceList = new ArrayList<Card> ();
		Card cardPrevious = null;
		
		for (Card card : cards) {
			if (cardPrevious != null) {
				if (cardPrevious.getRankToInt() == card.getRankToInt()) 
					continue;
					
				if (cardPrevious.getRankToInt() - card.getRankToInt() == 1) {
					if (sequenceList.size() == 0) {
						sequenceList.add(cardPrevious);
					}
					
					sequenceList.add(card);
					
					if (sequenceList.size() == 5) {
						return sequenceList;
					}
				}
				else {
					sequenceList.clear();
				}
			}
			
			cardPrevious = card;
		}
		
		if (sequenceList.size() == 5) {
			return sequenceList;
		}
		else {
			List<CardRankEnum> r = toRankEnumList(cards);

			//Check Scala Minima - tested (ok)
			if (r.contains(_A) && r.contains(_2) && r.contains(_3) && r.contains(_4) && r.contains(_5) ) {
				sequenceList.clear();
				
				for (Card c : cards) {
					//looking for cards: A(rank 12) 2(rank 0) 3(rank 1) 4(rank 2) 5(rank 3)
					if (c.getRankToInt() == 12 || c.getRankToInt() <= 3) {
						Boolean presente = false;
						
						//avoid duplicate - tested (ok)
						for (Card c2 : sequenceList){
							if (c.getRank().equals(c2.getRank())) presente = true;
						}
						
						if (! presente) sequenceList.add(c);
					}
				}
				
				//put card A at the end of the list (ordered min value)
				sequenceList.add(sequenceList.remove(0));
				
				return sequenceList;
			}
		}

		return null;
	}

	public static List<Card> getTris(List<Card> cards) {
		return checkPair(cards, 3, 5);
	}

	public static List<Card> getDoppiaCoppia(List<Card> cards) {
		List<Card> mergedList = new ArrayList<Card> (cards);
		List<Card> twoPair1 = checkPair(mergedList, 2, 2);
		
		if (twoPair1 != null) {
			mergedList.removeAll(twoPair1);
			List<Card> twoPair2 = checkPair(mergedList, 2, 3);
			
			if (twoPair2 != null) {
				twoPair1.addAll(twoPair2);
				
				return twoPair1;
			}
		}
		
		return null;
	}

	public static List<Card> getCoppia(List<Card> cards) {
		return checkPair(cards, 2, 5);
	}

	public static List<Card> getCartaAlta(List<Card> cards) {
		while (cards.size() > 5) {
			cards.remove(cards.size() - 1);
		}
	
		return cards;
	}

	//ok
	private static List<Card> getMergedCardList(IPlayer player, List<Card> tableCards) {
		List<Card> merged = new ArrayList<Card>();
		
		merged.addAll(tableCards);
		merged.add(player.getCards()[0]);
		merged.add(player.getCards()[1]);
		
		return merged;
	}

	//ok
	private static List<Card> getOrderedCardList(IPlayer player, List<Card> tableCards) {
		List<Card> ordered = getMergedCardList(player, tableCards);
		
		Collections.sort(ordered, new Comparator<Card>() {
			public int compare(Card c1, Card c2) {
				return c1.getRankToInt() < c2.getRankToInt() ? 1 : -1;
			}
		});
		
		return ordered;
	}

	//ok
	private static List<Card> checkPair(List<Card> mergedList, Integer pairSize, Integer retSize) {
		List<Card> checkedPair = new ArrayList<Card>();
		
		for (Card card1 : mergedList) {
			checkedPair.add(card1);
			
			for (Card card2 : mergedList) {
				if (!card1.equals(card2) && card1.getRank().equals(card2.getRank())) {
					checkedPair.add(card2);
				}
			}
			
			if (checkedPair.size() == pairSize) {
				if (checkedPair.size() < retSize) {
					for (Card card3 : mergedList) {
						if (checkedPair.size() < retSize && ! card1.getRank().equals(card3.getRank())) {
							checkedPair.add(card3);
						}
					}
				}
				
				return checkedPair;
			}
			
			checkedPair.clear();
		}
		
		return null;
	}

	//ok
	private static List<CardRankEnum> toRankEnumList(List<Card> cards) {
		List<CardRankEnum> rankEnumList = new ArrayList<CardRankEnum>();

		for (Card card : cards) {
			rankEnumList.add(card.getRank());
		}

		return rankEnumList;
	}

	private static void setRankingEnumAndList(IPlayer player, RankingEnum rankingEnum, List<Card> rankingList) {
		player.setRankingEnum(rankingEnum);
		player.setRankingList(rankingList);
	}
}
