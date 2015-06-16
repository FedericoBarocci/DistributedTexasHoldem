package bread_and_aces.game.model.players.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bread_and_aces.game.core.Card;
import bread_and_aces.game.core.Ranking;
import bread_and_aces.game.core.Rankings;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.utils.Pair;
import bread_and_aces.utils.DevPrinter;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = -7618547420110997571L;
	
	private final String name;
	private final long registrationTime;
	
	private List<Card> cards = new ArrayList<Card>();
	private Ranking rank = new Ranking();
	private Action action = Action.NONE;
	
	private int score;
	
	private boolean hasToken;

	private int bet;
	
//	@Inject private Printer printer;

	public Player(String name, long registrationTime/*, Printer printer*/) {
		this.name = name;
		this.registrationTime = registrationTime;
//		if (Main.Injector!=null)
//			Main.Injector.injectMembers(this);
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

	public void evaluateRanking(List<Card> tablecards) {
		if (action.equals(Action.FOLD)) {
			rank.setRankNotDef();
		}
		else {
			rank.setCards(cards, tablecards);
		}
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
	
	public Card getFirstCard() {
		return cards.get(0);
	}
	
	public Card getSecondCard() {
		return cards.get(1);
	}
	
	public Action getAction() {
		return action;
	}
	
	public void initBet() {
		bet = 0;
	}
	
	public int getBet() {
		return bet;
	}

	public void setAction(ActionKeeper actionKeeper) {
		this.action = actionKeeper.getAction();
		this.bet = actionKeeper.getValue();
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
		
		println(name + " token received");
	}
	
	public void receiveToken(String receivedFrom) {
		hasToken = true;
		
		println(name + " token received from "+receivedFrom);
	}
	
	public void sendToken() {
		hasToken = false;
		
		println(name + " token passed");
	}
	
	public void sendToken(String passedTo) {
		hasToken = false;
		
		println(name + " token passed to "+passedTo);
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
	
	/*private void print(String msg) {
		if (printer!=null)
			printer.print(msg);
	}*/
	private void println(String msg) {
//		if (printer!=null)
//			printer.println(msg);
		
		DevPrinter.println(new Throwable(), msg);
	}
}
