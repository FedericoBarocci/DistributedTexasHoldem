package bread_and_aces.game.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import bread_and_aces.game.Game;
import bread_and_aces.game.exceptions.MaxReachedException;
import bread_and_aces.game.exceptions.NegativeIntegerException;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.ActionsLogic;
import bread_and_aces.game.model.state.GameState;

@Singleton
public class BetManager {

	private final GameState gameState;
	private final Game game;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	private BoundInteger betValue;
	private ActionsLogic currentAction;
	private int tablebet = 0;
	private int mybet = 0;

	@Inject
	public BetManager(GamePlayersKeeper gamePlayersKeeper, GameState gameState, Game game) {
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.gameState = gameState;
		this.game = game;
	}

	public void init() {
		betValue = new BoundInteger(0, 0, game.getCoins());
		currentAction = ActionsLogic.NULL;
		tablebet = 0;
		mybet = 0;
	}
	
	public int bet(int i) {
		int res;
		
		try {
			res = betValue.add(i);
			currentAction = gameState.getGameState().getBetState();
		} 
		catch (MaxReachedException e1) {
			res = betValue.getValue();
			currentAction = gameState.getGameState().getMaxBetState();
		}
		
		return res;
	}
	
	public int unbet(int i) {
		int res;
		
		try {
			res = betValue.subtract(i);
			currentAction = gameState.getGameState().getBetState();
		} 
		catch (NegativeIntegerException e1) {
			res = betValue.getValue();
			currentAction = gameState.getGameState().getMinBetState();
		}
		
		return res;
	}
	
	public void setAction(Action action) {
		currentAction = action.getGameState().getMinBetState();
	}
	
	public BoundInteger getBet() {
		return betValue;
	}
	
	public void setBet(int value) {
		betValue.setValue(value);
	}
	
	public void setMin(int minbet) {
		betValue.setMin(minbet);
	}
	
	public int getMax() {
		return betValue.getMax();
	}
	
	public void setMax(int max) {
		betValue.setMax(max);
	}

	public ActionKeeper getActionKeeper() {
		return new ActionKeeper(currentAction.getAction(), betValue.getValue());
	}
	
	public void savebet() {
		tablebet = getSumAllPot();
		mybet += betValue.getValue();
//		System.out.println("HO SALVATO BET" + tablebet);
	}

	public int getSumAllPot() {
		int sum = tablebet;
		
		for (Player p : gamePlayersKeeper.getPlayers()) {
			sum += p.getBet();
		}
		
		return sum;
	}
	
	public String toString() {
		return betValue.toString() + " - " + currentAction.toString();
	}
	
	public int getMyBet() {
		return mybet;
	}

}
