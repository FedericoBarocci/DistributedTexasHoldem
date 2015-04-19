package breads_and_aces.game;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.exceptions.DrawException;
import it.unibo.cs.sd.poker.gui.controllers.GameUpdater;
import it.unibo.cs.sd.poker.gui.controllers.PlayerElements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.exception.PlayerCheckException;
import breads_and_aces.game.exception.WinnerException;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.game.model.table.TableState;
import breads_and_aces.game.model.utils.Pair;

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

	public Player getMe() {
		return gamePlayersKeeper.getPlayer(gamePlayersKeeper.getMe());
	}
	
	public String getNextId() {
		return gamePlayersKeeper.getNext(gamePlayersKeeper.getMe()).getName();
	}
	
	public GamePlayersKeeper getPlayersKeeper() {
		return gamePlayersKeeper;
	}
	
	public Player getPlayer(String name) {
		return gamePlayersKeeper.getPlayer(name);
	}
	
	public List<Player> getPlayers() {
		return gamePlayersKeeper.getPlayers();
	}
	
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

	public boolean evaluateLogicModel() throws WinnerException {
		try {
			for(Player p : getPlayers()) {
				if(! p.getAction().equals(Action.CHECK)) 
					throw new PlayerCheckException();
			}
		}
		catch(PlayerCheckException e) {
			return false;
		}
		
		table.setNextState();
		gamePlayersKeeper.resetActions();
		
		if (table.getState().equals(TableState.WINNER)) throw new WinnerException();
		
		return true;
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

	public void update(GameUpdater gameUpdater) {
		table.reset();
		gameUpdater.getTable().forEach(card->{
			table.addCards(card);
			System.out.println("Update table: " + card);
		});
		
		for(PlayerElements pe : gameUpdater.getPlayers()) {
			Player p = gamePlayersKeeper.getPlayer(pe.getName());
			
			if (p != null) {
				p.deal(new Pair<>(pe.getCard1(), pe.getCard2()));
				p.setScore(pe.getScore());
			}
		}
		
		System.out.println("Update my cards: " + getMe().getCards().get(0) + " " +  getMe().getCards().get(1));
	}
}
