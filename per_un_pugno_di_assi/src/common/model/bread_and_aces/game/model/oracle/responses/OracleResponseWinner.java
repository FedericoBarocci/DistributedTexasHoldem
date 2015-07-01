package bread_and_aces.game.model.oracle.responses;

import java.util.ArrayList;
import java.util.List;

import bread_and_aces.game.Game;
import bread_and_aces.game.core.BetManager;
import bread_and_aces.game.model.controller.Communication;
import bread_and_aces.game.model.players.keeper.GamePlayersKeeper;
import bread_and_aces.game.model.players.player.Player;
import bread_and_aces.game.model.state.GameState;
import bread_and_aces.game.model.table.Table;
import bread_and_aces.game.model.table.TableState;
import bread_and_aces.gui.view.ButtonsViewHandler;
import bread_and_aces.gui.view.LabelHandler;
import bread_and_aces.gui.view.ViewControllerDelegate;
import bread_and_aces.utils.DevPrinter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class OracleResponseWinner implements OracleResponse {

	private static final int fixedWinnerBonus = 50;
	
	private final List<Player> winners;
	private final GameState gameState;
	private final ViewControllerDelegate viewControllerDelegate;
	private final BetManager betManager;
	private final LabelHandler labelHandler;
	private final GamePlayersKeeper gamePlayersKeeper;
	private final ButtonsViewHandler buttonsViewHandler;
	private final Game game;
	private final Table table;

	@AssistedInject
	public OracleResponseWinner(ViewControllerDelegate viewControllerDelegate,
			@Assisted List<Player> winners, GameState gameState,
			BetManager betManager, LabelHandler labelHandler,
			GamePlayersKeeper gamePlayersKeeper, ButtonsViewHandler buttonsViewHandler,
			Game game, Table table) {
		this.viewControllerDelegate = viewControllerDelegate;
		this.winners = winners;
		this.gameState = gameState;
		this.betManager = betManager;
		this.labelHandler = labelHandler;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.buttonsViewHandler = buttonsViewHandler;
		this.game = game;
		this.table = table;
	}

	@Override
	public Communication exec() {
		Communication result = Communication.DEAL;
		
		if (gamePlayersKeeper.getPlayers().size() == 1) {
			return Communication.END;
		}
		
		for (Player p : gamePlayersKeeper.getPlayers()) {
			int score = p.getScore() - p.getTotalBet() - p.getBet();
			
			if (winners.contains(p)) {
				score += Math.floorDiv(betManager.getSumAllPot(), winners.size()) + fixedWinnerBonus;
			}

			DevPrinter.println("SCORE::" + p.getName() + " new score " + score);
			
			if (score >= game.getGoal()) {
				result = Communication.END;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Winner";
	}

	@Override
	public void finaly() {
		List<Player> winnersEndGame = new ArrayList<Player>();
		boolean endgame = gamePlayersKeeper.getPlayers().size() == 1;
		
		table.setState(TableState.WINNER);
		viewControllerDelegate.setRefresh();
		gamePlayersKeeper.getPlayers().forEach(p->p.collectBet());
		
		if (endgame) {
			viewControllerDelegate.endGame(gamePlayersKeeper.getPlayers().get(0).getName());
			viewControllerDelegate.enableButtons(false);
			labelHandler.init();
			
			return;
		}
		
		for (Player p : gamePlayersKeeper.getPlayers()) {
			int score = p.getScore() - p.getTotalBet();
			
			if (winners.contains(p)) {
				score += Math.floorDiv(betManager.getSumAllPot(), winners.size()) + fixedWinnerBonus;
				DevPrinter.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
			}

			DevPrinter.println("SCORE::" + p.getName() + " new score " + score + " / " + game.getGoal());
			
			if (score < 0) {
				score = 0;
			}
			else if (score >= game.getGoal()) {
				DevPrinter.println("endGame::" + p.getName() + " new score " + score);
				
				endgame = true;
				score = game.getGoal();
				winnersEndGame.add(p);
				game.stop();
				
				//viewControllerDelegate.endGame(p.getName());
			}
			
			p.setScore(score);
			
			if (gamePlayersKeeper.getMyPlayer().equals(p)) {
				labelHandler.setScore(score);
			}
		}
		
		if (endgame) {
			viewControllerDelegate.endGamePlayers(winnersEndGame);
			buttonsViewHandler.enableButtons(false);
			labelHandler.init();
		}
		else {
			viewControllerDelegate.showDown(winners);
			
			gamePlayersKeeper.getPlayers().forEach(p->p.initBet());
			gamePlayersKeeper.resetActions(true);
			
			gameState.reset();
			betManager.init();
			labelHandler.init();
			buttonsViewHandler.updateText("PLAY", "EXIT");
		}
	}
}
