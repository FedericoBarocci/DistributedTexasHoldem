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
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.MeProvider;
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
	
	private boolean refresh = false;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	@Inject
	public ControllerLogic(Game game, GameView gameView, GamePlayersKeeper gamePlayersKeeper, MeProvider meProvider) {
		this.game = game;
		this.view = gameView;
		this.gamePlayersKeeper = gamePlayersKeeper;
		this.nodeId = meProvider.getMe();
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
		
		gamePlayersKeeper.getPlayer(fromPlayer).setAction(action);
		setActionLogic(action);
//		setMyAction(action);
		
		gamePlayersKeeper.getPlayer(fromPlayer).sendToken(successor);
		gamePlayersKeeper.getPlayer(successor).receiveToken(fromPlayer);
		view.setViewToken(successor);
		
		if(evaluateLogicModel()) {
			view.addTableCards(game.showCards());
		}
		
		return true;
	}
	
	private boolean invalidAction(String fromPlayer) {
		return nodeId.equals(fromPlayer) && (! gamePlayersKeeper.getMyPlayer().hasToken()); 
	}
	
	private Player getSuccessor(String playerId) throws SinglePlayerException {
		Player next;
		
		if (gamePlayersKeeper.getPlayers().size() == 1) { 
			throw new SinglePlayerException();
		}
		
		do {
			next = gamePlayersKeeper.getNext(playerId);
			
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
				gamePlayersKeeper.resetActions();
				
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
		List<Player> players = gamePlayersKeeper.getPlayers();
		
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
		
		for(PlayerData pd : gameUpdater.getPlayers()) {
			Player p = gamePlayersKeeper.getPlayer(pd.getName());
			
			if (p != null) {
				p.deal(new Pair<>(pd.getCard1(), pd.getCard2()));
				p.setScore(pd.getScore());
			}
		}
		
		System.out.println("Update my cards: " + gamePlayersKeeper.getMyPlayer().getCards().get(0) + " " +  gamePlayersKeeper.getMyPlayer().getCards().get(1));
		
		setRefresh();
	}

	public void refresh(/*List<Player> players, String myName, int goal*/) {
//		System.out.println("REFRESHING GAME VIEW");
//		view.initTableCards();
////		initPlayers(players, myName, goal);
//		view.initPlayers(game.getPlayers(), nodeId, game.getGoal());
//		
//		viframe.repaint();
		
		view.refresh(gamePlayersKeeper.getPlayers(), nodeId, game.getGoal());
		refresh = false;
	}
	
	public boolean isSetRefresh() {
		System.out.println("isSetRefresh " + refresh);
		return refresh;
	}

	private void setRefresh() {
		System.out.println("now setRefresh is true");
		refresh = true;		
	}
	
	public void handleToken() {
		Player player = gamePlayersKeeper.getPlayer(nodeId);
		player.receiveToken();

		System.out.println("Ho ricevuto il token");

		view.setViewToken(player.getName());
	}
}
