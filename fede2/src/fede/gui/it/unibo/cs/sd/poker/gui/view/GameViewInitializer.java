package it.unibo.cs.sd.poker.gui.view;

import java.util.Collections;

import javax.inject.Inject;

public class GameViewInitializer {
	
	@Inject
	public GameViewInitializer(/*Model model,*/ GameView view) {
		view.create();
		//TODO pass PlayersKeeper values
		view.populatePlayers( Collections.emptyList() );
	}
}
