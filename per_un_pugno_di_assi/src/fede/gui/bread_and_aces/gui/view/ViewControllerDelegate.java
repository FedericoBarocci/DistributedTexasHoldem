package bread_and_aces.gui.view;


import java.util.List;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.oracle.actions.ActionKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.gui.view.PlayersViewHandler.PlayersViewHandlerInitArgs;
import bread_and_aces.gui.view.ViewInitalizer.ViewInitializerInitArgs;

@LazySingleton
// @Singleton
public class ViewControllerDelegate {

	private final ViewInitalizer viewInitializer;
	private final TableViewHandler tableView;
	private final PlayersViewHandler playersView;
	private final ButtonsViewHandler buttonsViewHandler;
	private final BetManager betManager;
	private final LabelHandler labelHandler;
	private final GameState gameState;

	private boolean refresh = false;
	private int goal;

	@Inject
	public ViewControllerDelegate(ViewInitalizer viewInitializer,
			TableViewHandler tableView, PlayersViewHandler playersView,
			ButtonsViewHandler buttonsViewHandler, BetManager betManager, LabelHandler labelHandler,
			GameState gameState) {
		this.viewInitializer = viewInitializer;
		this.tableView = tableView;
		this.playersView = playersView;
		this.buttonsViewHandler = buttonsViewHandler;
		this.betManager = betManager;
		this.labelHandler = labelHandler;
		this.gameState = gameState;
	}

	public void init(List<Player> players, String myName, int goal, int initialCoins) {
		this.goal = goal;

		refresh(players, myName);
		ViewInitializerInitArgs viewInitializerInitArgs = new ViewInitializerInitArgs(myName, initialCoins, goal);

		buttonsViewHandler.init(null);
		viewInitializer.init(viewInitializerInitArgs);
		labelHandler.init();
		labelHandler.printMessage("Let's start the game!");
	}

	public void setRefresh() {
		refresh = true;
	}

	public boolean isSetRefresh() {
		return refresh;
	}

	public void refresh(List<Player> players, String myName) {
		tableView.init(null);
		
		PlayersViewHandlerInitArgs initArgs = new PlayersViewHandlerInitArgs(players, myName, goal);
		
		playersView.init(initArgs);
		refresh = false;
	}

	public void setViewToken(String playerName) {
		if (isSetRefresh()) {
			return;
		}

		playersView.setViewToken(playerName);
		buttonsViewHandler.enableButtons(playerName);
	}

	public void enableButtons(boolean hasToken) {
		buttonsViewHandler.enableButtons(hasToken);
		buttonsViewHandler.resetText();
	}

	public void addTableCards(List<Player> players) {
		tableView.addTableCards();
		playersView.resetActions(players);
	}

	public void showDown(List<Player> winners) {
		tableView.addTableCards();
		playersView.showPlayersCards();
		playersView.showWinners(winners);

		String winnersString = winners.get(0).getName();

		for (int i = 1; i < winners.size(); i++) {
			winnersString += ", " + winners.get(i).getName();
		}

		winnersString += " win the hand!";
		
		labelHandler.printMessage(winnersString);
		buttonsViewHandler.enableButtons();
	}

	public void setPlayerAction(String fromPlayer, ActionKeeper action) {
		playersView.setPlayerAction(fromPlayer, action.getAction());
		labelHandler.printMessage(fromPlayer + " perform " + action.toString() + " action.");
	}

	public void endGame(String player) {
		playersView.showWinnerId(player);
		labelHandler.printMessage("Hands up! The winner is " + player);
	}

	public void remove(String playerId) {
		playersView.removeElement(playerId);
	}

	public void setViewState(ActionKeeper actionKeeper) {
		betManager.setBet(gameState.getMinBet());
		betManager.setMin(gameState.getMinBet());
		betManager.setAction();
		
		labelHandler.setValue(betManager.getSumAllPot(), gameState.getMinBet());
		
		buttonsViewHandler.updateText(betManager.getActionKeeper().getAction());
		//buttonsViewHandler.updateText(gameState.getGameState().getAction());
	}
	
	public void resetViewState() {
//		betManager.savebet();
		int coins = labelHandler.collectCoins(gameState.getMinBet());
		
		betManager.setMax(coins);
		betManager.setMin(0);
		betManager.setBet(0);
		
		buttonsViewHandler.updateText(Action.CHECK);
	}

	static class InitArgs {
		List<Player> players;
		String myName;
		int goal;
		int initialCoins;

		public InitArgs(List<Player> players, String myName, int goal, int initialCoins) {
			this.players = players;
			this.myName = myName;
			this.goal = goal;
			this.initialCoins = initialCoins;
		}
	}
}
