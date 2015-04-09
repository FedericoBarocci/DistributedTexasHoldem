package breads_and_aces.game.model.players.player;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.core.Deck;
import it.unibo.cs.sd.poker.game.core.MaxReachedException;
import it.unibo.cs.sd.poker.game.core.PositiveInteger;
import it.unibo.cs.sd.poker.game.core.Ranking;
import it.unibo.cs.sd.poker.game.core.Rankings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.main.Main;
import breads_and_aces.utils.printer.Printer;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = -7618547420110997571L;
	private final String name;
	
	private final List<Card> cards = new ArrayList<Card>();
	private final Ranking rank = new Ranking();
	private final PositiveInteger chips = new PositiveInteger();
	private Action action = Action.NONE;
	
	private long registrationTime;
	private boolean hasBucket;
	
	@Inject private /*final*/ Printer printer;

//	@AssistedInject
	public Player(/*@Assisted*/ String name/*, Printer printer*/) {
		this.name = name;
//		this.printer = printer;
		Main.Injector.injectMembers(this);
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * Game zone - start
	 */
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
	
	public List<Card> getCards() {
		return cards;
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
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
	/*
	 * Game zone - end
	 */
	

	/*
	 * Bucket zone - start
	 */
	public void receiveBucket() {
		hasBucket = true;
		printer.println("bucket received");
//		System.out.println("bucket received"); 
	}
	public void receiveBucket(String receivedFrom) {
		hasBucket = true;
		printer.println("bucket received from "+receivedFrom);
//		System.out.println("bucket received from "+receivedFrom);
	}
	public void passBucket() {
		hasBucket = false;
		printer.println("bucket passed");
//		System.out.println("bucket passed");
	}
	public void passBucket(String passedTo) {
		hasBucket = false;
		printer.println("bucket passed to "+passedTo);
//		System.out.println("bucket passed to "+passedTo);
	}
	public boolean hasBucket() {
		return hasBucket;
	}
	/*
	 * Bucket zone - end
	 */

	/*
	 * Register zone - start
	 */
//	public void setRegisterPosition(long registrationTime) {
//		this.registrationTime = registrationTime;
//	}

	@Override
	public int compareTo(Player player) {
		if (this.registrationTime < player.registrationTime) return -1;
		if (this.registrationTime > player.registrationTime) return 1;
		return 0;
	}
	/*
	 * Register zone - end
	 */

	/*public static class PlayersComparator implements Comparator<Player> {
		@Override
		public int compare(Player p1, Player p2) {
			return p1.compareTo(p2);
		}
	}*/
	
	/*@Override
	public String toString() {
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
	}*/
}
