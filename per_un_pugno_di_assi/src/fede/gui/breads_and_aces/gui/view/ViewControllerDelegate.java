package breads_and_aces.gui.view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.model.players.player.Player;

@Singleton
public class ViewControllerDelegate {

	private final ViewInitalizer viewInitializer;
	private final TableViewHandler tableView;
	private final PlayersViewHandler playersView;
	
	private boolean refresh = false;
	private int goal;

	@Inject
	public ViewControllerDelegate(ViewInitalizer viewInitializer, TableViewHandler tableView, PlayersViewHandler playersView) {
		this.viewInitializer = viewInitializer;
		this.tableView = tableView;
		this.playersView = playersView;
	}

	public void init(List<Player> players, String myName, int goal, int initialCoins) {
		this.goal = goal;
		
		refresh(players, myName);
		viewInitializer.init(myName, initialCoins);
	}

	public void setRefresh() {
		refresh = true;
	}
	
	public boolean isSetRefresh() {
		return refresh;
	}
	
	public void refresh(List<Player> players, String myName) {
		tableView.init();
		playersView.init(players, myName, goal);
		refresh = false;
	}

	public void setViewToken(String playerName) {
		playersView.setViewToken(playerName);
	}

	public void addTableCards() {
		tableView.addTableCards();
	}

	public void showDown(List<Player> winners) {
		tableView.addTableCards();
		playersView.showPlayersCards();
		playersView.showWinners(winners);
	}
}
