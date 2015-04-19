package it.unibo.cs.sd.poker.gui.view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.player.Player;

@Singleton
public class GameViewInitializerReal implements GameViewInitializer {

	private final GameView view;
	private final Game game;
	
	private static final int initialCoins = 200;
	private static final int initialScore = 0;
	private static final int initialGoal = 1000;
	
	@Inject
	public GameViewInitializerReal(GameView view, Game game) {
		this.view = view;
		this.game = game;
	}

	@Override
	public void start(String myName) {
		List<Player> players = game.getPlayersKeeper().getPlayers();
		game.getPlayersKeeper().setMe(myName);
		game.setGoal(initialGoal);
		
		for (Player p : players) { 
			p.setScore(initialScore);
		}
		
		view.initPlayers(players, myName, initialGoal);
//		view.initTableCards();
		view.initActionsGui(myName, initialCoins, initialScore);
		view.start();
	}

	@Override
	public GameView get() {
		return view;
	}
}
