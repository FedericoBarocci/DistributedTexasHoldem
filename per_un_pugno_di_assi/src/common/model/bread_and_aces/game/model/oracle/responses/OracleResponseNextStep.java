package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.gui.view.ViewControllerDelegate;

public class OracleResponseNextStep implements OracleResponse {

	private final List<Player> players;
	private final ViewControllerDelegate viewControllerDelegate;

	@AssistedInject
	public OracleResponseNextStep(ViewControllerDelegate viewControllerDelegate, @Assisted List<Player> players) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.players = players;
	}

	@Override
	public Communication exec() {
		viewControllerDelegate.addTableCards(players);
		return Communication.ACTION;
	}
	
	@Override
	public String toString() {
		return "Next Step";
	}
}
