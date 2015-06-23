package bread_and_aces.game.model.oracle.responses;

import java.util.List;

import bread_and_aces.game.Game;
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
	private final Game game;

	@AssistedInject
	public OracleResponseWinner(ViewControllerDelegate viewControllerDelegate,
			@Assisted List<Player> winners, GameState gameState,
			BetManager betManager, LabelHandler labelHandler,
			GamePlayersKeeper gamePlayersKeeper, ButtonsViewHandler buttonsViewHandler,
			Game game) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.winners = winners;
		this.gameState = gameState;
		this.betManager = betManager;
		this.labelHandler = labelHandler;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.buttonsViewHandler = buttonsViewHandler;
		this.game = game;
	}

	@Override
	public Communication exec() {
		Communication result = Communication.DEAL;
		
		viewControllerDelegate.setRefresh();
		viewControllerDelegate.showDown(winners);
		
		gamePlayersKeeper.getPlayers().forEach(p->p.collectBet());
		
		for (Player p : gamePlayersKeeper.getPlayers()) {
			int score = p.getScore() - p.getTotalBet();
			
			if (winners.contains(p)) {
				score += Math.floorDiv(betManager.getSumAllPot(), winners.size());
				System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
			}

//			System.out.println("SCORE::" + p.getName() + score);
			
			if (score < 0) {
				score = 0;
			}
			else if (score >= game.getGoal()) {
				score = game.getGoal();
				result = Communication.END;
				viewControllerDelegate.endGame(p.getName());
			}
			
			p.setScore(score);
			
			if (gamePlayersKeeper.getMyPlayer().equals(p)) {
				labelHandler.setScore(score);
			}
		}
		
		gamePlayersKeeper.getPlayers().forEach(p->p.initBet());
		
		gameState.reset();
		betManager.init();
		labelHandler.init();
		buttonsViewHandler.updateText("PLAY", "EXIT");
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Winner";
	}
}
