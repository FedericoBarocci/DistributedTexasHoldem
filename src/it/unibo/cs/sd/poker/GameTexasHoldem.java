package it.unibo.cs.sd.poker;

import it.unibo.cs.sd.poker.exceptions.DrawException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameTexasHoldem implements Serializable {

	private static final long serialVersionUID = 967261359515323981L;

	private final Deck deck;

	private final List<Player> players = new ArrayList<Player>();

	private final List<Card> tableCards = new ArrayList<Card>();
	
	private final List<Pot> pots = new ArrayList<Pot>();
	
	public GameTexasHoldem(Deck deck) {
		this.deck = deck;
		
		newPot();
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public String toString()
	{
		String s = "TAVOLO  \t";
		
		for (Card carta : getTableCards()) {
			s += carta.toString() + "  \t";
		}
		
		return s;
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public void deal() {
		for (Player player : players) {
			player.getCards().add(deck.pop());
			player.getCards().add(deck.pop());
		}
	}

	public void callFlop() {
		deck.pop();
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
	}

	public void betTurn() {
		deck.pop();
		tableCards.add(deck.pop());
	}

	public void betRiver() {
		deck.pop();
		tableCards.add(deck.pop());
	}

	public List<Player> getWinner() {
		checkPlayersRanking();
		
		List<Player> winnerList = new ArrayList<Player>();
		
		Player winner = players.get(0);
		Integer winnerRank = winner.getRankingInt();
		winnerList.add(winner);
		
		for (int i = 1; i < players.size(); i++) {
			Player player = players.get(i);
			Integer playerRank = player.getRankingInt();
			
			//Draw game
			if (winnerRank == playerRank) {
				try {
					Player highHandPlayer = checkHighSequence(winner, player);	
					
					winner = highHandPlayer;
					winnerRank = winner.getRankingInt();
					
					winnerList.clear();
					winnerList.add(winner);
				}
				catch (DrawException e) { 
					winnerList.add(player); 
				}
			}
			else if (winnerRank < playerRank) {
				winner = player;
				winnerRank = winner.getRankingInt();
				
				winnerList.clear();
				winnerList.add(winner);
			}
		}

		return winnerList;
	}
	
	//ok
	private Player checkHighSequence(Player player1, Player player2) throws DrawException {
		List<Card> player1Rank = player1.getSolutionCards();
		List<Card> player2Rank = player2.getSolutionCards();
		
		for(int i = 0; i < 5; i++) {
			Integer c1 = player1Rank.get(i).getRankToInt();
			Integer c2 = player2Rank.get(i).getRankToInt();
			
			if (c1 > c2) return player1;
			else if (c1 < c2) return player2;
		}
		
		throw new DrawException();
	}
	
	public List<Card> getTableCards() {
		return tableCards;
	}

	private void checkPlayersRanking() {
		for (Player p : players) {
			p.evaluateRanking(tableCards);
		}
	}

	public List<Pot> getPots() {
		return pots;
	}
	
	public void newPot() {
		Pot pot = new Pot(0);
		pots.add(pot);
	}
}
