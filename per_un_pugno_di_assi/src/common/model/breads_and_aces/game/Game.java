package breads_and_aces.game;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.exceptions.DrawException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.table.Table;

import com.google.inject.Singleton;

@Singleton
public class Game {
	
	private GamePlayersKeeper gamePlayersKeeper;
	private Table table;
	private boolean isStarted = false;
	private int goal;
	
	@Inject
	public Game(GamePlayersKeeper gamePlayersKeeper, Table table) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.table = table;
	}

//	public Player getMe() {
//		return gamePlayersKeeper.getPlayer(gamePlayersKeeper.getMyName());
//	}
	
//	public GamePlayersKeeper getPlayersKeeper() {
//		return gamePlayersKeeper;
//	}
	
//	public Player getPlayer(String name) {
//		return gamePlayersKeeper.getPlayer(name);
//	}
//	
//	public List<Player> getPlayers() {
//		return gamePlayersKeeper.getPlayers();
//	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		isStarted = true;
	}

	public Table getTable() {
		return table;
	}
	
	public List<Player> getWinner() {
		checkPlayersRanking();
		
		List<Player> winnerList = new ArrayList<Player>();
		
		List<Player> players = gamePlayersKeeper.getPlayers();
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
	private void checkPlayersRanking() {
		List<Player> players = gamePlayersKeeper.getPlayers();
		for (Player p : players) {
			p.evaluateRanking(table.getCards());
		}
	}
	//ok
	private Player checkHighSequence(Player player1, Player player2) throws DrawException {
		List<Card> player1Rank = player1.getSolutionCards();
		List<Card> player2Rank = player2.getSolutionCards();

		for (int i = 0; i < 5; i++) {
			Integer c1 = player1Rank.get(i).getRankToInt();
			Integer c2 = player2Rank.get(i).getRankToInt();

			if (c1 > c2)
				return player1;
			else if (c1 < c2)
				return player2;
		}

		throw new DrawException();
	}
	
	public List<Card> showCards() {
		List<Card> list = table.showCards();
		
		for(Card c : list) {
			System.out.println("show -> " + c);
		}
		
		return list;
	}

	public void setGoal(int initialgoal) {
		this.goal = initialgoal;
	}
	
	public int getGoal() {
		return goal;
	}
}
