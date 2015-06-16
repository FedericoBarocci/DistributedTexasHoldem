package bread_and_aces.gui.view;

import com.google.inject.Inject;

import bread_and_aces.gui.view.elements.frame.JFrameGame;

public class ViewCreator implements InitableView<Void> {

	private final JFrameGame jFrameGame;

	@Inject
	public ViewCreator(JFrameGame jFrameGame) {
		this.jFrameGame = jFrameGame;
	}
	
	@Override
	public void init(Void noArg) {
		jFrameGame.init();
	}
}
