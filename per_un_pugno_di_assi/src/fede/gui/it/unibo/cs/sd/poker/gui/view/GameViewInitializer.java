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
=======
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
>>>>>>> 1908d7383742a90b21939ad26ac5b0032e9da80a
		Deck deck = new Deck();
		Pair<Card> cards = new Pair<>(deck.pop(), deck.pop());
		players.forEach(c->c.deal(cards));*/
		// end dummy code
		
		view.init( players );
	}
}
