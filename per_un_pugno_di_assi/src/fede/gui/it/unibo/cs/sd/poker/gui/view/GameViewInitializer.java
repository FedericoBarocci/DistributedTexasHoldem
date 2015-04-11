package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Deck;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.GamePlayersKeeper;
import breads_and_aces.game.model.players.player.Player;

public class GameViewInitializer {
	
	private final GameView view;
	private final GamePlayersKeeper playersKeeper;

	@Inject
	public GameViewInitializer(GameView view, GamePlayersKeeper playersKeeper) {
		this.view = view;
		this.playersKeeper = playersKeeper;
	}
	
	public void start() {
//		view.create();
		
		//TODO pass PlayersKeeper values
//		view.populatePlayers( Collections.emptyList() );
		List<Player> players = 
				//playersKeeper.getPlayers();
				new ArrayList<>();
		players.add(new Player("Pippo"));
		players.add(new Player("Ciccio"));
		players.add(new Player("3"));
		players.add(new Player("4"));
		players.add(new Player("5"));
		players.add(new Player("6"));
		players.add(new Player("7"));
		players.add(new Player("7bis"));
		Deck deck = new Deck();
		players.forEach(c->c.deal(deck));
		view.init( players );
	}
}
