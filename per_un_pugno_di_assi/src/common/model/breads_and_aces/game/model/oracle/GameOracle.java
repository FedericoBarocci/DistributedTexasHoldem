package breads_and_aces.game.model.oracle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.core.Card;
import breads_and_aces.game.exceptions.DrawException;
import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.oracle.responses.OracleResponse;
import breads_and_aces.game.model.oracle.responses.OracleResponseFactory;
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
	private final OracleResponseFactory oracleResponseFactory;

	@Inject
	public GameOracle(Table table, GamePlayersKeeper gamePlayersKeeper, OracleResponseFactory oracleResponseFactory) {
		this.table = table;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.oracleResponseFactory = oracleResponseFactory;
	}
	
	public Player getSuccessor(String playerId) throws SinglePlayerException {
		Player next;
		String actualPlayerId = playerId;
		
		if (gamePlayersKeeper.getPlayers().size() == 1) { 
			throw new SinglePlayerException();
		}
		
		do {
			next = gamePlayersKeeper.getNext(actualPlayerId);
			
			if (next.getName().equals(playerId)) { 
				throw new SinglePlayerException();
			}
			
			actualPlayerId = next.getName();
		}
		while (next.getAction().equals(Action.FOLD));
		
		return next;
	}
	
	public OracleResponse ask() {
		List<Player> players = gamePlayersKeeper.getPlayers();

		if (conditionAllIn(players) || conditionSinglePlayer(players)) {
			System.out.println("Oracle think allin for all players or single player. WINNER.");
			table.setState(TableState.WINNER);
			
			return oracleResponseFactory.createOracleResponseWinner(getWinner());
		}
		
		System.out.println("Oracle think no immediate player win.");
		
		if (conditionPlayersHaveToSpeek(players)) {
			System.out.println("Oracle think players have to speak. OK.");
			return oracleResponseFactory.createOracleResponseOk();
		}
		
		System.out.println("Oracle think all players speaked.");
		
		if (conditionAllAgree(players) || conditionCallToMostOneRaise(players)) {
			System.out.println("Oracle think all players agree OR call.");
			
			table.setNextState();
			
			if (table.getState().equals(TableState.WINNER)) {
				System.out.println(" -> Oracle think this is last step. WINNER.");
				gamePlayersKeeper.resetActions(true);
				
				return oracleResponseFactory.createOracleResponseWinner(getWinner());
			}
			
			System.out.println(" -> Oracle think next step. NEXT_STEP.");
			gamePlayersKeeper.resetActions(false);
			
			return oracleResponseFactory.createOracleResponseNextStep(gamePlayersKeeper.getActivePlayers());
		}
		
		System.out.println("__Oracle think no changes required. OK.");
		
		return oracleResponseFactory.createOracleResponseOk();
	}

	public void update(GameUpdater gameUpdater) {
		table.reset();
		gameUpdater.getTable().forEach(card->table.addCard(card));
		
		for(PlayerData pd : gameUpdater.getPlayers()) {
			Player p = gamePlayersKeeper.getPlayer(pd.getName());
			p.deal(new Pair<>(pd.getCard1(), pd.getCard2()));
			p.setScore(pd.getScore());
		}
	}
	
	private List<Player> getWinner() {
		List<Player> players = gamePlayersKeeper.getPlayers();
		List<Player> winnerList = new ArrayList<Player>();
		
		for (Player p : players) {
			p.evaluateRanking(table.getAllCards());
		}
		
		Player winner = players.get(0);
		Integer winnerRank = winner.getRankingInt();
		winnerList.add(winner);
		
		for (int i = 1; i < players.size(); i++) {
			Player player = players.get(i);
			
			if (player.getAction().equals(Action.FOLD)) {
				continue;
			}
			
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
	
	private boolean conditionAllIn(List<Player> players) {
		return players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.ALLIN));
	}
	
	private boolean conditionSinglePlayer(List<Player> players) {
		return players.stream().filter(p->!p.getAction().equals(Action.FOLD)).count() == 1;
	}
	
	private boolean conditionPlayersHaveToSpeek(List<Player> players) {
		return players.stream().filter(p->p.getAction().equals(Action.NONE)).count() > 0;
	}
	
	private boolean conditionAllAgree(List<Player> players) {
		return players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.CHECK));
	}
	
	private boolean conditionCallToMostOneRaise(List<Player> players) {
		return players.stream().allMatch(p -> p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.RAISE) || p.getAction().equals(Action.CALL)) 
				&& players.stream() .filter(p -> p.getAction().equals(Action.RAISE)).count() <= 1;
	}
}
