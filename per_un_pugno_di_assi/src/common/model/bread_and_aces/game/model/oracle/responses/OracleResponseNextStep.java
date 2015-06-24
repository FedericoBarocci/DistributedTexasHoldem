package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.gui.view.ViewControllerDelegate;

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
		viewControllerDelegate.resetViewState();
		
		players.forEach(p->p.collectBet());
		
		gameState.reset();
		
		betManager.setMin(0);
		betManager.setAction();
		
		return Communication.ACTION;
	}
	
	@Override
	public String toString() {
		return "Next Step";
	}
}
