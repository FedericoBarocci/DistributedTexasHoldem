package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.oracle.actions.Action;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.gui.view.ViewControllerDelegate;
import breads_and_aces.game.core.BetManager;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class OracleResponseNextStep implements OracleResponse {

	private final List<Player> players;
	private final ViewControllerDelegate viewControllerDelegate;
	private final GameState gameState;
	private final BetManager betManager;

	@AssistedInject
	public OracleResponseNextStep(ViewControllerDelegate viewControllerDelegate, @Assisted List<Player> players, GameState gameState, BetManager betManager) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.players = players;
		this.gameState = gameState;
		this.betManager = betManager;
	}

	@Override
	public Communication exec() {
		viewControllerDelegate.addTableCards(players);
		viewControllerDelegate.resetViewState(gameState);
		players.forEach(p->p.initBet());
		gameState.reset();
		betManager.setMin(0);
		betManager.setAction(Action.NONE);
		
		return Communication.ACTION;
	}
	
	@Override
	public String toString() {
		return "Next Step";
	}
}
