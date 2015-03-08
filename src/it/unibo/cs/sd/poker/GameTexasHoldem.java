package it.unibo.cs.sd.poker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.List;

public class GameTexasHoldem implements Serializable {

	private static final long serialVersionUID = 967261359515323981L;

	private IDeck deck;

	private List<IPlayer> players;

	private List<Card> tableCards;

	public void newGame(IDeck deck, IPlayer player1, IPlayer... _players) {
		this.deck = deck;
		tableCards = new ArrayList<Card>();
		players = new ArrayList<IPlayer>();
		
		//the game needs at least one player
		players.add(player1);
		players.addAll(Arrays.asList(_players));
	}
	
	public void newGame(IDeck deck, List<IPlayer> player) {
		this.deck = deck;
		tableCards = new ArrayList<Card>();
		players = new ArrayList<IPlayer>();
		
		players.addAll(player);
	}
	
	public void printStatus()
	{
		List<Card> tavolo = this.getTableCards();
		
		System.out.print("TAVOLO  \t");
		
		for (Card carta : tavolo) {
			System.out.print( carta.toString() + "  \t");
		}
		
		System.out.println("\n");
	}

	//To abandon the game
	public void removePlayer(IPlayer player) {
		players.remove(player);
	}

	public void deal() {
		for (IPlayer player : players) {
			player.getCards()[0] = deck.pop();
			player.getCards()[1] = deck.pop();
		}
		
		//checkPlayersRanking();
	}

	/**
	 * doble initial bet
	 */
	public void callFlop() {
		deck.pop();
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		tableCards.add(deck.pop());
		
		//checkPlayersRanking();
	}

	public void betTurn() {
		deck.pop();
		tableCards.add(deck.pop());
		
		//checkPlayersRanking();
	}

	public void betRiver() {
		deck.pop();
		tableCards.add(deck.pop());
		
		//checkPlayersRanking();
	}

	public List<IPlayer> getWinner() {
		checkPlayersRanking();
		
		List<IPlayer> winnerList = new ArrayList<IPlayer>();
		
		IPlayer winner = players.get(0);
		Integer winnerRank = RankingUtil.getRankingToInt(winner);
		winnerList.add(winner);
		
		for (int i = 1; i < players.size(); i++) {
			IPlayer player = players.get(i);
			Integer playerRank = RankingUtil.getRankingToInt(player);
			
			//Draw game
			if (winnerRank == playerRank) {
				IPlayer highHandPlayer = checkHighSequence(winner, player);
				
				//Draw checkHighSequence
				if (highHandPlayer == null) {
					winnerList.add(player);
				}
				else {
					winner = highHandPlayer;
					winnerRank = RankingUtil.getRankingToInt(winner);
					
					winnerList.clear();
					winnerList.add(winner);
				}
			}
			else if (winnerRank < playerRank) {
				winner = player;
				winnerRank = RankingUtil.getRankingToInt(winner);
				
				winnerList.clear();
				winnerList.add(winner);
			}
		}

		return winnerList;
	}
	
	//ok
	private IPlayer checkHighSequence(IPlayer player1, IPlayer player2) {
		List<Card> player1Rank = player1.getRankingList();
		List<Card> player2Rank = player2.getRankingList();
		
		for(int i = 0; i < 5; i++) {
			Integer c1 = player1Rank.get(i).getRankToInt();
			Integer c2 = player2Rank.get(i).getRankToInt();
			
			//System.out.println(i + ": " + player1.getName() + c1 + " vs " + player2.getName() + c2);
			
			if (c1 > c2) return player1;
			else if (c1 < c2) return player2;
		}
		
		return null;
	}
	
	public List<Card> getTableCards() {
		return tableCards;
	}

	private void checkPlayersRanking() {
		for (IPlayer player : players) {
			RankingUtil.checkRanking(player, tableCards);
		}
	}
}
