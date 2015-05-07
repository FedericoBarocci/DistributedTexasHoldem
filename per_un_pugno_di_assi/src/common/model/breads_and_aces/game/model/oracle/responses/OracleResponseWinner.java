package breads_and_aces.game.model.oracle.responses;

import java.util.List;

import breads_and_aces.game.model.controller.Communication;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.state.ActionsLogic;
import breads_and_aces.game.model.state.GameState;
import breads_and_aces.gui.view.ViewControllerDelegate;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class OracleResponseWinner implements OracleResponse {

	private final List<Player> winners;
	private final GameState gameState;
	private final ViewControllerDelegate viewControllerDelegate;

	@AssistedInject
	public OracleResponseWinner(ViewControllerDelegate viewControllerDelegate, @Assisted List<Player> winners, GameState gameState) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.winners = winners;
		this.gameState = gameState;
	}

	@Override
	public Communication exec() {
		viewControllerDelegate.setRefresh();
		viewControllerDelegate.showDown(winners);
		
		for (Player p : winners) {
			p.setScore(p.getScore() + 50);
//			System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
		}
		
		gameState.setGameState(ActionsLogic.NULL);
		
		return Communication.DEAL;
	}
	
	@Override
	public String toString() {
		return "Winner";
	}
}
