package breads_and_aces.gui.view;


import java.util.List;

import javax.inject.Inject;

import org.limewire.inject.LazySingleton;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.gui.view.PlayersViewHandler.PlayersViewHandlerInitArgs;
import breads_and_aces.gui.view.ViewInitalizer.ViewInitializerInitArgs;

@LazySingleton
//@Singleton
public class ViewControllerDelegate {

	private final ViewInitalizer viewInitializer;
	private final TableViewHandler tableView;
	private final PlayersViewHandler playersView;
	private final ButtonsViewHandler buttonsViewHandler;
	
	private boolean refresh = false;
	private int goal;

	@Inject
	public ViewControllerDelegate(ViewInitalizer viewInitializer, TableViewHandler tableView, PlayersViewHandler playersView, ButtonsViewHandler buttonsViewHandler) {
		this.viewInitializer = viewInitializer;
		this.tableView = tableView;
		this.playersView = playersView;
		this.buttonsViewHandler = buttonsViewHandler;
	}

//	public void init(InitArgs args) {
//		this.goal = args.goal;
//		
//		refresh(args.players, args.myName);
//		viewInitializer.init(args.myName, args.initialCoins);
//	}
	public void init(List<Player> players, String myName, int goal, int initialCoins) {		
		this.goal = goal;
		
		refresh(players, myName);
		ViewInitializerInitArgs viewInitializerInitArgs = new ViewInitializerInitArgs(myName, initialCoins, goal);
		
		buttonsViewHandler.init(null);
		viewInitializer.init(viewInitializerInitArgs);
		viewInitializer.printMessage("Let's start the game!");
	}

	public void setRefresh() {
		refresh = true;
	}
	
	public boolean isSetRefresh() {
		return refresh;
	}
	
	public void refresh(List<Player> players, String myName) {
		tableView.init(null);
//		playersView.init(players, myName, goal);
		PlayersViewHandlerInitArgs initArgs = new PlayersViewHandlerInitArgs(players, myName, goal);
		playersView.init( initArgs );
		refresh = false;
	}

	public void setViewToken(String playerName) {
		if (isSetRefresh()) { return; }
		
		playersView.setViewToken(playerName);
		buttonsViewHandler.enableButtons(playerName);
	}

	public void enableButtons(boolean hasToken) {
		buttonsViewHandler.enableButtons(hasToken);
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
		viewInitializer.printMessage(winnersString);
		buttonsViewHandler.enableButtons();
	}

	public void setPlayerAction(String fromPlayer, Action action) {
		playersView.setPlayerAction(fromPlayer, action);
		viewInitializer.printMessage(fromPlayer + " perform " + action.toString() + " action.");
	}

	public void endGame(String player) {
		playersView.showWinnerId(player);
		viewInitializer.printMessage("Hands up! The winner is " + player);
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

	public void remove(String playerId) {
		playersView.removeElement(playerId);
	}
}
