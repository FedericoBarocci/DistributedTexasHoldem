package it.unibo.cs.sd.poker.gui.view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;

@Singleton
public class GameViewInitializerReal implements GameViewInitializer {

	private final GameView view;
	private final Game game;
	private final GamePlayersKeeper gamePlayersKeeper;
	
	private static final int initialCoins = 200;
	private static final int initialScore = 0;
	private static final int initialGoal = 1000;
	
	@Inject
	public GameViewInitializerReal(GameView view, Game game, GamePlayersKeeper gamePlayersKeeper) {
		this.view = view;
		this.game = game;
		this.gamePlayersKeeper = gamePlayersKeeper;
	}

	@Override
	public void start() {
		List<Player> players = gamePlayersKeeper.getPlayers();
//		gamePlayersKeeper.setMyName(myName);
		game.setGoal(initialGoal);
		
		for (Player p : players) { 
			p.setScore(initialScore);
		}
		
		view.initPlayers(players, gamePlayersKeeper.getMyName(), initialGoal);
//		view.initTableCards();
		view.initActionsGui(gamePlayersKeeper.getMyName(), initialCoins, initialScore);
		view.start();
	}

	@Override
	public GameView get() {
		return view;
	}
}
