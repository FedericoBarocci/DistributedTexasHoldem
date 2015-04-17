package it.unibo.cs.sd.poker.gui.view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.main.Main;

@Singleton
public class GameViewInitializerReal implements GameViewInitializer {

	private final GameView view;
	private final Game game;
	
	private static final int initialCoins = 200;
	private static final int initialScore = 0;
	private static final int initialGoal = 1000;
	
	private final String myName;

	@Inject
	public GameViewInitializerReal(GameView view, Game game) {
		this.view = view;
		this.game = game;
		this.myName = Main.nodeid;
		System.out.println("GameViewInitializerReal: "+myName);
//		this.playersKeeper = playersKeeper;
	}

	@Override
	public void start() {
		List<Player> players = game.getPlayersKeeper().getPlayers(); // business code
		view.initPlayers(players, myName, initialGoal, initialScore);
		view.initTableCards(game.getTable().getCards());
		view.initActionsGui(myName, initialCoins, initialScore); ///*, players.get(0).getCards()*/);
//		view.init( players );
	}
	
	@Override
	public GameView get() {
		return view;
	}
}
