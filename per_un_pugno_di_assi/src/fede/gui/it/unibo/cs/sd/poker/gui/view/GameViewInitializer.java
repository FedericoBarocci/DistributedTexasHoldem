package it.unibo.cs.sd.poker.gui.view;

import java.util.List;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;
import breads_and_aces.game.model.players.player.Player;

public class GameViewInitializer {
	
	private final GameView view;
	private final PlayersKeeper playersKeeper;

	@Inject
	public GameViewInitializer(/*Model model,*/ GameView view, PlayersKeeper playersKeeper) {
		this.view = view;
		this.playersKeeper = playersKeeper;
	}
	
	public void start() {
		view.create();
		
		//TODO pass PlayersKeeper values
//		view.populatePlayers( Collections.emptyList() );
		List<Player> players = playersKeeper.getPlayers();
//		players.forEach(c->c.);
		view.populatePlayers( playersKeeper.getPlayers() );
	}
}
