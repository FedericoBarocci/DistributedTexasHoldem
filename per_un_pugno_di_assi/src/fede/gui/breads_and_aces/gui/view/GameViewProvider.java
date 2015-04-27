package breads_and_aces.gui.view;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class GameViewProvider implements Provider<GameView> {

//	private final FoldButton foldButton;
//	private final OkButton okButton;
	private GameView gameView;
	
	@Inject
	public GameViewProvider(/*OkButton okButton, FoldButton foldButton*/) {
//		this.okButton = okButton;
//		this.foldButton = foldButton;
	}
	
	public GameViewProvider create() {
		gameView = new GameView(/*okButton, foldButton*/);
		return this;
	}
	
	@Override
	public GameView get() {
		return gameView;
	}
}
