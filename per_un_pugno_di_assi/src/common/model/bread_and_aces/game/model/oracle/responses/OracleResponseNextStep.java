package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.gui.view.ViewControllerDelegate;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class OracleResponseNextStep implements OracleResponse {

	private final List<Player> players;
	private final ViewControllerDelegate viewControllerDelegate;
	private final GameState gameState;
	private final BetManager betManager;
	private final Table table;
	private final GamePlayersKeeper gamePlayersKeeper;

	@AssistedInject
	public OracleResponseNextStep(
			ViewControllerDelegate viewControllerDelegate,
			@Assisted List<Player> players, GameState gameState,
			BetManager betManager, Table table,
			GamePlayersKeeper gamePlayersKeeper) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.players = players;
		this.gameState = gameState;
		this.betManager = betManager;
		this.table = table;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}

	@Override
	public Communication exec() {
		return Communication.ACTION;
	}

	@Override
	public String toString() {
		return "Next Step";
	}

	@Override
	public void complete() {
		table.setNextState();
		gamePlayersKeeper.resetActions(false);

		viewControllerDelegate.addTableCards(players);
		viewControllerDelegate.resetViewState();

		players.forEach(p -> p.collectBet());

		gameState.reset();

		betManager.setMin(0);
		betManager.setAction();
	}
}
