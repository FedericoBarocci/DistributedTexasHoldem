package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.gui.view.ButtonsViewHandler;
import bread_and_aces.gui.view.LabelHandler;
import bread_and_aces.gui.view.ViewControllerDelegate;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class OracleResponseWinner implements OracleResponse {

	private final List<Player> winners;
	private final GameState gameState;
	private final ViewControllerDelegate viewControllerDelegate;
	private final BetManager betManager;
	private final LabelHandler labelHandler;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final ButtonsViewHandler buttonsViewHandler;

	@AssistedInject
	public OracleResponseWinner(ViewControllerDelegate viewControllerDelegate,
			@Assisted List<Player> winners, GameState gameState,
			BetManager betManager, LabelHandler labelHandler,
			GamePlayersKeeper gamePlayersKeeper, ButtonsViewHandler buttonsViewHandler) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.winners = winners;
		this.gameState = gameState;
		this.betManager = betManager;
		this.labelHandler = labelHandler;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.buttonsViewHandler = buttonsViewHandler;
	}

	@Override
	public Communication exec() {
		viewControllerDelegate.setRefresh();
		viewControllerDelegate.showDown(winners);
		
		gamePlayersKeeper.getPlayers().forEach(p->{
			int score = p.getScore() - labelHandler.getCoins();
			
			if (winners.contains(p)) {
				score += Math.floorDiv(betManager.getSumAllPot(), winners.size());
				System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
			}
//			else if (gamePlayersKeeper.getActivePlayers().contains(p)) {
//				score -= labelHandler.getCoins();
//			}

			System.out.println("SCORE::" + p.getName() + score);

			p.setScore(score);
			p.initBet();
			
			if (gamePlayersKeeper.getMyPlayer().equals(p)) {
				labelHandler.setScore(score);
			}
		});
		
		gameState.reset();
		betManager.init();
		labelHandler.init();
		buttonsViewHandler.updateText("PLAY", "EXIT");
		
		return Communication.DEAL;
	}
	
	@Override
	public String toString() {
		return "Winner";
	}
}
