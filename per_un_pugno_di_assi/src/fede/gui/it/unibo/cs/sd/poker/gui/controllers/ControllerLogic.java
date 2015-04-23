package it.unibo.cs.sd.poker.gui.controllers;

import it.unibo.cs.sd.poker.game.core.Action;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.DealEventException;
import it.unibo.cs.sd.poker.gui.controllers.exceptions.SinglePlayerException;
import it.unibo.cs.sd.poker.gui.view.GameView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.exception.WinnerException;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.table.TableState;
import breads_and_aces.game.model.utils.Pair;

@Singleton
public class ControllerLogic {
	
	private final Game game;
	private final GameView view;
	private final String nodeId;
//	private Action myAction = Action.NONE;
	private ActionLogic actionlogic = ActionLogic.NULL;
	
	@Inject
	public ControllerLogic(Game game, GameView gameView, String nodeId) {
		this.game = game;
		this.view = gameView;
		this.nodeId = nodeId;
	}
	
	/*for recovery*/
	public void setStateLogic(ActionLogic actionlogic) {
		this.actionlogic = actionlogic;
	}
	
	/*for recovery*/
	public ActionLogic getStateLogic() {
		return actionlogic;
	}
	
	/*for recovery*/
	private void setActionLogic(Action m) {
		actionlogic = actionlogic.nextState(m);
	}
	
	/* for gui: possible user input */
	public List<ActionLogic> getLegalActions() {
		return actionlogic.getEdges();
	}
	
//	/*user action*/
//	public Action getMyAction() {
//		return myAction;
//	}
//	
//	/*user action*/
//	private void setMyAction(Action action) {
//		this.myAction = action;
//	}
	
	public boolean updateAction(String fromPlayer, Action action) throws DealEventException, SinglePlayerException {
		if (invalidAction(fromPlayer))
			return false;
		
		String successor = getSuccessor(fromPlayer).getName();
		
		game.getPlayer(fromPlayer).setAction(action);
		setActionLogic(action);
//		setMyAction(action);
		
		game.getPlayer(fromPlayer).sendToken(successor);
		game.getPlayer(successor).receiveToken(fromPlayer);
		view.setViewToken(successor);
		
		if(evaluateLogicModel()) {
			view.addTableCards(game.showCards());
		}
		
		return true;
	}
	
	private boolean invalidAction(String fromPlayer) {
		return nodeId.equals(fromPlayer) && (! game.getMe().hasToken()); 
	}
	
	private Player getSuccessor(String playerId) throws SinglePlayerException {
		Player next;
		
		if (game.getPlayers().size() == 1) { 
			throw new SinglePlayerException();
		}
		
		do {
			next = game.getPlayersKeeper().getNext(playerId);
			
			if (next.getName().equals(playerId)) { 
				throw new SinglePlayerException();
			}
		}
		while (next.getAction().equals(Action.FOLD));
		
		return next;
	}
	
	private boolean evaluateLogicModel() throws DealEventException {
		try {
			if (evaluateTable()) {
				System.out.println("evaluateTable returning TRUE - go to next state");
				
				game.getTable().setNextState();
				game.getPlayersKeeper().resetActions();
				
				if (game.getTable().getState().equals(TableState.WINNER)) {
					throw new WinnerException();
				}
				
				return true;
			}
		} 
		catch (WinnerException e) {
			System.out.println("evaluateLogicModel WinnerException");
			
			List<Player> winners = game.getWinner();
			view.addTableCards(game.showCards());
			view.showPlayersCards();
			view.showWinners(winners);
			
			for (Player p : winners) 
				System.out.println("VINCE " + p.getName() + " con " + p.getRanking().toString());
			
			actionlogic = ActionLogic.NULL;
//			setRefresh();
			
			throw new DealEventException();
		}
		
		return false;
	}
	
	private boolean evaluateTable() throws WinnerException {
		List<Player> players = game.getPlayers();
		
//		Stream<Player> filtered = players.stream().filter(p->!p.getAction().equals(Action.FOLD));
		
	
		if (players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.ALLIN)))
			throw new WinnerException();
		
		if (players.stream().filter(p->!p.getAction().equals(Action.FOLD)).count() == 1)
			throw new WinnerException();
		
		if (players.stream().filter(p->p.getAction().equals(Action.NONE)).count() > 0)
			return false;
		
		if (players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.CHECK)))
			return true;
		
//		if (players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.CALL)) )
//			return true;
		
//		List<Player> foldOrRaise = players.stream().filter(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.RAISE)).collect(Collectors.toList());
		if (players.stream().allMatch(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.RAISE) || p.getAction().equals(Action.CALL))
				&&
				players.stream().filter(p->p.getAction().equals(Action.RAISE)).count() <=1 ) {
//			|| p.getAction().equals(Action.CALL)
			return true;
		}
		
//		List<Player> foldOrRaise = players.stream().filter(p->p.getAction().equals(Action.FOLD) || p.getAction().equals(Action.RAISE)).collect(Collectors.toList());
//		if (foldOrRaise.stream().filter(p->p.getAction().equals(Action.RAISE)).count() <=1 ) {
////			|| p.getAction().equals(Action.CALL)
//			return true;
//		}
		/*
		if (filtered.allMatch(p->p.getAction().equals(Action.ALLIN))) {
			throw new WinnerException();
		}
		
		if (filtered.allMatch(p->p.getAction().equals(Action.CHECK))) {
			return true;
		}
		
		if (filtered.filter(p->p.getAction().equals(Action.RAISE)).count() <= 1) {
			Stream<Player> filteredNoRaise = filtered.filter(p->!p.getAction().equals(Action.RAISE));
			
			if (filteredNoRaise.allMatch(p->p.getAction().equals(Action.CALL))) {
				return true;
			}
		}
		*/
		
		return false;
	}

	public void update(GameUpdater gameUpdater) {
		game.getTable().reset();
		
		gameUpdater.getTable().forEach(card->{
			game.getTable().addCards(card);
			System.out.println("Update table: " + card);
		});
		
		for(PlayerElements pe : gameUpdater.getPlayers()) {
			Player p = game.getPlayersKeeper().getPlayer(pe.getName());
			
			if (p != null) {
				p.deal(new Pair<>(pe.getCard1(), pe.getCard2()));
				p.setScore(pe.getScore());
			}
		}
		
		System.out.println("Update my cards: " + game.getMe().getCards().get(0) + " " +  game.getMe().getCards().get(1));
		
		view.setRefresh();
	}
}
