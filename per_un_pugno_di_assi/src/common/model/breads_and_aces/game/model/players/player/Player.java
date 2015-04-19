package breads_and_aces.game.model.players.player;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.core.MaxReachedException;
import it.unibo.cs.sd.poker.game.core.PositiveInteger;
import it.unibo.cs.sd.poker.game.core.Ranking;
import it.unibo.cs.sd.poker.game.core.Rankings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.utils.Pair;
import breads_and_aces.main.Main;
import breads_and_aces.utils.printer.Printer;

public class Player implements Serializable/*, Comparable<Player>*/ {

	private static final long serialVersionUID = -7618547420110997571L;
	
	private final String name;
	//private final long registrationTime;
	
	private List<Card> cards = new ArrayList<Card>();
	private Ranking rank = new Ranking();
	private PositiveInteger chips = new PositiveInteger();
	private Action action = Action.NONE;
	
	private int score;
	
	private boolean hasToken;
	
	@Inject private Printer printer;

	public Player(String name/*, long registrationTime, Printer printer*/) {
		this.name = name;
		//this.registrationTime = registrationTime/*, Printer printer*/;
		if (Main.Injector!=null)
			Main.Injector.injectMembers(this);
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * Game zone - start
	 */
	public void deal(Pair<Card> cards2) {
		action = Action.NONE;
		
		cards.clear();
		cards.add( cards2.getFirst() );
		cards.add( cards2.getSecond() );
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
	 * Token zone - start
	 */
	public void receiveToken() {
		hasToken = true;
		
		if (printer!=null)
			printer.println(name + " token received");
	}
	
	public void receiveToken(String receivedFrom) {
		hasToken = true;
		
		if (printer!=null)
			printer.println(name + " token received from "+receivedFrom);
	}
	
	public void sendToken() {
		hasToken = false;
		
		if (printer!=null)
			printer.println(name + " token passed");
	}
	
	public void sendToken(String passedTo) {
		hasToken = false;
		
		if (printer!=null)
			printer.println(name + " token passed to "+passedTo);
	}
	
	public boolean hasToken() {
		return hasToken;
	}

	public Integer getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	/*
	 * Token zone - end
	 */

	/*
	 * Register zone - start
	 */
//	public void setRegisterPosition(long registrationTime) {
//		this.registrationTime = registrationTime;
//	}

//	@Override
//	public int compareTo(Player player) {
//		if (this.registrationTime < player.registrationTime) return -1;
//		if (this.registrationTime > player.registrationTime) return 1;
//		return 0;
//	}
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
