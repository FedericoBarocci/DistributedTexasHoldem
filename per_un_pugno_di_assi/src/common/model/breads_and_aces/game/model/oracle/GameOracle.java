package breads_and_aces.game.model.oracle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.core.Action;
import breads_and_aces.game.core.Card;
import breads_and_aces.game.exceptions.DrawException;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.table.Table;
import breads_and_aces.game.model.table.TableState;
import breads_and_aces.game.model.utils.Pair;
import breads_and_aces.game.updater.GameUpdater;
import breads_and_aces.game.updater.PlayerData;
import breads_and_aces.gui.controllers.exceptions.SinglePlayerException;

@Singleton
public class GameOracle {
	
	private final GamePlayersKeeper gamePlayersKeeper;
	private final Table table;

	private GameStates actionlogic = GameStates.NULL;
	
	@Inject
	public GameOracle(Table table, GamePlayersKeeper gamePlayersKeeper) {
		this.table = table;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}
	
	/*for recovery*/
	public void setGameState(GameStates actionlogic) {
		this.actionlogic = actionlogic;
	}
	
	/*for recovery*/
	public GameStates getGameState() {
		return actionlogic;
	}
	
	/*for recovery*/
	public void nextGameState(Action m) {
		actionlogic = actionlogic.nextState(m);
	}
	
	/* for gui: possible user input */
	public List<GameStates> getLegalActions() {
		return actionlogic.getEdges();
	}
	
	public Player getSuccessor(String playerId) throws SinglePlayerException {
		Player next;
		
		if (gamePlayersKeeper.getPlayers().size() == 1) { 
			throw new SinglePlayerException();
		}
		
		do {
			next = gamePlayersKeeper.getNext(playerId);
			
			if (next.getName().equals(playerId)) { 
				throw new SinglePlayerException();
			}
		}
		while (next.getAction().equals(Action.FOLD));
		
		return next;
	}
	
	public OracleResponses ask() {
		List<Player> players = gamePlayersKeeper.getPlayers();

		if (ConditionPlayersHaveToSpeek(players)) {
			return OracleResponses.OK;
		}
		
		if (ConditionAllIn(players) || ConditionSinglePlayer(players)) {
			return OracleResponses.WINNER;
		}
		
		if (ConditionAllAgree(players) || ConditionCallToMostOneRaise(players)) {
			table.setNextState();
			gamePlayersKeeper.resetActions();
			
			if (table.getState().equals(TableState.WINNER)) {
				return OracleResponses.WINNER;
			}
			
			return OracleResponses.NEXT_STEP;
		}
		
		return OracleResponses.OK;
	}

	public void update(GameUpdater gameUpdater) {
		table.reset();
		
		gameUpdater.getTable().forEach(card->{
			table.addCard(card);
			System.out.println("Update table: " + card);
		});
		
		for(PlayerData pd : gameUpdater.getPlayers()) {
			Player p = gamePlayersKeeper.getPlayer(pd.getName());
			
			if (p != null) {
				p.deal(new Pair<>(pd.getCard1(), pd.getCard2()));
				p.setScore(pd.getScore());
			}
		}
		
		System.out.println("Update my cards: " + gamePlayersKeeper.getMyPlayer().getCards().get(0) + " " +  gamePlayersKeeper.getMyPlayer().getCards().get(1));
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
			p.evaluateRanking(table.getAllCards());
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
	
	private boolean ConditionAllIn(List<Player> players) {
		return players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.ALLIN));
	}
	
	private boolean ConditionSinglePlayer(List<Player> players) {
		return players.stream().filter(p->!p.getAction().equals(Action.FOLD)).count() == 1;
	}
	
	private boolean ConditionPlayersHaveToSpeek(List<Player> players) {
		return players.stream().filter(p->p.getAction().equals(Action.NONE)).count() > 0;
	}
	
	private boolean ConditionAllAgree(List<Player> players) {
		return players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.CHECK));
	}
	
	private boolean ConditionCallToMostOneRaise(List<Player> players) {
		return players.stream().allMatch(p -> p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.RAISE) || p.getAction().equals(Action.CALL)) 
				&& players.stream() .filter(p -> p.getAction().equals(Action.RAISE)).count() <= 1;
	}
}
