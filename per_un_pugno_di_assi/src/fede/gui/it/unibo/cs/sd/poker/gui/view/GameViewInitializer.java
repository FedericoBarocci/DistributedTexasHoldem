package it.unibo.cs.sd.poker.gui.view;

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
				playersKeeper.getPlayers(); // business code
		// start dummy code
				/*new ArrayList<>();
		players.add(new Player("Pippo"));
		players.add(new Player("Ciccio"));
		players.add(new Player("3"));
		players.add(new Player("4"));
		players.add(new Player("5"));
		players.add(new Player("6"));
		players.add(new Player("7"));
		players.add(new Player("7bis"));
		Deck deck = new Deck();
		Pair<Card> cards = new Pair<>(deck.pop(), deck.pop());
		players.forEach(c->c.deal(cards));*/
		// end dummy code
		
		
		view.init( players );
	}
}
