package it.unibo.cs.sd.poker.gui.controllers;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
import it.unibo.cs.sd.poker.gui.view.GameView;

import java.util.List;

import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.exception.WinnerException;
import breads_and_aces.game.model.players.player.Player;

@Singleton
public class ControllerLogic {
	
	private Game game;
	private GameView view;
	private String nodeId;
	
	public ControllerLogic(Game game, GameView gameView, String nodeId) {
		this.game = game;
		this.view = gameView;
		this.nodeId = nodeId;
	}
	
	public boolean check(String fromPlayer, String toPlayer) throws DealEventException {
		if (invalidAction(fromPlayer))
			return false;
		
		game.getPlayer(fromPlayer).setAction(Action.CHECK);
		game.getPlayer(fromPlayer).sendToken(toPlayer);
		game.getPlayer(toPlayer).receiveToken(fromPlayer);
		view.setViewToken(toPlayer);
		
		try {
			if(game.evaluateLogicModel()) {
				view.addTableCards(game.showCards());
			}
		} 
		catch (WinnerException e) {
			List<Player> winners = game.getWinner();
			view.addTableCards(game.showCards());
			view.showPlayersCards();
			view.showWinners(winners);
			
			for (Player p : winners) 
				System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
			
			view.setRefresh();
			
			throw new DealEventException();
		}
		
		return true;
	}
	
	private boolean invalidAction(String fromPlayer) {
		return nodeId.equals(fromPlayer) && (! game.getMe().hasToken()); 
	}
}
