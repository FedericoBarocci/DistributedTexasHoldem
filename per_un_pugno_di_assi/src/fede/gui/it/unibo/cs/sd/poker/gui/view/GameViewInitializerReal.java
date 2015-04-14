package it.unibo.cs.sd.poker.gui.view;

import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.Game;
import breads_and_aces.game.model.players.player.Player;

public class GameViewInitializerReal implements GameViewInitializer {

	private final GameView view;
	private final Game game;

	@Inject
	public GameViewInitializerReal(GameView view, Game game) {
		this.view = view;
		this.game = game;
//		this.playersKeeper = playersKeeper;
	}

	@Override
	public void start() {
		List<Player> players = game.getPlayersKeeper().getPlayers(); // business code
		view.initPlayers(players);
		view.initTableCards(game.getTable().getCards());
		view.initActionsGui("Player Name", 200, 0); ///*, players.get(0).getCards()*/);
//		view.init( players );
	}
}
