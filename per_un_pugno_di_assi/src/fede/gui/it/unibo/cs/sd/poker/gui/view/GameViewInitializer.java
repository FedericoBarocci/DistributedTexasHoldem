package it.unibo.cs.sd.poker.gui.view;

import javax.inject.Inject;

import breads_and_aces.game.model.players.keeper.PlayersKeeper;

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
		view.populatePlayers( playersKeeper.getPlayers() );
	}
}
