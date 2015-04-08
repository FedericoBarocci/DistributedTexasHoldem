package it.unibo.cs.sd.poker.mvc;

import it.unibo.cs.sd.poker.gui.view.GameView;

import java.util.Collections;

import javax.inject.Inject;

public class GuiInitializer {
	
	@Inject
	public GuiInitializer(/*Model model,*/ GameView view) {
		view.create();
		//TODO pass PlayersKeeper values
		view.populatePlayers( Collections.emptyList() );
	}
}
