package it.unibo.cs.sd.poker.gui.view;

import it.unibo.cs.sd.poker.game.core.Card;
import it.unibo.cs.sd.poker.game.core.Deck;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.players.player.Player;
import breads_and_aces.game.model.utils.Pair;

public class GameViewInitializerGUIDummy implements GameViewInitializer {

	private final GameView view;
	
	private static final int initialCoins = 200;
	private static final int initialScore = 0;
	private static final int initialGoal = 1000;
	private static final String myName = "Anna";
	
	@Inject
	public GameViewInitializerGUIDummy(GameView view) {
		this.view = view;
	}

	@Override
	public void start() {
		// start dummy code
		
		Deck deck = new Deck();
		
		List<Card> tableCards = new ArrayList<Card>();
		
		for (int i=0;i<5;i++) {
			tableCards.add( deck.pop() );
		}
		
		view.initTableCards( tableCards);
		
		List<Player> players = new ArrayList<>();
		
		players.add(new Player(myName, 0));
		players.add(new Player("Bob", 1));
		players.add(new Player("Carl", 2));
		players.add(new Player("David", 3));
		players.add(new Player("Erik", 4));
		players.add(new Player("Fausto", 5));
		players.add(new Player("Gino", 6));
		players.add(new Player("Helen", 7));
		
		players.forEach(
			c->c.deal( new Pair<>(deck.pop(), deck.pop()) )
		);
		
		view.initPlayers(players, myName, initialGoal, initialScore);
		view.initActionsGui(myName, initialCoins, initialScore);
		view.setViewToken(myName);
		
		/*TEST*/
//		view.showPlayersCards();
	}
	
	@Override
	public GameView get() {
		return view;
	}
}
