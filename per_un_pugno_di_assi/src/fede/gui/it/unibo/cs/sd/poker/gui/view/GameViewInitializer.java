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
		players.add(new Player("Anna"));
		players.add(new Player("Bob"));
		players.add(new Player("Carl"));
		players.add(new Player("David"));
		players.add(new Player("Erik"));
		players.add(new Player("Fausto"));
		players.add(new Player("Gino"));
		players.add(new Player("Helen"));
		Deck deck = new Deck();
		players.forEach(c->c.deal(deck));
		view.init( players );
	}
}
